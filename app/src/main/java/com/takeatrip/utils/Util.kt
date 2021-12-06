package com.takeatrip.utils

import android.text.TextUtils
import android.view.View
import com.takeatrip.models.meal.MealData
import java.util.*

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}

fun isEmailValid(value: CharSequence): Boolean {
    return !TextUtils.isEmpty(value) && android.util.Patterns.EMAIL_ADDRESS.matcher(value)
        .matches();
}
