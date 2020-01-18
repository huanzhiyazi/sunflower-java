package com.google.samples.apps.sunflower.adapters;

import android.content.res.Resources;
import android.text.Editable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.samples.apps.sunflower.R;
import com.google.samples.apps.sunflower.views.FakeEditText;

import static androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT;

public final class PlantDetailBindingAdapters {
    private PlantDetailBindingAdapters() {}

    @BindingAdapter("imageFromUrl")
    public static void bindImageFromUrl(ImageView view, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(view.getContext())
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(view);
        }
    }

    @BindingAdapter("isGone")
    public static void bindIsGone(FloatingActionButton view, Boolean isGone) {
        if (isGone == null || isGone) {
            view.hide();
        } else {
            view.show();
        }
    }

    @BindingAdapter("renderHtml")
    public static void bindRenderHtml(TextView view, String description) {
        if (description != null) {
            view.setText(HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT));
            view.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            view.setText("");
        }
    }

    @BindingAdapter("wateringText")
    public static void bindWateringText(TextView textView, int wateringInterval) {
        final Resources resources = textView.getContext().getResources();
        final String quantityString = resources.getQuantityString(R.plurals.watering_needs_suffix,
                wateringInterval, wateringInterval);

        textView.setText(quantityString);
    }

    @BindingAdapter(value = "android:text", requireAll = false)
    public static void setName(FakeEditText view, String name) {
        if (!TextUtils.equals(view.getText(), name)) {
            view.setText(name);
        }
    }

    @InverseBindingAdapter(attribute = "android:text", event = "textAttrChanged")
    public static String getName(FakeEditText view) {
        final Editable editable = view.getText();
        return editable != null ? editable.toString() : null;
    }

    @BindingAdapter(value = {"textAttrChanged"}, requireAll = false)
    public static void setTextAttrChanged(FakeEditText view, InverseBindingListener inverseBindingListener) {
        view.setInverseBindingListener(inverseBindingListener);
    }
}
