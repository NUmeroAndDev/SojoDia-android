package com.numero.sojodia.extension

import android.content.Context
import com.numero.common.IModule

val Context.module: IModule
    get() = applicationContext as IModule