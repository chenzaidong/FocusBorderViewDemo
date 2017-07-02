package cc.myandroid.focusborderviewdemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * 自定义焦点框
 */
public class FocusBorderView extends AppCompatButton {

    private int moveCount;
    public static final int TRAN_DUR_ANIM = 300;
    private static final int DEFAULT_BACKGROUND_RESOURCE_ID = R.drawable.icon_focus_border;
    private int fixX = 0;
    private int fixY = 0;

    public FocusBorderView(Context context) {
        this(context, null);
    }

    public FocusBorderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusBorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FocusBorderView, defStyle, 0);
        setBackgroundResource(typedArray.getResourceId(R.styleable.FocusBorderView_focus_border_background, DEFAULT_BACKGROUND_RESOURCE_ID));
        typedArray.recycle();
        setFocusable(false);
        setClickable(false);
    }

    public void runTranslateAnimation(View toView) {
        runTranslateAnimation(toView, 1.0F, 1.0F);
    }

    public void runTranslateAnimation(View toView, final float scaleX, final float scaleY) {
        if (toView instanceof AbsListView) {
            AbsListView absListView = (AbsListView) toView;
            absListView.setOnItemSelectedListener(new OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    runTranslateAnimation(view, scaleX, scaleY);
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            toView = absListView.getSelectedView();
        }

        Rect fromRect = findLocationWithView(this);

        if (toView == null) {
            return;
        }
        Rect toRect = findLocationWithView(toView);

        int x = toRect.left - fromRect.left;
        int y = toRect.top - fromRect.top;

        int width = (int) (toView.getWidth() * scaleX);
        int height = (int) (toView.getHeight() * scaleY);
        int deltaX = (int) ((toView.getWidth() * Math.abs(scaleX - 1)) / 2.0f);
        int deltaY = (int) ((toView.getHeight() * Math.abs(scaleY - 1)) / 2.0f);

        x += fixX;
        y += fixY;
        flyWhiteBorder(width, height, x - deltaX, y - deltaY);
    }

    /**
     * 焦点框飞动、移动、变大
     *
     * @param width  框的宽(非放大后的)
     * @param height 框的高(非放大后的)
     * @param x      x坐标偏移量，相对于初始的框的中心点
     * @param y      y坐标偏移量，相对于初始的框的中心点
     */
    @SuppressLint("NewApi")
    private void flyWhiteBorder(int width, int height, float x, float y) {
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(ObjectAnimator.ofFloat(this, "x", x)).with(ObjectAnimator.ofFloat(this, "y", y))
                .with(ObjectAnimator.ofInt(this, "width", this.getWidth(), width))
                .with(ObjectAnimator.ofInt(this, "height", this.getHeight(), height));
        animSet.setInterpolator(new DecelerateInterpolator());
        animSet.addListener(flyListener);
        animSet.setDuration(TRAN_DUR_ANIM).start();
    }

    /**
     * 获取View的位置
     *
     * @param view 获取的控件
     * @return 位置
     */
    public Rect findLocationWithView(View view) {
        ViewGroup root = (ViewGroup) this.getParent();
        Rect rect = new Rect();
        view.getDrawingRect(rect);
        root.offsetDescendantRectToMyCoords(view, rect);
        return rect;
    }

    private Animator.AnimatorListener flyListener = new Animator.AnimatorListener() {

        public void onAnimationCancel(Animator arg0) {
        }

        public void onAnimationEnd(Animator arg0) {
            moveCount++;
            setVisibility(View.VISIBLE);
        }

        public void onAnimationRepeat(Animator arg0) {
        }

        public void onAnimationStart(Animator arg0) {
            if (moveCount != 0 && getVisibility() != View.VISIBLE) {
                setVisibility(View.VISIBLE);
            }
        }
    };

    public int getFixX() {
        return fixX;
    }

    public void setFixX(int fixX) {
        this.fixX = fixX;
    }

    public int getFixY() {
        return fixY;
    }

    public void setFixY(int fixY) {
        this.fixY = fixY;
    }
}