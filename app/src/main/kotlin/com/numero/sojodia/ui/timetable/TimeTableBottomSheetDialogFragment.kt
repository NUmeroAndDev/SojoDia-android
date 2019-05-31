package com.numero.sojodia.ui.timetable

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.numero.sojodia.R
import com.numero.sojodia.extension.app
import com.numero.sojodia.extension.stationTitleRes
import com.numero.sojodia.extension.titleStringRes
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.model.TimeTableRow
import com.numero.sojodia.repository.BusDataRepository
import kotlinx.android.synthetic.main.dialog_time_table.view.*

class TimeTableBottomSheetDialogFragment : BottomSheetDialogFragment(), TimeTableView {

    private lateinit var toolbar: Toolbar
    private val adapter: TimeTableRowAdapter = TimeTableRowAdapter()

    private lateinit var presenter: TimeTablePresenter

    private val busDataRepository: BusDataRepository
        get() = app.busDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arguments = arguments ?: return
        val route = arguments.getSerializable(ARG_ROUTE) as Route
        val reciprocate = arguments.getSerializable(ARG_RECIPROCATE) as Reciprocate
        TimeTablePresenterImpl(this, busDataRepository, route, reciprocate)
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val view = View.inflate(context, R.layout.dialog_time_table, null)
        toolbar = view.toolbar
        view.timeTableRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@TimeTableBottomSheetDialogFragment.adapter
        }
        view.notSchoolTermChip.setOnCheckedChangeListener { _, isChecked ->
            view.notSchoolTermChip.isChipIconVisible = isChecked
            adapter.isNotSchoolTerm = isChecked

        }
        dialog.setContentView(view)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    override fun setPresenter(presenter: TimeTablePresenter) {
        this.presenter = presenter
    }

    override fun showTimeTableRowList(timeTableRowList: List<TimeTableRow>) {
        adapter.tableRowList = timeTableRowList
    }

    override fun showCurrentRoute(route: Route) {
        toolbar.setTitle(route.stationTitleRes)
    }

    override fun showCurrentReciprocate(reciprocate: Reciprocate) {
        toolbar.setSubtitle(reciprocate.titleStringRes)
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

        fun newInstance(route: Route, reciprocate: Reciprocate): TimeTableBottomSheetDialogFragment = TimeTableBottomSheetDialogFragment().apply {
            arguments = bundleOf(ARG_ROUTE to route, ARG_RECIPROCATE to reciprocate)
        }
    }

}
