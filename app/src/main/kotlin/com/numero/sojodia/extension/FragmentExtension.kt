package com.numero.sojodia.extension

import androidx.fragment.app.Fragment
import com.numero.sojodia.Component

val Fragment.component: Component
    get() = activity?.application as Component