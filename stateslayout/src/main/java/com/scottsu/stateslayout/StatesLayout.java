package com.scottsu.stateslayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;


/**
 * 包名 com.scott.su.library
 * 描述
 * 作者 Su
 * 创建时间 2017/5/10 16:57
 **/
public class StatesLayout extends CoordinatorLayout
{
    private static final float RATIO_ICON_WITH = 0.25f;
    private static final float RATIO_DEFAULT_LOADING_WHEEL_WITH = 0.20f;

    private static final int DEFAULT_LOADING_LAYOUT_RES = R.layout.layout_state_default_loading;
    private static final int DEFAULT_EMPTY_LAYOUT_RES = Integer.MAX_VALUE - 1;
    private static final int DEFAULT_ERROR_LAYOUT_RES = Integer.MAX_VALUE - 2;

    private boolean mDefaultShowIcon = true;

    private boolean mDefaultHasLoadingText;

    private int mDefaultStateBackgroundColor;

    private int mDefaultTipTextColor;

    private int mDefaultLoadingWheelColor;

    private
    @DrawableRes
    int mDefaultEmptyIconRes;

    private
    @DrawableRes
    int mDefaultErrorIconRes;

    private
    @LayoutRes
    int mLoadingLayoutRes;

    private
    @LayoutRes
    int mEmptyLayoutRes;

    private
    @LayoutRes
    int mErrorLayoutRes;

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;

    private String mDefaultLoadingText;

    private String mDefaultEmptyText;

    private String mDefaultErrorText;

    private float mDefaultTextSizeSp = 16;

//    private StateView mDefaultEmptyView, mDefaultErrorView;

    private ProgressWheel mDefaultLoadingProgressWheel;

    private TextView mDefaultLoadingTipTextView;

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

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bringToTopOfZ(this);
    }

    private void initAttrs(Context context, AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatesLayout);

        mDefaultShowIcon = typedArray.getBoolean(R.styleable.StatesLayout_sl_default_show_icon, true);

        mDefaultStateBackgroundColor = typedArray.getColor(R.styleable.StatesLayout_sl_default_state_background_color,
                context.getResources().getColor(R.color.default_state_background));

        mDefaultTipTextColor = typedArray.getColor(R.styleable.StatesLayout_sl_default_text_color,
                context.getResources().getColor(R.color.default_tip_text));

        float tipTextSizePx = typedArray.getDimensionPixelSize(R.styleable.StatesLayout_sl_default_text_size,
                ScreenUtil.sp2Px(context, mDefaultTextSizeSp));
        mDefaultTextSizeSp = ScreenUtil.px2Sp(context, tipTextSizePx);

        mDefaultLoadingWheelColor = typedArray.getColor(R.styleable.StatesLayout_sl_default_loading_wheel_color,
                context.getResources().getColor(R.color.default_loading_wheel_color));
        mDefaultEmptyIconRes = typedArray.getResourceId(R.styleable.StatesLayout_sl_default_empty_icon, R.drawable.ic_placeholder_state_empty);
        mDefaultErrorIconRes = typedArray.getResourceId(R.styleable.StatesLayout_sl_default_error_icon, R.drawable.ic_placeholder_state_error);
        mDefaultLoadingText = typedArray.getString(R.styleable.StatesLayout_sl_default_loading_text);
        mDefaultEmptyText = typedArray.getString(R.styleable.StatesLayout_sl_default_empty_text);
        mDefaultErrorText = typedArray.getString(R.styleable.StatesLayout_sl_default_error_text);
        mDefaultHasLoadingText = (mDefaultLoadingText != null) && (!mDefaultLoadingText.isEmpty());

        mLoadingLayoutRes = typedArray.getResourceId(R.styleable.StatesLayout_sl_loading_layout, DEFAULT_LOADING_LAYOUT_RES);
        mEmptyLayoutRes = typedArray.getResourceId(R.styleable.StatesLayout_sl_empty_layout, DEFAULT_EMPTY_LAYOUT_RES);
        mErrorLayoutRes = typedArray.getResourceId(R.styleable.StatesLayout_sl_error_layout, DEFAULT_ERROR_LAYOUT_RES);

        typedArray.recycle();
    }

    private void initLoadingView(Context context)
    {
        if (DEFAULT_LOADING_LAYOUT_RES == mLoadingLayoutRes)
        {
            initDefaultLoadingView(context);
        } else
        {
            mLoadingView = LayoutInflater.from(context).inflate(mLoadingLayoutRes, this, false);
        }

        mLoadingView.setOnClickListener(new OnClickListener()
        {
            public void onClick(View view)
            {

            }
        });

        bringToTopOfZ(mLoadingView);
    }

    private void initEmptyView(Context context)
    {
        if (DEFAULT_EMPTY_LAYOUT_RES == mEmptyLayoutRes)
        {
            mEmptyView = initDefaultEmptyView(context);
        } else
        {
            mEmptyView = LayoutInflater.from(context).inflate(mEmptyLayoutRes, this, false);
        }

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
        if (DEFAULT_ERROR_LAYOUT_RES == mErrorLayoutRes)
        {
            mErrorView = initDefaultErrorView(context);
        } else
        {
            mErrorView = LayoutInflater.from(context).inflate(mErrorLayoutRes, this, false);
        }

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

    private void initDefaultLoadingView(Context context)
    {
        //User default loading view.
        mLoadingView = LayoutInflater.from(context).inflate(R.layout.layout_state_default_loading, this, false);
        mDefaultLoadingProgressWheel = (ProgressWheel) mLoadingView.findViewById(R.id.progress_wheel_layout_state_default_loading);
        mDefaultLoadingTipTextView = (TextView) mLoadingView.findViewById(R.id.tv_tip_layout_state_default_loading);
        TextView textView = (TextView) mLoadingView.findViewById(R.id.tv_tip_layout_state_default_loading);

        if (mDefaultHasLoadingText)
        {
            mDefaultLoadingTipTextView.setVisibility(VISIBLE);
            textView.setText(TextUtils.isEmpty(mDefaultLoadingText) ? context.getString(R.string.default_state_tip_loading) : mDefaultLoadingText);
            textView.setTextColor(mDefaultTipTextColor);
            textView.setTextSize(mDefaultTextSizeSp);
        } else
        {
            mDefaultLoadingTipTextView.setVisibility(GONE);
        }

        int width = (int) (ScreenUtil.getScreenWidth(context) * RATIO_DEFAULT_LOADING_WHEEL_WITH);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mDefaultLoadingProgressWheel.getLayoutParams();
        params.width = width;
        params.height = width;

        mDefaultLoadingProgressWheel.setLayoutParams(params);
        mDefaultLoadingProgressWheel.setBarColor(mDefaultLoadingWheelColor);
        mDefaultLoadingProgressWheel.setCircleRadius(width);
        mDefaultLoadingProgressWheel.setBarWidth(width / 11);

        mLoadingView.setBackgroundColor(mDefaultStateBackgroundColor);
    }

    private View initDefaultEmptyView(Context context)
    {
        StateView defaultEmptyView = generateDefaultStateView(TextUtils.isEmpty(mDefaultEmptyText) ? context.getString(R.string.default_state_tip_empty) : mDefaultEmptyText,
                mDefaultEmptyIconRes);

        int width = (int) (ScreenUtil.getScreenWidth(context) * RATIO_ICON_WITH);

        ImageView icon = defaultEmptyView.getIconImageView();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) icon.getLayoutParams();
        params.width = width;
        params.height = width;
        icon.setLayoutParams(params);

        return defaultEmptyView;
    }

    private View initDefaultErrorView(Context context)
    {
        StateView defaultErrorView = generateDefaultStateView(TextUtils.isEmpty(mDefaultErrorText) ? context.getString(R.string.default_state_tip_error) : mDefaultErrorText,
                mDefaultErrorIconRes);

        int width = (int) (ScreenUtil.getScreenWidth(context) * RATIO_ICON_WITH);

        ImageView icon = defaultErrorView.getIconImageView();
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) icon.getLayoutParams();
        params.width = width;
        params.height = width;
        icon.setLayoutParams(params);

        return defaultErrorView;
    }

    private void bringToTopOfZ(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            //To keep this view always on the Top of axis Z.
            view.setTranslationZ(Float.MAX_VALUE);
        }
    }

    private StateView generateDefaultStateView(String tip, @DrawableRes int iconRes)
    {
        StateView stateView = new StateView(this.getContext());

        stateView.setTip(tip);
        stateView.setTipColor(mDefaultTipTextColor);
        stateView.setTipSize(mDefaultTextSizeSp);

        if (mDefaultShowIcon)
        {
            stateView.setIcon(iconRes);
        }

        stateView.setBackgroundColor(mDefaultStateBackgroundColor);

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
        showView(mEmptyView);
        hideView(mLoadingView);
        hideView(mErrorView);
    }

    public void showError()
    {
        showView(mErrorView);
        hideView(mEmptyView);
        hideView(mLoadingView);
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
            addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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

    public void setDefaultLoadingWheelColor(@ColorInt int colorInt)
    {
        if (mDefaultLoadingProgressWheel != null)
        {
            mDefaultLoadingProgressWheel.setBarColor(colorInt);
        }
    }

//    public void setDefaultEmptyIconRes(@DrawableRes int defaultEmptyIconRes)
//    {
//        mDefaultEmptyView.setIcon(defaultEmptyIconRes);
//    }
//
//    public void setDefaultErrorIconRes(@DrawableRes int defaultErrorIconRes)
//    {
//        mDefaultErrorView.setIcon(defaultErrorIconRes);
//    }
//
//    public void setDefaultEmptyTip(String defaultEmptyTip)
//    {
//        mDefaultEmptyView.setTip(defaultEmptyTip);
//    }
//
//    public void setDefaultErrorTip(String defaultErrorTip)
//    {
//        mDefaultErrorView.setTip(defaultErrorTip);
//    }
//
//    public void setDefaultTipTextSizeSp(float defaultTipTextSizeSp)
//    {
//        mDefaultTextSizeSp = defaultTipTextSizeSp;
//
//        mDefaultEmptyView.setTipSize(defaultTipTextSizeSp);
//        mDefaultErrorView.setTipSize(defaultTipTextSizeSp);
//
//        if (mDefaultLoadingTipTextView != null)
//        {
//            mDefaultLoadingTipTextView.setTextSize(defaultTipTextSizeSp);
//        }
//    }
//
//    public void setDefaultTipTextColor(@ColorInt int defaultTipTextColor)
//    {
//        mDefaultTipTextColor = defaultTipTextColor;
//
//        mDefaultEmptyView.setTipSize(defaultTipTextColor);
//        mDefaultErrorView.setTipSize(defaultTipTextColor);
//
//        if (mDefaultLoadingTipTextView != null)
//        {
//            mDefaultLoadingTipTextView.setTextSize(defaultTipTextColor);
//        }
//    }

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
