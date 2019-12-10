package com.google.samples.apps.sunflower.adapters;

import android.view.View;

import androidx.databinding.BindingAdapter;

public final class BindingAdapters {
    private BindingAdapters() {}

    @BindingAdapter("isGone")
    public static void bindIsGone(View view, boolean isGone) {
        view.setVisibility(isGone ? View.GONE : View.VISIBLE);
    }
}
