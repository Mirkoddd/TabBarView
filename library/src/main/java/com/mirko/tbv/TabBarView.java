package com.mirko.tbv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;

public class TabBarView extends LinearLayout {
    public interface IconTabProvider {
        @DrawableRes public int getPageIconResId(int position);
    }

    private static final int STRIP_HEIGHT = 6;

    private final Paint mPaint;
    private int mStripHeight;
    private float mOffset = 0f;
    private static int mSelectedTab = 0;
    private ViewPager mViewPager;
    private int mTabCount;
    private final PageListener mPageListener = new PageListener();
    private OnPageChangeListener mOnPageChangeListener;

    public TabBarView(Context context) {
        this(context, null);
    }

    public TabBarView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.actionBarTabBarStyle);
    }

    public TabBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(false);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);

        mStripHeight = (int)(STRIP_HEIGHT * getResources().getDisplayMetrics().density + .5f);
    }

    public void setStripColor(int color) {
        if (mPaint.getColor() != color) {
            mPaint.setColor(color);
            invalidate();
        }
    }

    public void setStripHeight(int height) {
        if (mStripHeight != height) {
            mStripHeight = height;
            invalidate();
        }
    }

    public void setSelectedTab(int tabIndex) {
        if (tabIndex < 0) {
            tabIndex = 0;
        }
        final int childCount = getChildCount();
        if (tabIndex >= childCount) {
            tabIndex = childCount - 1;
        }
        if (mSelectedTab != tabIndex) {
            mSelectedTab = tabIndex;
            invalidate();
        }
    }

    public void setOffset(int position, float offset) {
        if (mOffset != offset) {
            mOffset = offset;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Draw the strip manually
        View child = getChildAt(mSelectedTab);
        int height = getHeight();
        if (child != null) {
            float left = child.getLeft();
            float right = child.getRight();
            if (mOffset > 0f && mSelectedTab < mTabCount - 1) {
                View nextChild = getChildAt(mSelectedTab + 1);
                if (nextChild != null) {
                    final float nextTabLeft = nextChild.getLeft();
                    final float nextTabRight = nextChild.getRight();
                    left = (mOffset * nextTabLeft + (1f - mOffset) * left);
                    right = (mOffset * nextTabRight + (1f - mOffset) * right);
                }
            }
            canvas.drawRect(left, height - mStripHeight, right, height, mPaint);
        }
    }

    public void setViewPager(ViewPager pager) {
        this.mViewPager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.setOnPageChangeListener(mPageListener);

        notifyDataSetChanged();
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mSelectedTab = position;
            mOffset = positionOffset;

            invalidate();

            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageSelected(position);
            }
        }
    }

    public void notifyDataSetChanged() {
        removeAllViews();

        mTabCount = mViewPager.getAdapter().getCount();

        for (int i = 0; i < mTabCount; i++) {
            PagerAdapter pagerAdapter = mViewPager.getAdapter();
            CharSequence title = pagerAdapter.getPageTitle(i);

            int iconResId = 0;
            if(pagerAdapter instanceof IconTabProvider) {
                iconResId = ((IconTabProvider)pagerAdapter).getPageIconResId(i);
            }

            if (getResources().getConfiguration().orientation == 1) {
                addTabViewPortrait(i, title, iconResId);
            } else {
                addTabViewLandscape(i, title, iconResId);
            }
        }

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                mSelectedTab = mViewPager.getCurrentItem();
            }
        });
    }

    private void addTabViewLandscape(final int i, CharSequence title, @DrawableRes int pageIconResId) {
        TabView tab = new TabView(getContext());
        tab.setText(title, pageIconResId);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(i);
            }
        });

        this.addView(tab);
    }


    private void addTabViewPortrait(final int i, CharSequence title, @DrawableRes int pageIconResId) {
        TabView tab = new TabView(getContext());
        tab.setIcon(pageIconResId);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(i);
            }
        });
        if(title != null) CheatSheet.setup(tab, title);

        this.addView(tab);
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }
}