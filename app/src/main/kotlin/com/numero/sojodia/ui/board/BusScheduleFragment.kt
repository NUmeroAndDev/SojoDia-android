package com.numero.sojodia.ui.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.numero.sojodia.R
import com.numero.sojodia.extension.app
import com.numero.sojodia.extension.createCountTime
import com.numero.sojodia.extension.getTodayStringOnlyFigure
import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.model.Week
import com.numero.sojodia.repository.BusDataRepository
import kotlinx.android.synthetic.main.bus_schedule_fragment.*
import java.util.*

class BusScheduleFragment : Fragment(), BusScheduleContract.View {


    private val busDataRepository: BusDataRepository
        get() = app.busDataRepository

    private lateinit var presenter: BusScheduleContract.Presenter

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
        BusSchedulePresenter(this, busDataRepository, reciprocate)
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
        presenter.onTimeChanged(Week.getCurrentWeek())
        tkCountdownTextView.start()
        tndCountdownTextView.start()
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
        tkCountdownTextView.stop()
        tndCountdownTextView.stop()
    }

    override fun setPresenter(presenter: BusScheduleContract.Presenter) {
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
            presenter.onTimeChanged(Week.getCurrentWeek())
        }
    }

    override fun showTkBusTimeList(busTimeList: MutableList<BusTime>) {
        tkBusTimeViewPager.apply {
            adapter = BusTimePagerAdapter().apply {
                setBusTimeList(busTimeList)
            }
        }
    }

    override fun showTndBusTimeList(busTimeList: MutableList<BusTime>) {
        tndBusTimeViewPager.apply {
            adapter = BusTimePagerAdapter().apply {
                setBusTimeList(busTimeList)
            }
        }
    }

    override fun selectTkCurrentBusPosition(position: Int) {
        tkBusTimeViewPager.setCurrentItem(position, true)
    }

    override fun startTkCountDown(busTime: BusTime) {
        val time = busTime.time.createCountTime()
        tkCountdownTextView.setTime(time.hour, time.min)
    }

    override fun selectTndCurrentBusPosition(position: Int) {
        tndBusTimeViewPager.setCurrentItem(position, true)
    }

    override fun startTndCountDown(busTime: BusTime) {
        val time = busTime.time.createCountTime()
        tndCountdownTextView.setTime(time.hour, time.min)
    }

    override fun showTkNextButton() {
        tkNextImageButton?.visibility = View.VISIBLE
    }

    override fun showTkPreviewButton() {
        tkPreviewImageButton?.visibility = View.VISIBLE
    }

    override fun showTkNoBusLayout() {
        tkNoBusLayout.visibility = View.VISIBLE
    }

    override fun showTndNextButton() {
        tndNextImageButton?.visibility = View.VISIBLE
    }

    override fun showTndPreviewButton() {
        tndPreviewImageButton?.visibility = View.VISIBLE
    }

    override fun showTndNoBusLayout() {
        tndNoBusLayout.visibility = View.VISIBLE
    }

    override fun hideTkNextButton() {
        tkNextImageButton?.visibility = View.INVISIBLE
    }

    override fun hideTkPreviewButton() {
        tkPreviewImageButton?.visibility = View.INVISIBLE
    }

    override fun hideTkNoBusLayout() {
        tkNoBusLayout.visibility = View.GONE
    }

    override fun hideTndNextButton() {
        tndNextImageButton?.visibility = View.INVISIBLE
    }

    override fun hideTndPreviewButton() {
        tndPreviewImageButton?.visibility = View.INVISIBLE
    }

    override fun hideTndNoBusLayout() {
        tndNoBusLayout.visibility = View.GONE
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