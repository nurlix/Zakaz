package com.ulutsoft.cafe.android;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by NURLAN on 13/10/2015.
 */
public class MenuLayout extends LinearLayout {

    private View menu;
    private View content;

    protected static final int menuMargin = 1;

    public enum MenuState { CLOSED, OPEN, CLOSING, OPENING };

    protected int currentContentOffset = 0;
    protected MenuState menuState = MenuState.CLOSED;

    protected Scroller menuAnimationScroller = new Scroller(this.getContext(), new SmoothInterpolator());
    protected Runnable menuAnimationRunnable = new AnimationRunnable();
    protected Handler menuAnimationHandler = new Handler();

    private static final int menuAnimationDuration = 500;
    private static final int menuAnimationPollingInterval = 15;

    public MenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuLayout(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        this.menu = this.getChildAt(0);
        this.content = this.getChildAt(1);
        this.menu.setVisibility(View.GONE);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed) this.calculateChildDimensions();

        this.menu.layout(l, t, r - menuMargin, b);
        this.content.layout(l + currentContentOffset, t, r + currentContentOffset, b);
    }

    public void toggleMenu() {
        switch (this.menuState) {
            case CLOSED:
                this.menuState = MenuState.OPENING;
                this.menu.setVisibility(View.VISIBLE);
                this.menuAnimationScroller.startScroll(0, 0, this.getMenuWidth(), 0, menuAnimationDuration);
                break;
            case OPEN:
                this.menuState = MenuState.CLOSING;
                this.menuAnimationScroller.startScroll(this.currentContentOffset,0, -this.currentContentOffset, 0, menuAnimationDuration);
                break;
            default:
                return;
        }
        this.menuAnimationHandler.postDelayed(this.menuAnimationRunnable, menuAnimationPollingInterval);
        this.invalidate();
    }

    private int getMenuWidth() {
        return this.menu.getLayoutParams().width;
    }

    public MenuState getMenuState() {
        return this.menuState;
    }

    private void calculateChildDimensions() {
        this.content.getLayoutParams().height = this.getHeight();
        this.content.getLayoutParams().width = this.getWidth();
        this.menu.getLayoutParams().width = this.getWidth() - menuMargin;
        this.menu.getLayoutParams().height = this.getHeight();
    }

    private void adjustContentPosition(boolean isAnimationOngoing) {
        int scrollerOffset = this.menuAnimationScroller.getCurrX();

        this.content.offsetLeftAndRight(scrollerOffset - this.currentContentOffset);
        this.currentContentOffset = scrollerOffset;
        this.invalidate();

        if (isAnimationOngoing)
            this.menuAnimationHandler.postDelayed(this.menuAnimationRunnable, menuAnimationPollingInterval);
        else
            this.onMenuTransitionComplete();
    }

    private void onMenuTransitionComplete() {
        switch (this.menuState) {
            case OPENING:
                this.menuState = MenuState.OPEN;
                break;
            case CLOSING:
                this.menuState = MenuState.CLOSED;
                this.menu.setVisibility(View.GONE);
                break;
            default:
                return;
        }
    }

    protected class SmoothInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float t) {
            return (float)Math.pow(t-1, 5) + 1;
        }

    }

    protected class AnimationRunnable implements Runnable {
        @Override
        public void run() {
            MenuLayout.this.adjustContentPosition(MenuLayout.this.menuAnimationScroller.computeScrollOffset());
        }

    }
}

