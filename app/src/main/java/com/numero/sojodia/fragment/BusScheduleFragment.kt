package com.numero.sojodia.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.numero.sojodia.contract.BusScheduleContract
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.util.BroadCastUtil
import com.numero.sojodia.view.adapter.BusTimePagerAdapter
import com.numero.sojodia.model.BusTime
import com.numero.sojodia.model.Time
import com.numero.sojodia.util.DateUtil
import com.numero.sojodia.util.TimeUtil
import com.numero.sojodia.view.CountDownClockTextView
import com.numero.sojodia.R
import kotlinx.android.synthetic.main.bus_schedule_fragment.*

class BusScheduleFragment : Fragment(), BusScheduleContract.View {

    private var listener: BusScheduleFragmentListener? = null

    private lateinit var presenter: BusScheduleContract.Presenter

    private var currentDateString: String? = null

    private val isDateChanged: Boolean
        get() = DateUtil.getTodayStringOnlyFigure() != currentDateString

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BusScheduleFragmentListener) {
            listener = context
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arguments = arguments ?: return
        val reciprocate = arguments.getSerializable(Reciprocate::class.java.simpleName) as Reciprocate
        listener?.onActivityCreated(this, reciprocate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentDateString = DateUtil.getTodayStringOnlyFigure()
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
        presenter.onTimeChanged(DateUtil.getWeek())
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    override fun setPresenter(presenter: BusScheduleContract.Presenter) {
        this.presenter = presenter
    }

    private fun initCountDownClockTextView() {
        tkCountdownTextView.setOnTimeChangedListener(object : CountDownClockTextView.OnTimeChangedListener {
            override fun onTimeChanged() {
                checkDateChange()
            }

            override fun onTimeLimit() {
                presenter.nextTkBus()
            }
        })

        tndCountdownTextView.setOnTimeChangedListener(object : CountDownClockTextView.OnTimeChangedListener {
            override fun onTimeChanged() {}

            override fun onTimeLimit() {
                presenter.nextTndBus()
            }
        })
    }

    private fun initTimeTableButton() {
        tkTimeTableButton.setOnClickListener {
            presenter.showTimeTableDialog(Route.TK)
        }
        tndTimeTableButton.setOnClickListener {
            presenter.showTimeTableDialog(Route.TND)
        }
    }

    private fun initNextPreviewButton() {
        tkNextImageButton.setOnClickListener {
            presenter.nextTkBus()
        }
        tkPreviewImageButton.setOnClickListener {
            presenter.previewTkBus()
        }
        tndNextImageButton.setOnClickListener {
            presenter.nextTndBus()
        }
        tndPreviewImageButton.setOnClickListener {
            presenter.previewTndBus()
        }
    }

    private fun checkDateChange() {
        if (isDateChanged) {
            currentDateString = DateUtil.getTodayStringOnlyFigure()
            BroadCastUtil.sendBroadCast(activity, BroadCastUtil.ACTION_CHANGED_DATE)
            presenter.onTimeChanged(DateUtil.getWeek())
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
        val time = Time(busTime.hour, busTime.min, 0)
        val countTime = TimeUtil.getCountTime(time)
        tkCountdownTextView.setTime(countTime.hour, countTime.min)
    }

    override fun selectTndCurrentBusPosition(position: Int) {
        tndBusTimeViewPager.setCurrentItem(position, true)
    }

    override fun startTndCountDown(busTime: BusTime) {
        val time = Time(busTime.hour, busTime.min, 0)
        val countTime = TimeUtil.getCountTime(time)
        tndCountdownTextView.setTime(countTime.hour, countTime.min)
    }

    override fun showTkNextButton() {
        tkNextImageButton.visibility = View.VISIBLE
    }

    override fun showTkPreviewButton() {
        tkPreviewImageButton.visibility = View.VISIBLE
    }

    override fun showTkNoBusLayout() {
        tkNoBusLayout.visibility = View.VISIBLE
    }

    override fun showTndNextButton() {
        tndNextImageButton.visibility = View.VISIBLE
    }

    override fun showTndPreviewButton() {
        tndPreviewImageButton.visibility = View.VISIBLE
    }

    override fun showTndNoBusLayout() {
        tndNoBusLayout.visibility = View.VISIBLE
    }

    override fun hideTkNextButton() {
        tkNextImageButton.visibility = View.GONE
    }

    override fun hideTkPreviewButton() {
        tkPreviewImageButton.visibility = View.GONE
    }

    override fun hideTkNoBusLayout() {
        tkNoBusLayout.visibility = View.GONE
    }

    override fun hideTndNextButton() {
        tndNextImageButton.visibility = View.GONE
    }

    override fun hideTndPreviewButton() {
        tndPreviewImageButton.visibility = View.GONE
    }

    override fun hideTndNoBusLayout() {
        tndNoBusLayout.visibility = View.GONE
    }

    override fun showTimeTableDialog(route: Route, reciprocate: Reciprocate) {
        listener?.showTimeTableDialog(route, reciprocate)
    }

    interface BusScheduleFragmentListener {
        fun onActivityCreated(fragment: BusScheduleFragment, reciprocate: Reciprocate)

        fun showTimeTableDialog(route: Route, reciprocate: Reciprocate)
    }

    companion object {
        fun newInstance(reciprocate: Reciprocate): BusScheduleFragment = BusScheduleFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Reciprocate::class.java.simpleName, reciprocate)
            }
        }
    }
}
