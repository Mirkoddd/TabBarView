package com.mirko.tbv;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabView extends LinearLayout {

    private ImageView mImageView;
    private TextView mTextView;

    public TabView(Context context) {
        this(context, null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.actionBarTabStyle);
    }

    public TabView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.actionBarTabTextStyle, outValue, true);
        int textStyle = outValue.data;

        int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());

        mImageView = new ImageView(context);
        mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        mImageView.setScaleType(ScaleType.CENTER_INSIDE);

        mTextView = new TextView(context);
        mTextView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setCompoundDrawablePadding(padding);
        mTextView.setTextAppearance(context, textStyle);

        addView(mImageView);
        addView(mTextView);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void setIcon(@DrawableRes int resId) {
        setIcon(getResources().getDrawable(resId));
    }

    public void setIcon(Drawable icon) {
        mImageView.setVisibility(icon != null ? View.VISIBLE : View.GONE);
        mImageView.setImageDrawable(icon);
    }

    public void setText(@StringRes int textId, @DrawableRes int drawableId) {
        setText(getContext().getString(textId), drawableId);
    }

    public void setText(CharSequence text, @DrawableRes int drawableId) {
        mTextView.setText(text);
        mTextView.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0);
    }

}