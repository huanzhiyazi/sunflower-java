package com.google.samples.apps.sunflower.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.samples.apps.sunflower.R;

public class MaskedCardView extends MaterialCardView {
    private final ShapeAppearancePathProvider pathProvider = new ShapeAppearancePathProvider();
    private final Path path = new Path();
    private final ShapeAppearanceModel shapeAppearance;
    private final RectF rectF = new RectF(0f, 0f, 0f, 0f);

    public MaskedCardView(Context context) {
        this(context, null);
    }

    public MaskedCardView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.materialCardViewStyle);
    }

    public MaskedCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.shapeAppearance = new ShapeAppearanceModel(
                context,
                attrs,
                defStyleAttr,
                R.style.Widget_MaterialComponents_CardView
        );
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        rectF.right = w;
        rectF.bottom = h;
        pathProvider.calculatePath(shapeAppearance, 1f, rectF, path);
        super.onSizeChanged(w, h, oldw, oldh);
    }
}
