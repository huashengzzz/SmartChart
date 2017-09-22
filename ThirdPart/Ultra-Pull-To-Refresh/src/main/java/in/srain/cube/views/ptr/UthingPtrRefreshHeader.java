package in.srain.cube.views.ptr;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import in.srain.cube.views.ptr.indicator.PtrIndicator;
import in.srain.cube.views.ptr.util.PtrLocalDisplay;

/**
 * Created by linhe on 2016/4/28.
 */
public class UthingPtrRefreshHeader extends FrameLayout implements PtrUIHandler {

    private ImageView mImageView;
    private AnimationDrawable animationDrawable;
    private int mImgWidth;

    public UthingPtrRefreshHeader(Context context) {
        super(context);
        initViews(null);
    }

    public UthingPtrRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(attrs);
    }

    public UthingPtrRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(attrs);
    }

    protected void initViews(AttributeSet attrs) {
        View parent = View.inflate(getContext(), R.layout.uthing_ptr_refresh_default_header, this);
        mImageView = (ImageView) parent.findViewById(R.id.iv_ptr_anim);
        mImageView.setImageResource(R.drawable.ptr_refresh_head_animation);
        animationDrawable = (AnimationDrawable) mImageView.getDrawable();
        PtrLocalDisplay.init(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        if (animationDrawable != null) {
            animationDrawable.start();
        }
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();

        switch (status) {
            case PtrFrameLayout.PTR_STATUS_PREPARE:
                if (currentPos <= mOffsetToRefresh) {
                    float percent = currentPos / (float) mOffsetToRefresh;
                    mImgWidth = mImageView.getMeasuredWidth();
                    float imgLeft = PtrLocalDisplay.SCREEN_WIDTH_PIXELS - percent * (PtrLocalDisplay.SCREEN_WIDTH_PIXELS / 2.0f + mImgWidth / 2.0f);
                    mImageView.layout((int) imgLeft, mImageView.getTop(), (int) (imgLeft + mImgWidth), mImageView.getBottom());
                }
                break;
            case PtrFrameLayout.PTR_STATUS_LOADING:

                break;
            case PtrFrameLayout.PTR_STATUS_COMPLETE:
                mImgWidth = mImageView.getMeasuredWidth();
                if (currentPos <= mOffsetToRefresh) {
                    float percent = currentPos / (float) mOffsetToRefresh;
                    float imgLeft = PtrLocalDisplay.SCREEN_WIDTH_PIXELS / 2.0f - (1 - percent) * (PtrLocalDisplay.SCREEN_WIDTH_PIXELS / 2.0f + mImgWidth / 2.0f);
                    if (imgLeft < PtrLocalDisplay.SCREEN_WIDTH_PIXELS / 2.0f - mImgWidth / 2.0f)
                        mImageView.layout((int) imgLeft, mImageView.getTop(), (int) (imgLeft + mImgWidth), mImageView.getBottom());
                }
                break;
        }
    }
}
