package com.numero.sojodia.extension

import androidx.fragment.app.Fragment
import com.numero.sojodia.ILegacyModule

val Fragment.module: ILegacyModule
    get() = activity?.application as ILegacyModule