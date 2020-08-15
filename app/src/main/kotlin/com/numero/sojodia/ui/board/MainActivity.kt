package com.numero.sojodia.ui.board

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.numero.sojodia.R
import com.numero.sojodia.databinding.ActivityMainBinding
import com.numero.sojodia.extension.getTodayString
import com.numero.sojodia.extension.module
import com.numero.sojodia.extension.titleStringRes
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.service.UpdateDataWorker
import com.numero.sojodia.ui.settings.SettingsActivity
import com.numero.sojodia.ui.splash.SplashActivity
import com.numero.sojodia.ui.timetable.TimeTableBottomSheetDialogFragment
import java.util.*

class MainActivity : AppCompatActivity(), BusScheduleFragment.BusScheduleFragmentListener {

    private val busDataRepository: BusDataRepository
        get() = module.busDataRepository

    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appUpdateManager = AppUpdateManagerFactory.create(this)

        if (busDataRepository.getBusData().isNoBusData) {
            startActivity(SplashActivity.createIntent(this))
            finish()
            return
        }

        initViews()

        val shortcutIntent = intent.getStringExtra("shortcut")
        val reciprocate = intent.getSerializableExtra(BUNDLE_RECIPROCATE) as? Reciprocate
                ?: Reciprocate.from(shortcutIntent)
        // FIXME
        binding.viewPager.currentItem = reciprocate.ordinal
        startCheckUpdateService()
        checkHasUpdate()
    }

    override fun onResume() {
        super.onResume()
        checkProgressUpdate()
        binding.dateTextView.text = Calendar.getInstance().getTodayString(getString(R.string.date_pattern))
    }

    override fun onDataChanged() {
        binding.dateTextView.text = Calendar.getInstance().getTodayString(getString(R.string.date_pattern))
    }

    override fun showTimeTableDialog(route: Route, reciprocate: Reciprocate) {
        TimeTableBottomSheetDialogFragment.newInstance(route, reciprocate).showIfNeed(supportFragmentManager)
    }

    private fun initViews() {
        val reciprocateList = listOf(Reciprocate.GOING, Reciprocate.RETURN)
        binding.viewPager.adapter = BusSchedulePagerAdapter(this, reciprocateList)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val reciprocate = reciprocateList[position]
            tab.text = getString(reciprocate.titleStringRes)
        }.attach()
        binding.settingsImageButton.setOnClickListener {
            startActivity(SettingsActivity.createIntent(this))
        }
    }

    private fun startCheckUpdateService() {
        val request = OneTimeWorkRequestBuilder<UpdateDataWorker>()
                .build()
        WorkManager.getInstance(this).beginUniqueWork(
                UPDATE_WORKER_NAME,
                ExistingWorkPolicy.KEEP,
                request
        ).enqueue()
        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData(UPDATE_WORKER_NAME).observe(this) {
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

    private fun checkProgressUpdate() {
        appUpdateManager.appUpdateInfo
                .addOnSuccessListener { appUpdateInfo ->
                    if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, this, 0)
                    }
                }
    }

    private fun checkHasUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                binding.badgeView.isVisible = true
            }
        }
    }

    private fun showNeedRestartNotice() {
        Snackbar.make(binding.rootLayout, R.string.message_need_update, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.positive_button_need_update) {
                    busDataRepository.reloadBusData()
                    this.recreate()
                }
                .setAnchorView(R.id.tab_card_view)
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
