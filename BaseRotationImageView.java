package jp.co.yayoi_kk.android.snapvoucher.views;

import android.content.Context;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.OrientationEventListener;
import android.widget.ImageView;


/**
 * Created on 2016/12/14.
 */

public abstract class BaseRotationImageView extends ImageView {

    private OrientationEventListener _orientationEventLitener;

    private static final int UP = 0;
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    private static final int LEFT = 3;

    private int currentOrientation = 0;
    private int nextOrientation = 0;

    abstract void onOrientationChangeToUp();
    abstract void onOrientationChangeToRight();
    abstract void onOrientationChangeToLeft();
    abstract void onOrientationChangeToDown();


    public BaseRotationImageView(Context context) {
        super(context);
        init(context);
    }

    public BaseRotationImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseRotationImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        _orientationEventLitener = new OrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int degree) {
                _rotate(degree);
            }
        };
    }

    private void _rotate(int degree) {
        if(degree >= 330 || degree <= 30) {
            nextOrientation = UP;
        }
        if(degree >= 60 && degree <= 120 ) {
            nextOrientation = LEFT;
        }
        if(degree >= 150 && degree <= 210) {
            nextOrientation = DOWN;
        }
        if(degree >= 240 && degree <= 300) {
            nextOrientation = RIGHT;
        }
        _orientationChange();
    }

    private void _orientationChange() {
        if(currentOrientation == nextOrientation) {
            return;
        }
        Log.d("orientation", "change");
        if(nextOrientation == UP) {
            onOrientationChangeToUp();
        }
        if(nextOrientation == RIGHT) {
           onOrientationChangeToRight();
        }
        if(nextOrientation == DOWN) {
            onOrientationChangeToDown();
        }
        if(nextOrientation == LEFT) {
            onOrientationChangeToLeft();
        }
        currentOrientation = nextOrientation;
    }

    public void enable() {
        _orientationEventLitener.enable();
    }

    public void disable() {
        _orientationEventLitener.disable();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onOrientationChangeToUp();
    }
}
