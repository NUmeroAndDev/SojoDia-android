package com.numero.sojodia.ui.board

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.numero.sojodia.R
import com.numero.sojodia.extension.getTodayString
import com.numero.sojodia.extension.module
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.ConfigRepository
import com.numero.sojodia.service.UpdateDataWorker
import com.numero.sojodia.ui.settings.SettingsActivity
import com.numero.sojodia.ui.splash.SplashActivity
import com.numero.sojodia.ui.timetable.TimeTableBottomSheetDialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BusScheduleFragment.BusScheduleFragmentListener {

    private val busDataRepository: BusDataRepository
        get() = module.busDataRepository
    private val configRepository: ConfigRepository
        get() = module.configRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (busDataRepository.getBusData().isNoBusData) {
            startActivity(SplashActivity.createIntent(this))
            finish()
            return
        }

        initViews()

        val shortcutIntent = intent.getStringExtra("shortcut")
        val reciprocate = intent.getSerializableExtra(BUNDLE_RECIPROCATE) as?Reciprocate
                ?: Reciprocate.from(shortcutIntent)
        // FIXME
        viewPager.currentItem = reciprocate.ordinal
        startCheckUpdateService()
    }

    override fun onResume() {
        super.onResume()
        toolbar.subtitle = Calendar.getInstance().getTodayString(getString(R.string.date_pattern))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> startActivity(SettingsActivity.createIntent(this))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDataChanged() {
        toolbar.subtitle = Calendar.getInstance().getTodayString(getString(R.string.date_pattern))
    }

    override fun showTimeTableDialog(route: Route, reciprocate: Reciprocate) {
        TimeTableBottomSheetDialogFragment.newInstance(route, reciprocate).showIfNeed(supportFragmentManager)
    }

    private fun initViews() {
        viewPager.adapter = BusScheduleFragmentPagerAdapter(this, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun startCheckUpdateService() {
        val request = OneTimeWorkRequestBuilder<UpdateDataWorker>()
                .build()
        WorkManager.getInstance().beginUniqueWork(
                UPDATE_WORKER_NAME,
                ExistingWorkPolicy.KEEP,
                request
        ).enqueue()
        WorkManager.getInstance().getWorkInfosForUniqueWorkLiveData(UPDATE_WORKER_NAME).observe(this) {
            if (it.isEmpty()) return@observe
            val info = it[0]
            if (info.state == WorkInfo.State.SUCCEEDED) {
                val result = info.outputData.getString(UpdateDataWorker.KEY_RESULT)
                        ?: return@observe
                if (result == UpdateDataWorker.SUCCESS_UPDATE) {
                    showNeedRestartNotice()
                }
            }
        }
    }

    private fun showNeedRestartNotice() {
        Snackbar.make(rootLayout, R.string.message_need_update, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.positive_button_need_update) {
                    busDataRepository.reloadBusData()
                    this.recreate()
                }
                .setAnchorView(R.id.tabCardView)
                .show()
    }

    companion object {

        private const val BUNDLE_RECIPROCATE = "BUNDLE_RECIPROCATE"
        private const val UPDATE_WORKER_NAME = "UPDATE_WORKER_NAME"

        fun createClearTopIntent(context: Context, reciprocate: Reciprocate? = null) = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(BUNDLE_RECIPROCATE, reciprocate)
        }
    }
}
