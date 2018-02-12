package com.numero.sojodia.activity

import android.content.*
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
import com.numero.sojodia.view.adapter.BusScheduleFragmentPagerAdapter
import com.numero.sojodia.service.UpdateBusDataService
import com.numero.sojodia.util.BroadCastUtil
import com.numero.sojodia.R
import com.numero.sojodia.extension.getApplicationComponent
import com.numero.sojodia.extension.getTodayString
import com.numero.sojodia.extension.showDialog
import com.numero.sojodia.fragment.TimeTableDialogFragment
import com.numero.sojodia.presenter.TimeTablePresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

import javax.inject.Inject

class MainActivity : AppCompatActivity(), BusScheduleFragment.BusScheduleFragmentListener {

    private val finishDownloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            showNeedRestartDialog()
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
            R.id.action_settings -> startActivity(SettingsActivity.createIntent(this))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(fragment: BusScheduleFragment, reciprocate: Reciprocate) {
        // FIXME AdapterではActivity再生成時にPresenterを生成できないからこの実装している
        BusSchedulePresenter(fragment, busDataRepository, reciprocate)
    }

    override fun onDataChanged() {
        toolbar.subtitle = Calendar.getInstance().getTodayString(getString(R.string.date_pattern))
    }

    override fun showTimeTableDialog(route: Route, reciprocate: Reciprocate) {
        TimeTableDialogFragment.newInstance().also {
            TimeTablePresenter(it, busDataRepository, route, reciprocate)
            it.show(supportFragmentManager, "")
        }
    }

    private fun initViews() {
        viewPager.adapter = BusScheduleFragmentPagerAdapter(this, supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun startCheckUpdateService() {
        startService(Intent(this, UpdateBusDataService::class.java))
    }

    private fun showNeedRestartDialog() {
        showDialog(
                R.string.message_need_update,
                R.string.positive_button_need_update,
                { _, _ ->
                    busDataRepository.clearCache()
                    this.recreate()
                }
        )
    }
}
