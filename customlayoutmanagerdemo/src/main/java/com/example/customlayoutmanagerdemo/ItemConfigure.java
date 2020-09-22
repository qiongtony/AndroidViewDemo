package com.example.customlayoutmanagerdemo;

import com.example.customlayoutmanagerdemo.utils.ScreenUtil;

public class ItemConfigure {
    /**
     * item的宽度
     */
    private int mItemWidth;

    /**
     * 滑一屏的尺寸
     */
    private int mOneScreenWidth;
    /**
     * 第一个/最后一个item的间距：保证这个item能够居中显示，第一个设左边距，最后一个设右边距
     */
    private int mBorderItemHorizontalMargin = 0;

    private float mMinScale;

    private float mScaleValue;
    public ItemConfigure(float minScale, int itemExposedWidth) {
        if (minScale <= 0f || itemExposedWidth < 0){
            throw new IllegalArgumentException("minScale smaller than 0 or itemExposedWidth smaller than 0");
        }
        if (minScale > 1f){
            throw new IllegalArgumentException("minScale more than 1f");
        }
        mMinScale = minScale;
        mScaleValue = 1f - minScale;
        mItemWidth = (int) ((ScreenUtil.getScreenWidth() - 2 * itemExposedWidth) / ((1f - minScale) + 1f));
        mOneScreenWidth = (int) Math.ceil(mItemWidth);
        mBorderItemHorizontalMargin = (ScreenUtil.getScreenWidth() - mItemWidth) / 2;
    }

    public int getItemWidth() {
        return mItemWidth;
    }

    public int getOneScreenWidth() {
        return mOneScreenWidth;
    }

    public int getBorderItemHorizontalMargin() {
        return mBorderItemHorizontalMargin;
    }

    public float getMinScale() {
        return mMinScale;
    }

    public float getScaleValue() {
        return mScaleValue;
    }

    private static ItemConfigure defaultItemConfigure;

    public static ItemConfigure getDefault(){
        if (defaultItemConfigure != null){
            return defaultItemConfigure;
        }
        defaultItemConfigure = new ItemConfigure(0.9f, (int) ScreenUtil.dp2Px(10));
        return defaultItemConfigure;
    }
}
