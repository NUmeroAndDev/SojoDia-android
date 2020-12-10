package com.numero.sojodia.ui.board

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.numero.sojodia.R
import com.numero.sojodia.databinding.BusScheduleFragmentBinding
import com.numero.sojodia.extension.component
import com.numero.sojodia.extension.getTodayStringOnlyFigure
import com.numero.sojodia.extension.stationTitleRes
import com.numero.sojodia.model.*
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.ui.theme.SojoDiaTheme
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        currentDateString = Calendar.getInstance().getTodayStringOnlyFigure()
        _binding = BusScheduleFragmentBinding.inflate(inflater, container, false)
        val reciprocate = checkNotNull(arguments?.getSerializable(ARG_RECIPROCATE)) as Reciprocate
        binding.composeView?.setContent {
            SojoDiaTheme {
                val vm = viewModel<BusBoardViewModel>()
                val uiState by vm.uiState.collectAsState()
                LaunchedEffect(Unit) {
                    vm.fetchBusData(busDataRepository, reciprocate)
                }
                BusBoardContent(
                    busBoardUiState = uiState
                )
            }
        }
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

        fun newInstance(reciprocate: Reciprocate): BusScheduleFragment =
            BusScheduleFragment().apply {
                arguments = bundleOf(ARG_RECIPROCATE to reciprocate)
            }
    }
}

@Composable
fun BusBoardContent(
    modifier: Modifier = Modifier,
    busBoardUiState: BusBoardUiState
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CountdownCard(
            modifier = Modifier.fillMaxSize().weight(1f),
            busBoardSchedule = busBoardUiState.tkBusBoardSchedule
        )
        Spacer(modifier = Modifier.preferredHeight(8.dp))
        CountdownCard(
            modifier = Modifier.fillMaxSize().weight(1f),
            busBoardSchedule = busBoardUiState.tndBusBoardSchedule
        )
    }
}

@Composable
fun CountdownCard(
    modifier: Modifier = Modifier,
    busBoardSchedule: BusBoardSchedule
) {
    Card(
        modifier = modifier.padding(16.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = busBoardSchedule.route.stationTitleRes),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.primary
                )
                OutlinedButton(
                    shape = CircleShape,
                    colors = ButtonConstants.defaultOutlinedButtonColors(
                        contentColor = MaterialTheme.colors.onSurface
                    ),
                    contentPadding = ButtonConstants.DefaultContentPadding.copy(
                        start = 12.dp
                    ),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = vectorResource(id = R.drawable.ic_schedule),
                        modifier = Modifier.size(ButtonConstants.DefaultIconSize)
                    )
                    Spacer(modifier = Modifier.preferredWidth(4.dp))
                    Text(text = stringResource(id = R.string.timetable_label))
                }
            }

            Box(
                modifier = Modifier.weight(1f)
            ) {
                // TODO Replace countdown
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "HH:mm:ss"
                )
            }

            BusDepartureTime(
                modifier = Modifier.fillMaxWidth(),
                nearBusTime = busBoardSchedule.nearBusTime,
                nextBusTime = busBoardSchedule.nextBusTime
            )
        }
    }
}

@Composable
fun BusDepartureTime(
    modifier: Modifier = Modifier,
    nearBusTime: BusTime?,
    nextBusTime: BusTime?
) {
    Box(
        modifier = modifier.preferredHeight(48.dp)
    ) {
        if (nearBusTime != null) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = nearBusTime.time.format(),
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        }
        if (nextBusTime != null) {
            Text(
                modifier = Modifier.align(Alignment.CenterEnd),
                text = nextBusTime.time.format(),
                style = MaterialTheme.typography.body2
            )
        }
    }
}

fun Time.format(format: String = "%02d:%02d"): String {
    return format.format(hour, min)
}

@Preview("CountdownCard")
@Composable
fun CountdownCardPreview() {
    SojoDiaTheme {
        CountdownCard(
            busBoardSchedule = BusBoardSchedule(
                route = Route.TK,
                nearBusTime = BusTime(
                    Time(9, 1),
                    week = Week.WEEKDAY,
                    isNonstop = false,
                    isOnlyOnSchooldays = false
                ),
                nextBusTime = BusTime(
                    Time(10, 10),
                    week = Week.WEEKDAY,
                    isNonstop = false,
                    isOnlyOnSchooldays = false
                )
            )
        )
    }
}