package com.numero.sojodia.extension

import android.support.v4.app.Fragment
import com.numero.sojodia.SojoDiaApplication
import com.numero.sojodia.di.ApplicationComponent

val Fragment.component: ApplicationComponent?
    get() = (activity?.application as? SojoDiaApplication)?.component