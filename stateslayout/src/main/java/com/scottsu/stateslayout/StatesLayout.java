package com.scottsu.stateslayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


/**
 * 包名 com.scott.su.library
 * 描述
 * 作者 Su
 * 创建时间 2017/5/10 16:57
 **/
public class StatesLayout extends CoordinatorLayout
{
    private int mStateBackgroundColor;
    private int mTipTextColor;
    private int mLoadingWheelColor;
    private int mEmptyIconRes;
    private int mErrorIconRes;
    private int mLoadingLayoutRes;
    private String mLoadingTip;
    private String mEmptyTip;
    private String mErrorTip;
    private float mTipTextSizeSp = 12;
    private View mLoadingView;
    private StateView mEmptyView, mErrorView;
    private StatesLayoutCallback mCallback;


    public StatesLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public StatesLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        initAttrs(context, attrs);
        initLoadingView(context);
        initEmptyView(context);
        initErrorView(context);
        bringToTopOfZ(this);
    }

    private void initAttrs(Context context, AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatesLayout);

        mStateBackgroundColor = typedArray.getColor(R.styleable.StatesLayout_sl_state_background_color,
                context.getResources().getColor(R.color.default_state_background));
        mTipTextColor = typedArray.getColor(R.styleable.StatesLayout_sl_tip_text_color,
                context.getResources().getColor(R.color.default_tip_text));

        float tipTextSizePx = typedArray.getDimensionPixelSize(R.styleable.StatesLayout_sl_tip_text_size,
                ScreenUtil.sp2Px(context, mTipTextSizeSp));
        mTipTextSizeSp = ScreenUtil.px2Sp(context, tipTextSizePx);

        mLoadingWheelColor = typedArray.getColor(R.styleable.StatesLayout_sl_loading_wheel_color,
                context.getResources().getColor(R.color.default_loading_wheel_color));
        mEmptyIconRes = typedArray.getResourceId(R.styleable.StatesLayout_sl_empty_icon, R.drawable.ic_placeholder_state_empty);
        mErrorIconRes = typedArray.getResourceId(R.styleable.StatesLayout_sl_error_icon, R.drawable.ic_placeholder_state_error);
        mLoadingLayoutRes = typedArray.getResourceId(R.styleable.StatesLayout_sl_loading_layout, R.layout.layout_state_default_loading);
        mLoadingTip = typedArray.getString(R.styleable.StatesLayout_sl_loading_tip);
        mEmptyTip = typedArray.getString(R.styleable.StatesLayout_sl_empty_tip);
        mErrorTip = typedArray.getString(R.styleable.StatesLayout_sl_error_tip);

        typedArray.recycle();
    }

    private void initLoadingView(Context context)
    {
        if (R.layout.layout_state_default_loading == mLoadingLayoutRes)
        {
            initDefaultLoadingView(context);
        } else
        {
            mLoadingView = LayoutInflater.from(context).inflate(mLoadingLayoutRes, this, false);
        }

        bringToTopOfZ(mLoadingView);
    }

    private void initDefaultLoadingView(Context context)
    {
        //User default loading view.
        mLoadingView = LayoutInflater.from(context).inflate(R.layout.layout_state_default_loading, this, false);
        ProgressWheel progressWheel = (ProgressWheel) mLoadingView.findViewById(R.id.progress_wheel_layout_state_default_loading);
        TextView textView = (TextView) mLoadingView.findViewById(R.id.tv_tip_layout_state_default_loading);
        textView.setText(TextUtils.isEmpty(mLoadingTip) ? context.getString(R.string.default_state_tip_loading) : mLoadingTip);
        textView.setTextColor(mTipTextColor);
        textView.setTextSize(mTipTextSizeSp);

        progressWheel.setBarColor(mLoadingWheelColor);
        mLoadingView.setBackgroundColor(mStateBackgroundColor);
    }

    private void initEmptyView(Context context)
    {
        mEmptyView = generateStateView(TextUtils.isEmpty(mEmptyTip) ? context.getString(R.string.default_state_tip_empty) : mEmptyTip,
                mEmptyIconRes);
        mEmptyView.setBackgroundColor(mStateBackgroundColor);
        mEmptyView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCallback().onEmptyClick(v);
            }
        });

        bringToTopOfZ(mEmptyView);
    }

    private void initErrorView(Context context)
    {
        mErrorView = generateStateView(TextUtils.isEmpty(mErrorTip) ? context.getString(R.string.default_state_tip_error) : mErrorTip,
                mErrorIconRes);
        mErrorView.setBackgroundColor(mStateBackgroundColor);
        mErrorView.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCallback().onErrorClick(v);
            }
        });

        bringToTopOfZ(mErrorView);
    }

    private void bringToTopOfZ(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            //To keep this view always on the Top of axis Z.
            view.setTranslationZ(Float.MAX_VALUE);
        }
    }

    private StateView generateStateView(String tip, @DrawableRes int iconRes)
    {
        StateView stateView = new StateView(this.getContext());
        stateView.setTip(tip);
        stateView.setIcon(iconRes);
        stateView.setBackgroundColor(mStateBackgroundColor);
        stateView.setTipColor(mTipTextColor);
        stateView.setTipSize(mTipTextSizeSp);
        return stateView;
    }

    public void showLoading()
    {
        showView(mLoadingView);
        hideView(mEmptyView);
        hideView(mErrorView);
    }

    public void showEmpty()
    {
        showEmpty("");
    }

    public void showError()
    {
        showError("");
    }

    public void showEmpty(String tip)
    {
        showView(mEmptyView);
        hideView(mLoadingView);
        hideView(mErrorView);

        if (!TextUtils.isEmpty(tip))
        {
            mEmptyView.setTip(tip);
        }
    }

    public void showError(String tip)
    {
        showView(mErrorView);
        hideView(mEmptyView);
        hideView(mLoadingView);

        if (!TextUtils.isEmpty(tip))
        {
            mErrorView.setTip(tip);
        }
    }

    public void showContent()
    {
        hideView(mErrorView);
        hideView(mEmptyView);
        hideView(mLoadingView);
    }

    private void showView(View view)
    {
        if (view.getParent() == null)
        {
            addView(view, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        if (view.getVisibility() != VISIBLE)
        {
            view.setVisibility(VISIBLE);
        }
    }

    private void hideView(View view)
    {
        if (view.getParent() == null)
        {
            return;
        }

        if (view.getVisibility() == VISIBLE)
        {
            view.setVisibility(GONE);
        }
    }

    public void setCallback(StatesLayoutCallback mCallback)
    {
        this.mCallback = mCallback;
    }

    private StatesLayoutCallback getCallback()
    {
        if (mCallback == null)
        {
            mCallback = new StatesLayoutCallback()
            {
                @Override
                public void onEmptyClick(View view)
                {

                }

                @Override
                public void onErrorClick(View view)
                {

                }
            };
        }
        return mCallback;
    }

    public interface StatesLayoutCallback
    {
        void onEmptyClick(View view);

        void onErrorClick(View view);
    }


}
