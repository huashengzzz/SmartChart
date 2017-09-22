package com.smart.smartchart.ui.activity.home;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.adapter.rv.CommonAdapter;
import com.smart.smartchart.ui.adapter.rv.MultiItemTypeAdapter;
import com.smart.smartchart.ui.adapter.rv.base.ViewHolder;
import com.smart.smartchart.ui.base.BaseSwipeBackActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by gs on 2017/9/19.
 */

public class MessageActivity extends BaseSwipeBackActivity {
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private CommonAdapter<String> adapter;
    private List<String> datas = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        initToobar();
        datas.add("");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter = new CommonAdapter<String>(this, R.layout.item_my_message, datas) {

            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {

            }
        });
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        datas.add("");
                        datas.add("");
                        adapter.notifyDataSetChanged();
                        refreshlayout.finishLoadmore();
                        if (adapter.getItemCount() > 20) {
                            refreshlayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
                        }
                    }
                }, 2000);
            }

            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshlayout.finishRefresh();
                        refreshlayout.setLoadmoreFinished(false);
                    }
                }, 2000);
            }
        });


        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    private void initToobar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        title.setText("消息");
        toolbar.setNavigationIcon(R.drawable.icon_btn_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
