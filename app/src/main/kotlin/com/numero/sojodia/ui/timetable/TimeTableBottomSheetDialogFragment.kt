package com.numero.sojodia.ui.timetable

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.numero.sojodia.databinding.DialogTimeTableBinding
import com.numero.sojodia.extension.module
import com.numero.sojodia.extension.stationTitleRes
import com.numero.sojodia.extension.titleStringRes
import com.numero.sojodia.model.Reciprocate
import com.numero.sojodia.model.Route
import com.numero.sojodia.model.TimeTableRowList
import com.numero.sojodia.repository.BusDataRepository

class TimeTableBottomSheetDialogFragment : BottomSheetDialogFragment(), TimeTableView {

    private val adapter: TimeTableRowAdapter = TimeTableRowAdapter()

    private lateinit var binding: DialogTimeTableBinding
    private lateinit var presenter: TimeTablePresenter
    private val route: Route by lazy {
        arguments?.getSerializable(ARG_ROUTE) as Route
    }
    private val reciprocate: Reciprocate by lazy {
        arguments?.getSerializable(ARG_RECIPROCATE) as Reciprocate
    }

    private val busDataRepository: BusDataRepository
        get() = module.busDataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TimeTablePresenterImpl(this, busDataRepository, route, reciprocate)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        binding = DialogTimeTableBinding.inflate(LayoutInflater.from(context))
        binding.toolbar.apply {
            setTitle(route.stationTitleRes)
            binding.toolbar.setSubtitle(reciprocate.titleStringRes)
        }
        binding.timeTableRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = this@TimeTableBottomSheetDialogFragment.adapter
        }
        binding.notSchoolTermChip.setOnCheckedChangeListener { _, isChecked ->
            binding.notSchoolTermChip.isChipIconVisible = isChecked
            adapter.isNotSchoolTerm = isChecked
        }
        dialog.setContentView(binding.root)
        return dialog
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

    override fun showTimeTableRowList(timeTableRowList: TimeTableRowList) {
        adapter.tableRowList = timeTableRowList.value
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
