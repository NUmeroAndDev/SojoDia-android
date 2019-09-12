package com.numero.sojodia.ui.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.numero.sojodia.R
import com.numero.sojodia.extension.getTodayStringOnlyFigure
import com.numero.sojodia.extension.module
import com.numero.sojodia.model.*
import com.numero.sojodia.repository.BusDataRepository
import kotlinx.android.synthetic.main.bus_schedule_fragment.*
import java.util.*

class BusScheduleFragment : Fragment(), BusScheduleView {

    private val busDataRepository: BusDataRepository
        get() = module.busDataRepository

    private lateinit var presenter: BusSchedulePresenter

    private var listener: BusScheduleFragmentListener? = null
    private var currentDateString: String? = null

    private val isDateChanged: Boolean
        get() = Calendar.getInstance().getTodayStringOnlyFigure() != currentDateString

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BusScheduleFragmentListener) {
            listener = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        val arguments = arguments ?: return
        val reciprocate = arguments.getSerializable(ARG_RECIPROCATE) as Reciprocate
        BusSchedulePresenterImpl(this, busDataRepository, reciprocate)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentDateString = Calendar.getInstance().getTodayStringOnlyFigure()
        return inflater.inflate(R.layout.bus_schedule_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCountDownClockTextView()
        initNextPreviewButton()
        initTimeTableButton()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
        presenter.setupBusTime(Week.getCurrentWeek())
        tkCountdownTextView.start()
        tndCountdownTextView.start()
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
        tkCountdownTextView.stop()
        tndCountdownTextView.stop()
    }

    override fun setPresenter(presenter: BusSchedulePresenter) {
        this.presenter = presenter
    }

    private fun initCountDownClockTextView() {
        tkCountdownTextView.apply {
            setOnTimeLimitListener {
                presenter.nextTkBus()
            }
            setOnCountListener {
                checkDateChange()
            }
        }
        tndCountdownTextView.setOnTimeLimitListener {
            presenter.nextTndBus()
        }
        tkCountdownTextView.setSyncView(tndCountdownTextView)
    }

    private fun initTimeTableButton() {
        tkTimetableActionChip.setOnClickListener {
            presenter.showTimeTableDialog(Route.TK)
        }
        tndTimetableActionChip.setOnClickListener {
            presenter.showTimeTableDialog(Route.TND)
        }
    }

    private fun initNextPreviewButton() {
        tkNextImageButton?.setOnClickListener {
            presenter.nextTkBus()
        }
        tkPreviewImageButton?.setOnClickListener {
            presenter.previewTkBus()
        }
        tndNextImageButton?.setOnClickListener {
            presenter.nextTndBus()
        }
        tndPreviewImageButton?.setOnClickListener {
            presenter.previewTndBus()
        }
    }

    private fun checkDateChange() {
        if (isDateChanged) {
            listener?.onDataChanged()
            currentDateString = Calendar.getInstance().getTodayStringOnlyFigure()
            presenter.setupBusTime(Week.getCurrentWeek())
        }
    }

    override fun showTkBusTimeList(busTimeList: TkBusTimeList) {
        tkBusTimeViewPager.apply {
            adapter = BusTimePagerAdapter().apply {
                setBusTimeList(busTimeList.value)
            }
        }
    }

    override fun showTndBusTimeList(busTimeList: TndBusTimeList) {
        tndBusTimeViewPager.apply {
            adapter = BusTimePagerAdapter().apply {
                setBusTimeList(busTimeList.value)
            }
        }
    }

    override fun selectTkCurrentBusPosition(position: Int) {
        tkBusTimeViewPager.setCurrentItem(position, true)
    }

    override fun startTkCountDown(busTime: BusTime) {
        val time = busTime.time - Time()
        tkCountdownTextView.setTime(time.hour, time.min)
    }

    override fun selectTndCurrentBusPosition(position: Int) {
        tndBusTimeViewPager.setCurrentItem(position, true)
    }

    override fun startTndCountDown(busTime: BusTime) {
        val time = busTime.time - Time()
        tndCountdownTextView.setTime(time.hour, time.min)
    }

    override fun showTkNextButton() {
        tkNextImageButton?.isInvisible = false
    }

    override fun showTkPreviewButton() {
        tkPreviewImageButton?.isInvisible = false
    }

    override fun showTkNoBusLayout() {
        tkNoBusTextView.isVisible = true
        tkCountdownTextView.isVisible = false
        tkBusTimeViewPager.isVisible = false
    }

    override fun showTndNextButton() {
        tndNextImageButton?.isInvisible = false
    }

    override fun showTndPreviewButton() {
        tndPreviewImageButton?.isInvisible = false
    }

    override fun showTndNoBusLayout() {
        tndNoBusTextView.isVisible = true
        tndCountdownTextView.isVisible = false
        tndBusTimeViewPager.isVisible = false
    }

    override fun hideTkNextButton() {
        tkNextImageButton?.isInvisible = true
    }

    override fun hideTkPreviewButton() {
        tkPreviewImageButton?.isInvisible = true
    }

    override fun hideTkNoBusLayout() {
        tkNoBusTextView.isVisible = false
        tkCountdownTextView.isVisible = true
        tkBusTimeViewPager.isVisible = true
    }

    override fun hideTndNextButton() {
        tndNextImageButton?.isInvisible = true
    }

    override fun hideTndPreviewButton() {
        tndPreviewImageButton?.isInvisible = true
    }

    override fun hideTndNoBusLayout() {
        tndNoBusTextView.isVisible = false
        tndCountdownTextView.isVisible = true
        tndBusTimeViewPager.isVisible = true
    }

    override fun showTimeTableDialog(route: Route, reciprocate: Reciprocate) {
        listener?.showTimeTableDialog(route, reciprocate)
    }

    interface BusScheduleFragmentListener {
        fun onDataChanged()

        fun showTimeTableDialog(route: Route, reciprocate: Reciprocate)
    }

    companion object {

        private const val ARG_RECIPROCATE = "ARG_RECIPROCATE"

        fun newInstance(reciprocate: Reciprocate): BusScheduleFragment = BusScheduleFragment().apply {
            arguments = bundleOf(ARG_RECIPROCATE to reciprocate)
        }
    }
}
