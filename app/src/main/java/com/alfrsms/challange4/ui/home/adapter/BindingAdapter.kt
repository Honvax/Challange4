package com.alfrsms.challange4.ui.home.adapter

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("app:hideIfEmpty")
fun hideIfEmpty(view: View, isEmpty: Boolean) {
    view.isVisible = isEmpty
}