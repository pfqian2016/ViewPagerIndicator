package com.android.irish.myviewpager.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.irish.myviewpager.ViewPagerFragment;

import java.util.List;


/**
 * Created by Irish on 2016/5/16.
 */
public class ViewPagerIndicator extends LinearLayout{
    private static final float RADIO_TRIANGLE_WIDTH=1/6F;
    private static final int COLOR_TEXT_NORMAL=0xccffffff;
    private static final int COLOR_TEXT_HIGHLIGHT=0xffffffff;

    private int mTriangleWidth;
    private int mTriangleHeight;

    private int mTranslationX;
    private int mInitTranslationX;

    private Paint mPaint;
    private Path mPath;
    private ViewPager mViewPager;
    private List<String> mTitles;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX, getHeight());
        canvas.drawPath(mPath,mPaint);
        canvas.restore();


        super.dispatchDraw(canvas);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint=new Paint();
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setPathEffect(new CornerPathEffect(3));


    }

    /**
     * Indicator scroll with finger's move
     * @param position
     * @param positionOffset
     */
    private void scroll(int position, float positionOffset) {
        int tabWidth=getWidth()/3;
        mTranslationX= (int) (tabWidth*(position+positionOffset));

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth= (int) (w/3*RADIO_TRIANGLE_WIDTH);
        mInitTranslationX=w/3/2-mTriangleWidth/2;
        initTriangle();
    }

    private void initTriangle() {
        mTriangleHeight=mTriangleWidth/2;
        mPath=new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }
    public void setViewPager(ViewPager viewPager,int position){
        mViewPager=viewPager;
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //tabWidth*offset+tabWidth*position
                scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                highlightTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setCurrentItem(position);
        highlightTextColor(position);
    }

    private void highlightTextColor(int position){
        resetTextColor();
        View view=getChildAt(position);
        if(view instanceof TextView){
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHT);
        }
    }

    private void resetTextColor() {
        for(int i=0;i<getChildCount();i++){
            View view=getChildAt(i);
            if(view instanceof TextView){
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
            }
        }
    }

    private void setItemClickEvent(){
        int count=getChildCount();

        for(int i=0;i<count;i++){
            final int j=i;
            View view=getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }


    public void setTabItemTitles(List<String> titles) {
        if(titles!=null&&titles.size()>0){
            this.removeAllViews();
            mTitles=titles;
            for(String title:mTitles){
                addView(generateTextView(title));
            }
        }
        setItemClickEvent();
    }

    private View generateTextView(String title) {
        TextView tv=new TextView(getContext());
        LinearLayout.LayoutParams lp=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.width=getScreenWidth(getContext())/3;
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#cccccc"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setLayoutParams(lp);
        return tv;
    }

    private static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();

    }
}
