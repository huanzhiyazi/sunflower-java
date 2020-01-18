package com.google.samples.apps.sunflower.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.InverseBindingListener;

public class FakeEditText extends AppCompatEditText {
    private InverseBindingListener inverseBindingListener;
    public void setInverseBindingListener(InverseBindingListener listener) {
        this.inverseBindingListener = listener;
    }

    public FakeEditText(Context context) {
        super(context);
        init();
    }

    public FakeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FakeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (inverseBindingListener != null) {
                    inverseBindingListener.onChange();
                }
            }
        });
    }
}
