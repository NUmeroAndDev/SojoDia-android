package com.numero.sojodia.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import com.numero.sojodia.fragment.BusScheduleFragment
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.presenter.BusSchedulePresenter
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.view.TimeTableDialog
import com.numero.sojodia.view.adapter.BusScheduleFragmentPagerAdapter
import com.numero.sojodia.service.UpdateBusDataService
import com.numero.sojodia.util.BroadCastUtil
import com.numero.sojodia.view.NeedRestartDialog
import com.numero.sojodia.R
import com.numero.sojodia.extension.getApplicationComponent
import com.numero.sojodia.extension.getTodayString
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

import javax.inject.Inject

class MainActivity : AppCompatActivity(), BusScheduleFragment.BusScheduleFragmentListener {

    private val finishDownloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showNeedRestartDialog()
        }
    }

    private val changedDateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            toolbar.subtitle = Calendar.getInstance().getTodayString(getString(R.string.date_pattern))
        }
    }

    @Inject
    lateinit var busDataRepository: BusDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        getApplicationComponent()?.inject(this)

        initViews()

        val shortcutIntent = intent.getStringExtra("shortcut")
        viewPager.currentItem = if (shortcutIntent == "coming_home") 1 else 0
        startCheckUpdateService()
    }

    override fun onResume() {
        super.onResume()
        toolbar.subtitle = Calendar.getInstance().getTodayString(getString(R.string.date_pattern))
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).apply {
            registerReceiver(finishDownloadReceiver, IntentFilter(BroadCastUtil.ACTION_FINISH_DOWNLOAD))
            registerReceiver(changedDateReceiver, IntentFilter(BroadCastUtil.ACTION_CHANGED_DATE))
        }
    }

    public override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).apply {
            unregisterReceiver(finishDownloadReceiver)
            unregisterReceiver(changedDateReceiver)
        }
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

    override fun onActivityCreated(fragment: BusScheduleFragment, reciprocate: Reciprocate) {
        // FIXME AdapterではActivity再生成時にPresenterを生成できないからこの実装している
        BusSchedulePresenter(fragment, busDataRepository, reciprocate)
    }

    override fun showTimeTableDialog(route: Route, reciprocate: Reciprocate) {
        TimeTableDialog(this, busDataRepository).apply {
            setRoute(route)
            setReciprocate(reciprocate)
            show()
        }
    }

    private fun initViews() {
        val fragmentPagerAdapter = BusScheduleFragmentPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = fragmentPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun startCheckUpdateService() {
        startService(Intent(this@MainActivity, UpdateBusDataService::class.java))
    }

    private fun showNeedRestartDialog() {
        NeedRestartDialog.init(this).setOnPositiveButtonClickListener {
            busDataRepository.clearCache()
            this.recreate()
        }.show()
    }
}
