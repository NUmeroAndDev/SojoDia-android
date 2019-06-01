package com.numero.sojodia.ui.board

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.snackbar.Snackbar
import com.numero.sojodia.R
import com.numero.sojodia.extension.app
import com.numero.sojodia.extension.applyAppTheme
import com.numero.sojodia.extension.getTodayString
import com.numero.sojodia.ui.timetable.TimeTableBottomSheetDialogFragment
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.service.UpdateBusDataService
import com.numero.sojodia.ui.settings.SettingsActivity
import com.numero.sojodia.ui.splash.SplashActivity
import com.numero.sojodia.util.BroadCastUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BusScheduleFragment.BusScheduleFragmentListener {

    private val finishDownloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showNeedRestartNotice()
        }
    }

    private val busDataRepository: BusDataRepository
        get() = app.busDataRepository
    private val configRepository: IConfigRepository
        get() = app.configRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyAppTheme(configRepository.appTheme)
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

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver(finishDownloadReceiver, IntentFilter(BroadCastUtil.ACTION_FINISH_DOWNLOAD))
    }

    public override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(finishDownloadReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val adapter = viewPager.adapter as? BusScheduleFragmentPagerAdapter
                        ?: return false
                val reciprocate = adapter.reciprocateList[viewPager.currentItem]
                startActivity(SettingsActivity.createIntent(this, reciprocate))
            }
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
        startService(Intent(this, UpdateBusDataService::class.java))
    }

    private fun showNeedRestartNotice() {
        Snackbar.make(rootLayout, R.string.message_need_update, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.positive_button_need_update) {
                    busDataRepository.reloadBusData()
                    this.recreate()
                }
                .show()
    }

    companion object {

        private const val BUNDLE_RECIPROCATE = "BUNDLE_RECIPROCATE"

        fun createClearTopIntent(context: Context, reciprocate: Reciprocate? = null) = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(BUNDLE_RECIPROCATE, reciprocate)
        }
    }
}