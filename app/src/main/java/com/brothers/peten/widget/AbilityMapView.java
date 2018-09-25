package com.brothers.peten.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liujingxiu
 * @date 2018-7-3
 * @desc 能力分布图
 */

public class AbilityMapView extends View {

    /**
     * 手指触屏时，距离有效点的有效范围
     */
    private static final float AVAILABLE_IN_SIZE = 22;

    /**
     * 能力层级
     */
    private int mLayer = 5;
    /**
     * 能力数组
     */
    private String[] mAbilityArray;
    /**
     * 各能力分值
     */
    private int[] mAbilityScore;
    /**
     * 各能力满分分值
     */
    private int mAbilityFullMark = 100;
    /**
     * 中心x坐标
     */
    private int mCenterX;
    /**
     * 中心Y坐标
     */
    private int mCenterY;
    /**
     * 大圆半径
     */
    private int mRadius;
    /**
     * 各项能力颜色值
     */
    private int[] mColors = {Color.BLUE, Color.BLACK, Color.CYAN, Color.GREEN, Color.MAGENTA};
    /**
     * 主色调
     */
    private int mThemeColor = Color.parseColor("#7228D1");
    /**
     * 存储多边形顶点数组的数组
     */
    private List<List<PointF>> mPointsArrayList;
    /**
     * 存储能力点的数组
     */
    private ArrayList<PointF> mAbilityPoints;
    /**
     * 上下文
     */
    private Context context;

    public AbilityMapView(Context context) {
        super(context);
        this.context = context;
    }

    public AbilityMapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int tViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        int tViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        int tPadding = dip2px(70);
        if (tViewWidth < tViewHeight) {
            tViewHeight = tViewWidth;
        } else {
            tViewWidth = tViewHeight;
        }
        setMeasuredDimension(tViewWidth, tViewHeight);
        mCenterX = tViewWidth / 2;
        mCenterY = tViewWidth / 2;
        mRadius = tViewWidth / 2 - tPadding;
        initData();
    }

    private void initData() {
        // 算出间隔角度
        float tAngle = (float) ((2 * Math.PI) / mLayer);
        // 初始化集合存储各个顶点
        mPointsArrayList = new ArrayList<>();
        float x;
        float y;
        float tFirstRadius = mRadius * 4 / 10;
        for (int i = 0; i < mLayer; i++) {
            List<PointF> tPointFList = new ArrayList<>();
            float r = tFirstRadius + (mRadius - tFirstRadius) * i / (mLayer - 1);
            for (int j = 0; j < mAbilityArray.length; j++) {
                // 此处减去π／2是为了让点逆时针旋转90度（为了让图是立着的 更加美观）
                x = mCenterX + (float) (r * Math.cos(j * tAngle - Math.PI / 2));
                y = mCenterY + (float) (r * Math.sin(j * tAngle - Math.PI / 2));
                tPointFList.add(new PointF(x, y));
            }
            mPointsArrayList.add(tPointFList);
        }
        mAbilityPoints = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        drawPolygon(canvas, paint);
        if (mAbilityArray.length > 0) {
            drawAbilityLine(canvas, paint, mAbilityArray.length);
            drawAbilityMap(canvas, paint);
        }
    }

    /**
     * 绘制文字
     *
     * @param canvas
     * @param x
     * @param y
     * @param text
     * @param color
     * @param paint
     */
    private void drawText(Canvas canvas, float x, float y, String text, int color, Paint paint) {
        paint.reset();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(color);
        //绘制圆点标记
        canvas.drawCircle(x, y, dip2px(3), paint);
        paint.setTextSize(dip2px(15));
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int w = rect.width(); //获取宽度
        int h = rect.height();//获取高度
        float textX = 0;
        float textY = 0;

        //正上方
        if (Math.abs(mCenterX - x) < 2 && y < mCenterY) {
            textX = x - w / 2;
            textY = y - h - dip2px(2);
            //正下方
        } else if (Math.abs(mCenterX - x) < 2 && y > mCenterY) {
            textX = x - w / 2;
            textY = y + h + dip2px(2);
            //坐标点位于右上
        } else if (x >= mCenterX && y < mCenterY) {
            textX = x + dip2px(5);
            textY = y - h / 2;
            //坐标点位于右下
        } else if (x > mCenterX && y > mCenterY) {
            textX = x;
            textY = y + h + dip2px(5);
            //坐标点位于左下
        } else if (x < mCenterX && y > mCenterY) {
            textX = x - w - dip2px(5);
            textY = y + h + dip2px(5);
            //坐标点位于左上
        } else if (x < mCenterX && y < mCenterY) {
            textX = x - w - dip2px(5);
            textY = y - h / 2;
        }

        canvas.drawText(text, textX, textY, paint);
    }

    /**
     * 绘制能力分布
     *
     * @param canvas
     * @param paint
     */
    private void drawAbilityMap(Canvas canvas, Paint paint) {
        int angle = 360 / mAbilityArray.length;//两种能力虚线之间的夹角
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        for (int i = 0; i < mAbilityArray.length; i++) {
            Path path = new Path();
            paint.setColor(Color.parseColor("#99A2A8FF"));
            path.moveTo(mCenterX, mCenterY);
            float radius = (mAbilityScore[i] * mRadius) / mAbilityFullMark;//计算每个分值在大圆半径上的长度
            //计算分值在能力虚线上的坐标
            float tempX = (float) (mCenterX + radius * Math.cos(2 * Math.PI * (-90 + angle * i) / 360));
            float tempY = (float) (mCenterY + radius * Math.sin(2 * Math.PI * (-90 + angle * i) / 360));
            path.lineTo(tempX, tempY);
            int index = i + 1;
            if (index >= mAbilityScore.length) {
                index = 0;
            }
            float endRadius = (mAbilityScore[index] * mRadius) / mAbilityFullMark;//计算每个分值在大圆半径上的长度
            //计算分值在能力虚线上的坐标
            float endX = (float) (mCenterX + endRadius * Math.cos(2 * Math.PI * (-90 + angle * (index)) / 360));
            float endY = (float) (mCenterY + endRadius * Math.sin(2 * Math.PI * (-90 + angle * (index)) / 360));
            //构造线性渐变
//            LinearGradient lg=new LinearGradient(tempX,tempY,endX,endY,Color.parseColor("#FFDF9C28"),
//                    Color.parseColor("#FFECBB67"), Shader.TileMode.MIRROR);
//            paint.setShader(lg);
            path.lineTo(endX, endY);
            path.close();
            canvas.drawPath(path, paint);
            //计算标记点坐标
            float flagX = (float) (mCenterX + (mRadius + 30) * Math.cos(2 * Math.PI * (-90 + angle * (i)) / 360));
            float flagY = (float) (mCenterY + (mRadius + 30) * Math.sin(2 * Math.PI * (-90 + angle * (i)) / 360));
            drawText(canvas, mPointsArrayList.get(mPointsArrayList.size() - 1).get(i).x,
                    mPointsArrayList.get(mPointsArrayList.size() - 1).get(i).y, mAbilityArray[i], mThemeColor, paint);
        }
    }

    /**
     * 绘制五角层级图
     */
    private void drawPolygon(Canvas canvas, Paint paint) {
        canvas.save();
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        Path tPath = new Path();
        for (int i = 0; i < mLayer; i++) {
            switch (i) {
                case 0:
                    paint.setColor(mThemeColor);
                    break;
                case 1:
                    paint.setColor(mThemeColor);
                    break;
                case 2:
                    paint.setColor(mThemeColor);
                    break;
                case 3:
                    paint.setColor(mThemeColor);
                    break;
                case 4:
                    paint.setColor(mThemeColor);
                    break;
                default:
            }
            for (int j = mPointsArrayList.size() - 1; j > -1; j--) {
                float x = mPointsArrayList.get(i).get(j).x;
                float y = mPointsArrayList.get(i).get(j).y;
                if (j == mPointsArrayList.size() - 1) {
                    tPath.moveTo(x, y);
                } else {
                    tPath.lineTo(x, y);
                }
            }
            tPath.close();
            canvas.drawPath(tPath, paint);
            tPath.reset();
        }
        canvas.restore();
    }

    /**
     * 绘制能力虚线
     *
     * @param canvas
     * @param paint
     * @param abilityNumber
     */
    private void drawAbilityLine(Canvas canvas, Paint paint, int abilityNumber) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(mThemeColor);
        paint.setAntiAlias(true);//抗锯齿
//        paint.setPathEffect(new DashPathEffect(new float[] {15, 15}, 0));//设置虚线15sp实线，15sp虚线
        int angle = 360 / abilityNumber;//两种能力虚线之间的夹角
        float X1;//能力虚线在大圆上的X坐标
        float Y1;//能力虚线在大圆上的Y坐标
        for (int i = 0; i < abilityNumber; i++) {
            X1 = (float) (mCenterX + mRadius * Math.cos(2 * Math.PI * (-90 + angle * i) / 360));
            Y1 = (float) (mCenterY + mRadius * Math.sin(2 * Math.PI * (-90 + angle * i) / 360));
            Path mPath = new Path();
            mPath.reset();
            mPath.moveTo(mCenterX, mCenterY);//设置中心点为虚线路径起点
            mPath.lineTo(X1, Y1);//设置圆上坐标点为虚线路径终点
            canvas.drawPath(mPath, paint);
        }
    }

    //设置能力分级
    public void setLayer(int layer) {
        this.mLayer = layer;
        postInvalidate();
    }

    public void setAbilityList(String[] mAbilityList) {
        this.mAbilityArray = mAbilityList;
        postInvalidate();
    }

    public int[] getAbilityScore() {
        return mAbilityScore;
    }

    public void setAbilityScore(int[] abilityScore) {
        this.mAbilityScore = abilityScore;
        postInvalidate();
    }

    public void setAbilityFullMark(int abilityFullMark) {
        this.mAbilityFullMark = abilityFullMark;
        postInvalidate();
    }

    public void setColors(int[] colors) {
        this.mColors = colors;
        postInvalidate();
    }

    public void setThemeColor(int colorInt) {
        mThemeColor = colorInt;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public int dip2px(float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float tTouchX = event.getX();
        float tTouchY = event.getY();

        if (MotionEvent.ACTION_MOVE == event.getAction()) {
            for (int i = 0; i < mPointsArrayList.size(); i++) {
                List<PointF> tPointList = mPointsArrayList.get(i);
                for (int j = 0; j < tPointList.size(); j++) {
                    if (Math.abs(tPointList.get(j).x - tTouchX) < AVAILABLE_IN_SIZE
                            && Math.abs(tPointList.get(j).y - tTouchY) < AVAILABLE_IN_SIZE) {
                        mAbilityScore[j] = 40 + i * 15;
                        invalidate();
                        return true;
                    }
                }
            }
        }

        return true;
    }
}
