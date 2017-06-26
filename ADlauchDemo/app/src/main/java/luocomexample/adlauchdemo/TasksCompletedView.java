package luocomexample.adlauchdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by DQ on 2017/6/26.
 */
public class TasksCompletedView extends View {
    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;
    // 当前进度
    private int mProgress;
    // 总进度
    private int mTotalProgress = 100;

    public TasksCompletedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initVariable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mXCenter=getWidth()/2;
        mYCenter=getHeight()/2;

        canvas.drawCircle(mXCenter,mYCenter,mRadius,mCirclePaint);

        if (mProgress>=0){
            RectF oval=new RectF();
            oval.left=(mXCenter-mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mXCenter + mRingRadius;
            oval.bottom = mYCenter + mRingRadius;
            canvas.drawArc(oval,-90,((float) mProgress / mTotalProgress) * 360,false,mRingPaint);

            String txt = mTotalProgress - mProgress + "";
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);
        }

    }


    public void setProgress(int progress) {
        mProgress = progress;
//		invalidate();
        postInvalidate(); //刷新界面
    }

    public void setTotalProgress(int progress) {
        mTotalProgress = progress;
    }

    //自定义圆初始化
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        mRadius = typedArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typedArray.getDimension(R.styleable.TasksCompletedView_strokeWitdth, 10);
        mCircleColor = typedArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typedArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);  //抗锯齿
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);  //实心

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);  //空心
        mRingPaint.setStrokeWidth(mStrokeWidth);

        mTextPaint=new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.parseColor("#666666"));
        mTextPaint.setTextSize(getResources().getDimension(R.dimen.x20));

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);
    }


}
