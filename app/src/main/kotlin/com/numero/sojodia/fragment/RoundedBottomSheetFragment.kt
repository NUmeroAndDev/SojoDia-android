package com.numero.sojodia.fragment

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.numero.sojodia.R

open class RoundedBottomSheetFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme_Rounded

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, theme)
        return super.onCreateDialog(savedInstanceState)
    }
}