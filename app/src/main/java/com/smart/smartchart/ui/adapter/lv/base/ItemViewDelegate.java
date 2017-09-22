package com.smart.smartchart.ui.adapter.lv.base;


import com.smart.smartchart.ui.adapter.lv.ViewHolder;

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);
}
