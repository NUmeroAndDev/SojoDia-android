package com.numero.sojodia.activity

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
import com.numero.common.IIntentResolver
import com.numero.sojodia.R
import com.numero.sojodia.extension.module
import com.numero.sojodia.extension.getTodayString
import com.numero.sojodia.extension.styleRes
import com.numero.sojodia.fragment.BusScheduleFragment
import com.numero.sojodia.fragment.TimeTableBottomSheetDialogFragment
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.repository.IBusDataRepository
import com.numero.sojodia.repository.IConfigRepository
import com.numero.sojodia.service.UpdateBusDataService
import com.numero.sojodia.util.BroadCastUtil
import com.numero.sojodia.view.adapter.BusScheduleFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), BusScheduleFragment.BusScheduleFragmentListener {

    private val finishDownloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showNeedRestartNotice()
        }
    }

    private val busDataRepository: IBusDataRepository
        get() = module.busDataRepository
    private val configRepository: IConfigRepository
        get() = module.configRepository
    private val intentResolver: IIntentResolver
        get() = module.intentResolver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(configRepository.theme.styleRes)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (busDataRepository.isNoBusTimeData) {
            startActivity(intentResolver.splashActivityIntent)
            finish()
            return
        }

        initViews()

        val shortcutIntent = intent.getStringExtra("shortcut")
        val reciprocate = intent.getSerializableExtra(BUNDLE_RECIPROCATE) as?Reciprocate
                ?: Reciprocate.findReciprocate(shortcutIntent)
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
                val reciprocate = Reciprocate.findReciprocate(viewPager.currentItem)
                startActivity(intentResolver.getSettingsActivityIntent(reciprocate))
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
        // FIXME これなんとかしたい
        const val BUNDLE_RECIPROCATE = "BUNDLE_RECIPROCATE"
    }
}
