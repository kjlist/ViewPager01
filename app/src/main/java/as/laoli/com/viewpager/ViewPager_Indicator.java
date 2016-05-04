package as.laoli.com.viewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by laoli on 16/5/3.
 */
public class ViewPager_Indicator extends LinearLayout {
    private Paint mPaint;
    private Path mPath;
    private int mTriangleWidth;
    private int mTriangleHeight;
    private static final float RADIO_TRANGLE_WIDTH=1/6f;
    private int mInitTranslationX;
    private int mTranslationX;
    private int mVisibleCount;
    private static final int COUNT_DEFAULT=4;
    private List<String> mTitles;
    private static final int colors=000000;

    public ViewPager_Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取可见tab的数量
        TypedArray a=context.obtainStyledAttributes
                (attrs,R.styleable.ViewPager_Indicator);
        mVisibleCount=a.getInt(R.styleable.ViewPager_Indicator_visible_tab_count,COUNT_DEFAULT);
        if (mVisibleCount<0){
            mVisibleCount=COUNT_DEFAULT;
        }
        a.recycle();
        //初始化画笔
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ff4fff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setPathEffect(new CornerPathEffect(3));
    }
//在xml加载完成后回调
    @Override
    protected void onFinishInflate() {

        super.onFinishInflate();
        int cCount=getChildCount();
        if (cCount==0){
            return;
        }
        for (int i=0;i<cCount;i++){
            View v=getChildAt(i);
            LinearLayout.LayoutParams lp = (LayoutParams) v.getLayoutParams();
            //因为子view在linearLayout中
            lp.weight=0;
            lp.width=getScreenWidth()/mVisibleCount;
            v.setLayoutParams(lp);
        }
    }

    private int getScreenWidth() {
        WindowManager wm= (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX+mTranslationX,getHeight()+2);
        canvas.drawPath(mPath,mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    public ViewPager_Indicator(Context context) {
        super(context,null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth= (int) (w/mVisibleCount*RADIO_TRANGLE_WIDTH);
        mInitTranslationX=w/mVisibleCount/2-mTriangleWidth/2;
        initTriangle();
    }
//初始化三角形
    private void initTriangle() {
        mTriangleHeight=mTriangleWidth/2;
        mPath=new Path();
        mPath.moveTo(0,0);
        mPath.lineTo(mTriangleWidth,0);
        mPath.lineTo(mTriangleWidth/2,-mTriangleHeight);
        mPath.close();
    }
//指示器跟随手指进行滚动
    public void scroll(int position, float positionOffset) {
        int tabWidth=getWidth()/mVisibleCount;
        mTranslationX= (int) (tabWidth*(positionOffset+position));
        //容器移动,当tab移动至最后一个时
        if (position>=(mVisibleCount-2)&&positionOffset>0&&
                getChildCount()>mVisibleCount){
            if (mVisibleCount!=1){
            this.scrollTo((position-(mVisibleCount-2))*tabWidth+
                    (int)(tabWidth*positionOffset),0);
            }
            else
            {
                this.scrollTo(position*tabWidth+(int)(tabWidth*positionOffset),0);

            }

        }
        invalidate();
    }
    public void setTabItemTitles(List<String>titles){
        if (titles!=null&&titles.size()>0){
            this.removeAllViews();
            mTitles=titles;
            for (String title:mTitles){
                addView(generateView(title));
            }
        }
    }
//根据title创建tab
    private View generateView(String title) {
        TextView tv=new TextView(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width=getScreenWidth()/mVisibleCount;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        tv.setTextColor(Color.BLACK);
        tv.setLayoutParams(lp);
        return tv;
    }
    //设置可见的tab数量
    public void setmVisibleCount(int count){
        mVisibleCount=count;
    }
}
