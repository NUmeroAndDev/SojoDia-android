package com.numero.sojodia.component.main

import androidx.fragment.app.Fragment
import com.numero.common.IModule
import com.numero.common.extension.module

val Fragment.module: IModule
    get() = requireContext().module