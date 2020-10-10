package com.numero.sojodia.ui.timetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.LifecycleOwnerAmbient
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.ui.tooling.preview.Preview
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.numero.sojodia.R
import com.numero.sojodia.databinding.DialogTimeTableBinding
import com.numero.sojodia.extension.module
import com.numero.sojodia.extension.stationTitleRes
import com.numero.sojodia.extension.titleStringRes
import com.numero.sojodia.model.*
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.ui.theme.SojoDiaTheme
import com.numero.sojodia.ui.theme.saturdayBlue
import com.numero.sojodia.ui.theme.sundayRed
import java.util.*

class TimeTableBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: DialogTimeTableBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val route by lazy {
        requireArguments().getSerializable(ARG_ROUTE) as Route
    }
    private val reciprocate by lazy {
        requireArguments().getSerializable(ARG_RECIPROCATE) as Reciprocate
    }

    private val busDataRepository: BusDataRepository
        get() = module.busDataRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogTimeTableBinding.inflate(LayoutInflater.from(context))
        binding.composeView.setContent {
            SojoDiaTheme {
                TimetableContent(
                    route = route,
                    reciprocate = reciprocate,
                    busDataRepository = busDataRepository
                )
            }
        }
        return binding.root
    }

    fun showIfNeed(fragmentManager: FragmentManager) {
        if (fragmentManager.findFragmentByTag(TAG) == null) {
            show(fragmentManager, TAG)
        }
    }

    companion object {

        private const val ARG_ROUTE = "ARG_ROUTE"
        private const val ARG_RECIPROCATE = "ARG_RECIPROCATE"

        private const val TAG = "TimeTableBottomSheetDialogFragment"

        fun newInstance(
            route: Route,
            reciprocate: Reciprocate
        ) = TimeTableBottomSheetDialogFragment().apply {
            arguments = bundleOf(ARG_ROUTE to route, ARG_RECIPROCATE to reciprocate)
        }
    }
}

/*
 * TODO
 *  - Filter only school
 *  - Horizontal divider
 */
@Composable
fun TimetableContent(
    route: Route,
    reciprocate: Reciprocate,
    busDataRepository: BusDataRepository
) {
    var timetableRowList by remember { mutableStateOf(TimeTableRowList(emptyList())) }
    val presenter = TimeTablePresenterImpl(object : TimeTableView {
        override fun showTimeTableRowList(list: TimeTableRowList) {
            timetableRowList = list
        }

        override fun setPresenter(presenter: TimeTablePresenter) {
        }
    }, busDataRepository, route, reciprocate)
    LifecycleOwnerAmbient.current.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            presenter.subscribe()
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
            presenter.unSubscribe()
        }
    })
    Column {
        TimetableToolbar(
            route = route,
            reciprocate = reciprocate
        )
        TimetableHeader()
        TimetableDivider()
        ScrollableColumn {
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            timetableRowList.value.forEachIndexed { index, timeTableRow ->
                if (index != 0) {
                    TimetableDivider()
                }
                TimetableRowItem(
                    timeTableRow = timeTableRow,
                    isNotSchoolTerm = false,
                    isCurrentHourItem = timeTableRow.hour == currentHour
                )
                if (index == timetableRowList.value.lastIndex) {
                    TimetableDivider()
                    TimetableFooter()
                }
            }
        }
    }
}

@Composable
fun TimetableToolbar(
    route: Route,
    reciprocate: Reciprocate
) {
    val context = ContextAmbient.current
    Row(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            Text(
                text = context.getString(route.stationTitleRes),
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = context.getString(reciprocate.titleStringRes),
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.primary
            )
        }
        Button(
            modifier = Modifier.align(Alignment.CenterVertically),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
            backgroundColor = MaterialTheme.colors.surface,
            shape = CircleShape,
            onClick = {
                // TODO click event
            }
        ) {
            Text(
                text = context.getString(R.string.not_school_term)
            )
        }
    }
}

@Composable
fun TimetableHeader() {
    val context = ContextAmbient.current
    val headerTextStyle = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
    Row {
        Text(
            text = context.getString(R.string.hour),
            style = headerTextStyle,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(56.dp).padding(vertical = 4.dp)
        )
        Text(
            text = context.getString(R.string.weekday),
            style = headerTextStyle,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        )
        Text(
            text = context.getString(R.string.saturday),
            style = headerTextStyle,
            color = saturdayBlue,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        )
        Text(
            text = context.getString(R.string.sunday),
            style = headerTextStyle,
            color = sundayRed,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        )
    }
}

@Composable
fun TimetableRowItem(
    timeTableRow: TimeTableRow,
    isNotSchoolTerm: Boolean,
    isCurrentHourItem: Boolean
) {
    Surface(
        color = if (isCurrentHourItem) {
            MaterialTheme.colors.primary.copy(alpha = 0.24f)
        } else {
            Color.Transparent
        }
    ) {
        Row {
            Text(
                text = "%02d".format(timeTableRow.hour),
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(56.dp).padding(vertical = 4.dp)
            )
            Text(
                text = timeTableRow.getOnWeekdayText(isNotSchoolTerm),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = timeTableRow.getOnSaturdayText(isNotSchoolTerm),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = timeTableRow.getOnSundayText(isNotSchoolTerm),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun TimetableFooter() {
    val context = ContextAmbient.current
    Text(
        text = context.getString(R.string.time_table_dialog_description),
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.End,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
private fun TimetableDivider() {
    Divider(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
    )
}

@Preview("TimetableToolbar")
@Composable
fun TimetableToolbarPreview() {
    SojoDiaTheme {
        TimetableToolbar(
            route = Route.TK,
            reciprocate = Reciprocate.GOING
        )
    }
}

@Preview("TimetableHeader")
@Composable
fun TimetableHeaderPreview() {
    SojoDiaTheme {
        TimetableHeader()
    }
}

@Preview("TimetableRowItem")
@Composable
fun TimetableRowItemPreview() {
    SojoDiaTheme {
        TimetableRowItem(
            timeTableRow = TimeTableRow(
                hour = 6
            ).apply {
                addBusTimeOnWeekday(
                    BusTime(
                        time = Time(hour = 6, min = 10),
                        week = Week.WEEKDAY,
                        isNonstop = false,
                        isOnlyOnSchooldays = false
                    )
                )
                addBusTimeOnSaturday(
                    BusTime(
                        time = Time(hour = 6, min = 1),
                        week = Week.SATURDAY,
                        isNonstop = false,
                        isOnlyOnSchooldays = false
                    )
                )
                addBusTimeOnSunday(
                    BusTime(
                        time = Time(hour = 6, min = 59),
                        week = Week.SUNDAY,
                        isNonstop = false,
                        isOnlyOnSchooldays = false
                    )
                )
            },
            isNotSchoolTerm = false,
            isCurrentHourItem = false
        )
    }
}

@Preview("TimetableFooter")
@Composable
fun TimetableFooterPreview() {
    SojoDiaTheme {
        TimetableFooter()
    }
}