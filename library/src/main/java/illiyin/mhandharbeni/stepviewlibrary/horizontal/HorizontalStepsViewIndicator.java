package illiyin.mhandharbeni.stepviewlibrary.horizontal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import illiyin.mhandharbeni.stepviewlibrary.R;
import illiyin.mhandharbeni.stepviewlibrary.bean.StepBean;

/**
 * Created by root on 2/8/18.
 */

public class HorizontalStepsViewIndicator extends View
{
    private int defaultStepIndicatorNum = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());

    private float mCompletedLineHeight;
    private float mCircleRadius;

    private Drawable mCompleteIcon;
    private Drawable mAttentionIcon;
    private Drawable mDefaultIcon;
    private float mCenterY;
    private float mLeftY;
    private float mRightY;

    private List<StepBean> mStepBeanList ;
    private int mStepNum = 0;
    private float mLinePadding;

    private List<Float> mCircleCenterPointPositionList;
    private Paint mUnCompletedPaint;
    private Paint mCompletedPaint;
    private int mUnCompletedLineColor = ContextCompat.getColor(getContext(), R.color.uncompleted_color);
    private int mCompletedLineColor = Color.WHITE;
    private PathEffect mEffects;
    private int mComplectingPosition;


    private Path mPath;

    private OnDrawIndicatorListener mOnDrawListener;
    private int screenWidth;

    public void setOnDrawListener(OnDrawIndicatorListener onDrawListener)
    {
        mOnDrawListener = onDrawListener;
    }

    public float getCircleRadius()
    {
        return mCircleRadius;
    }


    public HorizontalStepsViewIndicator(Context context)
    {
        this(context, null);
    }

    public HorizontalStepsViewIndicator(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public HorizontalStepsViewIndicator(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init
     */
    private void init()
    {
        mStepBeanList = new ArrayList<>();
        mPath = new Path();
        mEffects = new DashPathEffect(new float[]{8, 8, 8, 8}, 1);

        mCircleCenterPointPositionList = new ArrayList<>();

        mUnCompletedPaint = new Paint();
        mCompletedPaint = new Paint();
        mUnCompletedPaint.setAntiAlias(true);
        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mUnCompletedPaint.setStyle(Paint.Style.STROKE);
        mUnCompletedPaint.setStrokeWidth(2);

        mCompletedPaint.setAntiAlias(true);
        mCompletedPaint.setColor(mCompletedLineColor);
        mCompletedPaint.setStyle(Paint.Style.STROKE);
        mCompletedPaint.setStrokeWidth(2);

        mUnCompletedPaint.setPathEffect(mEffects);
        mCompletedPaint.setStyle(Paint.Style.FILL);

        mCompletedLineHeight = 0.05f * defaultStepIndicatorNum;
        mCircleRadius = 0.28f * defaultStepIndicatorNum;
        mLinePadding = 0.85f * defaultStepIndicatorNum;

        mCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.complted);
        mAttentionIcon = ContextCompat.getDrawable(getContext(), R.drawable.attention);
        mDefaultIcon = ContextCompat.getDrawable(getContext(), R.drawable.default_icon);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = defaultStepIndicatorNum * 2;
        if(MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(widthMeasureSpec))
        {
            screenWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        int height = defaultStepIndicatorNum;
        if(MeasureSpec.UNSPECIFIED != MeasureSpec.getMode(heightMeasureSpec))
        {
            height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
        }
        width = (int) (mStepNum * mCircleRadius * 2 - (mStepNum - 1) * mLinePadding);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterY = 0.5f * getHeight();
        mLeftY = mCenterY - (mCompletedLineHeight / 2);
        mRightY = mCenterY + mCompletedLineHeight / 2;

        mCircleCenterPointPositionList.clear();
        for(int i = 0; i < mStepNum; i++)
        {
            float paddingLeft = (screenWidth - mStepNum * mCircleRadius * 2 - (mStepNum - 1) * mLinePadding) / 2;
            mCircleCenterPointPositionList.add(paddingLeft + mCircleRadius + i * mCircleRadius * 2 + i * mLinePadding);
        }

        if(mOnDrawListener!=null)
        {
            mOnDrawListener.ondrawIndicator();
        }
    }

    @Override
    protected synchronized void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(mOnDrawListener!=null)
        {
            mOnDrawListener.ondrawIndicator();
        }
        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mCompletedPaint.setColor(mCompletedLineColor);

        for(int i = 0; i < mCircleCenterPointPositionList.size() -1; i++)
        {
            final float preComplectedXPosition = mCircleCenterPointPositionList.get(i);
            final float afterComplectedXPosition = mCircleCenterPointPositionList.get(i + 1);

            if(i <= mComplectingPosition&&mStepBeanList.get(0).getState()!=StepBean.STEP_UNDO)//判断在完成之前的所有点
            {
                canvas.drawRect(preComplectedXPosition + mCircleRadius - 10, mLeftY, afterComplectedXPosition - mCircleRadius + 10, mRightY, mCompletedPaint);
            } else
            {
                mPath.moveTo(preComplectedXPosition + mCircleRadius, mCenterY);
                mPath.lineTo(afterComplectedXPosition - mCircleRadius, mCenterY);
                canvas.drawPath(mPath, mUnCompletedPaint);
            }
        }
        for(int i = 0; i < mCircleCenterPointPositionList.size(); i++)
        {
            final float currentComplectedXPosition = mCircleCenterPointPositionList.get(i);
            Rect rect = new Rect((int) (currentComplectedXPosition - mCircleRadius), (int) (mCenterY - mCircleRadius), (int) (currentComplectedXPosition + mCircleRadius), (int) (mCenterY + mCircleRadius));

            StepBean stepsBean = mStepBeanList.get(i);

            if(stepsBean.getState()==StepBean.STEP_UNDO)
            {
                mDefaultIcon.setBounds(rect);
                mDefaultIcon.draw(canvas);
            }else if(stepsBean.getState()==StepBean.STEP_CURRENT)
            {
                mCompletedPaint.setColor(Color.WHITE);
                canvas.drawCircle(currentComplectedXPosition, mCenterY, mCircleRadius * 1.1f, mCompletedPaint);
                mAttentionIcon.setBounds(rect);
                mAttentionIcon.draw(canvas);
            }else if(stepsBean.getState()==StepBean.STEP_COMPLETED)
            {
                mCompleteIcon.setBounds(rect);
                mCompleteIcon.draw(canvas);
            }
        }
    }

    public List<Float> getCircleCenterPointPositionList()
    {
        return mCircleCenterPointPositionList;
    }

    public void setStepNum(List<StepBean> stepsBeanList)
    {
        this.mStepBeanList = stepsBeanList;
        mStepNum = mStepBeanList.size();

        if(mStepBeanList!=null&&mStepBeanList.size()>0)
        {
            for(int i = 0;i<mStepNum;i++)
            {
                StepBean stepsBean = mStepBeanList.get(i);
                {
                    if(stepsBean.getState()==StepBean.STEP_COMPLETED)
                    {
                        mComplectingPosition = i;
                    }
                }
            }
        }

        requestLayout();
    }

    public void setUnCompletedLineColor(int unCompletedLineColor)
    {
        this.mUnCompletedLineColor = unCompletedLineColor;
    }

    public void setCompletedLineColor(int completedLineColor)
    {
        this.mCompletedLineColor = completedLineColor;
    }

    public void setDefaultIcon(Drawable defaultIcon)
    {
        this.mDefaultIcon = defaultIcon;
    }

    public void setCompleteIcon(Drawable completeIcon)
    {
        this.mCompleteIcon = completeIcon;
    }

    public void setAttentionIcon(Drawable attentionIcon)
    {
        this.mAttentionIcon = attentionIcon;
    }

    public interface OnDrawIndicatorListener
    {
        void ondrawIndicator();
    }
}