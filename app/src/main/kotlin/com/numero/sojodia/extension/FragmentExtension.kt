package com.numero.sojodia.extension

import androidx.fragment.app.Fragment
import com.numero.common.IModule

val Fragment.module: IModule
    get() = activity?.application as IModule