package com.numero.sojodia.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import androidx.core.os.bundleOf

import com.numero.sojodia.R
import com.numero.sojodia.contract.TimeTableContract
import com.numero.sojodia.extension.component
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.model.TimeTableRow
import com.numero.sojodia.presenter.TimeTablePresenter
import com.numero.sojodia.repository.BusDataRepository
import com.numero.sojodia.view.adapter.TimeTableRowAdapter
import kotlinx.android.synthetic.main.dialog_time_table.view.*
import javax.inject.Inject

class TimeTableDialogFragment : DialogFragment(), TimeTableContract.View {

    private var toolbar: Toolbar? = null
    private val adapter: TimeTableRowAdapter = TimeTableRowAdapter()

    private lateinit var presenter: TimeTableContract.Presenter

    @Inject
    lateinit var busDataRepository: BusDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component?.inject(this)

        val arguments = arguments ?: return
        val route = arguments.getSerializable(ARG_ROUTE) as Route
        val reciprocate = arguments.getSerializable(ARG_RECIPROCATE) as Reciprocate
        TimeTablePresenter(this, busDataRepository, route, reciprocate)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_time_table, null)
        toolbar = view.toolbar.apply {
            setNavigationOnClickListener {
                dismiss()
            }
        }
        view.timeTableRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@TimeTableDialogFragment.adapter
        }
        return AlertDialog.Builder(view.context)
                .setView(view)
                .create()
    }

    override fun onStart() {
        super.onStart()
        dialog ?: return
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unSubscribe()
    }

    override fun setPresenter(presenter: TimeTableContract.Presenter) {
        this.presenter = presenter
    }

    override fun showTimeTableRowList(timeTableRowList: MutableList<TimeTableRow>) {
        adapter.tableRowList = timeTableRowList
    }

    override fun showCurrentRoute(route: Route) {
        toolbar?.setTitle(route.stationStringRes)
    }

    override fun showCurrentReciprocate(reciprocate: Reciprocate) {
        toolbar?.setSubtitle(reciprocate.titleStringRes)
    }

    companion object {

        private const val ARG_ROUTE = "ARG_ROUTE"
        private const val ARG_RECIPROCATE = "ARG_RECIPROCATE"

        fun newInstance(route: Route, reciprocate: Reciprocate): TimeTableDialogFragment = TimeTableDialogFragment().apply {
            arguments = bundleOf(ARG_ROUTE to route, ARG_RECIPROCATE to reciprocate)
        }
    }

}
