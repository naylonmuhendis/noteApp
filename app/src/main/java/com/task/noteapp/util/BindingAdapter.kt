package com.task.noteapp.util

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("visibleGoneIsNotNullOrEmpty")
    fun visibleGoneIsNotNullOrEmpty(view: TextView, text: String?) {
        view.visibility = if (text.isNullOrEmpty().not()) View.VISIBLE else View.GONE
    }

    @SuppressLint("SimpleDateFormat")
    @JvmStatic
    @BindingAdapter("setTimeStamp")
    fun setTimeStamp(textView: TextView, time: Long?) {
        time?.let {
            textView.text = SimpleDateFormat("dd/MM/yyyy").format(Date(time))
        }
    }
}