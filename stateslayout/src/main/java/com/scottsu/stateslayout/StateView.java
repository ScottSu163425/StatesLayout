package com.scottsu.stateslayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 包名 com.scott.su.library
 * 描述
 * 作者 Su
 * 创建时间 2017/5/11 16:58
 **/
public class StateView extends FrameLayout
{
    private TextView mTipTextView;
    private ImageView mIconImageView;


    public StateView(@NonNull Context context)
    {
        super(context);
        initStateView(context);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initStateView(context);
    }

    public StateView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initStateView(context);
    }

    private void initStateView(Context context)
    {
        View content = LayoutInflater.from(context)
                .inflate(R.layout.layout_state_view,this,true);
        mIconImageView= (ImageView) content.findViewById(R.id.iv_icon_layout_state_view);
        mTipTextView= (TextView) content.findViewById(R.id.tv_tip_layout_state_view);
    }

    public void setTip(String tip){
        mTipTextView.setText(tip);
    }

    public void setTipColor(@ColorInt int color){
        mTipTextView.setTextColor(color);
    }

    public void setTipSize( float sizeSp){
        mTipTextView.setTextSize(sizeSp);
    }

    public void setIcon(@DrawableRes  int resId){
        mIconImageView.setImageResource(resId);
    }

    public void setIcon(Drawable drawable){
        mIconImageView.setImageDrawable(drawable);
    }

    public ImageView getIconImageView()
    {
        return mIconImageView;
    }
}
