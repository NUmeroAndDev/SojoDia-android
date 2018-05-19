package com.numero.sojodia.extension

import androidx.fragment.app.Fragment
import com.numero.sojodia.IApplication

val Fragment.app: IApplication
    get() = activity?.application as IApplication