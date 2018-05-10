package com.numero.sojodia.extension

import androidx.fragment.app.Fragment
import com.numero.sojodia.SojoDiaApplication
import com.numero.sojodia.di.ApplicationComponent

val androidx.fragment.app.Fragment.component: ApplicationComponent?
    get() = (activity?.application as? SojoDiaApplication)?.component