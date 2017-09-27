package com.smart.smartchart.widget.panellist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


public class PanelListLayout extends RelativeLayout {

    private PanelListAdapter adapter;

    public PanelListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PanelListAdapter adapter) {
        this.adapter = adapter;
        adapter.initAdapter();
    }

    public PanelListLayout(Context context) {
        super(context);
    }

    public PanelListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PanelListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
