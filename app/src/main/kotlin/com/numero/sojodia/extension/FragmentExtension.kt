package com.numero.sojodia.extension

import androidx.fragment.app.Fragment
import com.numero.sojodia.Module

val Fragment.module: Module
    get() = activity?.application as Module