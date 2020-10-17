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
import com.numero.sojodia.databinding.BusScheduleFragmentBinding
import com.numero.sojodia.extension.getTodayStringOnlyFigure
import com.numero.sojodia.extension.component
import com.numero.sojodia.model.*
import com.numero.sojodia.repository.BusDataRepository
import java.util.*

class BusScheduleFragment : Fragment(), BusScheduleView {

    private val busDataRepository: BusDataRepository
        get() = component.busDataRepository

    private lateinit var presenter: BusSchedulePresenter

    private var _binding: BusScheduleFragmentBinding? = null
    private val binding get() = _binding!!

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
        val arguments = arguments ?: return
        val reciprocate = arguments.getSerializable(ARG_RECIPROCATE) as Reciprocate
        BusSchedulePresenterImpl(this, busDataRepository, reciprocate)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        currentDateString = Calendar.getInstance().getTodayStringOnlyFigure()
        _binding = BusScheduleFragmentBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.tkCountdownTextView.start()
        binding.tndCountdownTextView.start()
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
        binding.tkCountdownTextView.stop()
        binding.tndCountdownTextView.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setPresenter(presenter: BusSchedulePresenter) {
        this.presenter = presenter
    }

    private fun initCountDownClockTextView() {
        binding.tkCountdownTextView.apply {
            setOnTimeLimitListener {
                presenter.nextTkBus()
            }
            setOnCountListener {
                checkDateChange()
            }
        }
        binding.tndCountdownTextView.setOnTimeLimitListener {
            presenter.nextTndBus()
        }
        binding.tkCountdownTextView.setSyncView(binding.tndCountdownTextView)
    }

    private fun initTimeTableButton() {
        binding.tkTimetableActionChip.setOnClickListener {
            presenter.showTimeTableDialog(Route.TK)
        }
        binding.tndTimetableActionChip.setOnClickListener {
            presenter.showTimeTableDialog(Route.TND)
        }
    }

    private fun initNextPreviewButton() {
        binding.tkNextImageButton?.setOnClickListener {
            presenter.nextTkBus()
        }
        binding.tkPreviewImageButton?.setOnClickListener {
            presenter.previewTkBus()
        }
        binding.tndNextImageButton?.setOnClickListener {
            presenter.nextTndBus()
        }
        binding.tndPreviewImageButton?.setOnClickListener {
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
        binding.tkBusTimeViewPager.apply {
            adapter = BusTimePagerAdapter().apply {
                setBusTimeList(busTimeList.value)
            }
        }
    }

    override fun showTndBusTimeList(busTimeList: TndBusTimeList) {
        binding.tndBusTimeViewPager.apply {
            adapter = BusTimePagerAdapter().apply {
                setBusTimeList(busTimeList.value)
            }
        }
    }

    override fun selectTkCurrentBusPosition(position: Int) {
        binding.tkBusTimeViewPager.setCurrentItem(position, true)
    }

    override fun startTkCountDown(busTime: BusTime) {
        val time = busTime.time - Time()
        binding.tkCountdownTextView.setTime(time.hour, time.min)
    }

    override fun selectTndCurrentBusPosition(position: Int) {
        binding.tndBusTimeViewPager.setCurrentItem(position, true)
    }

    override fun startTndCountDown(busTime: BusTime) {
        val time = busTime.time - Time()
        binding.tndCountdownTextView.setTime(time.hour, time.min)
    }

    override fun showTkNextButton() {
        binding.tkNextImageButton?.isInvisible = false
    }

    override fun showTkPreviewButton() {
        binding.tkPreviewImageButton?.isInvisible = false
    }

    override fun showTkNoBusLayout() {
        binding.tkNoBusTextView.isVisible = true
        binding.tkCountdownTextView.isInvisible = true
        binding.tkBusTimeViewPager.isVisible = false
    }

    override fun showTndNextButton() {
        binding.tndNextImageButton?.isInvisible = false
    }

    override fun showTndPreviewButton() {
        binding.tndPreviewImageButton?.isInvisible = false
    }

    override fun showTndNoBusLayout() {
        binding.tndNoBusTextView.isVisible = true
        binding.tndCountdownTextView.isInvisible = true
        binding.tndBusTimeViewPager.isVisible = false
    }

    override fun hideTkNextButton() {
        binding.tkNextImageButton?.isInvisible = true
    }

    override fun hideTkPreviewButton() {
        binding.tkPreviewImageButton?.isInvisible = true
    }

    override fun hideTkNoBusLayout() {
        binding.tkNoBusTextView.isVisible = false
        binding.tkCountdownTextView.isVisible = true
        binding.tkBusTimeViewPager.isVisible = true
    }

    override fun hideTndNextButton() {
        binding.tndNextImageButton?.isInvisible = true
    }

    override fun hideTndPreviewButton() {
        binding.tndPreviewImageButton?.isInvisible = true
    }

    override fun hideTndNoBusLayout() {
        binding.tndNoBusTextView.isVisible = false
        binding.tndCountdownTextView.isVisible = true
        binding.tndBusTimeViewPager.isVisible = true
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
