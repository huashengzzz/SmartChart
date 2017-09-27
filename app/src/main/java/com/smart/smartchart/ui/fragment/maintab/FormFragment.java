package com.smart.smartchart.ui.fragment.maintab;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseFragment;
import com.smart.smartchart.widget.panellist.MyPanelListAdapter;
import com.smart.smartchart.widget.panellist.PanelListLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by gs on 2017/9/15.
 */

public class FormFragment extends BaseFragment{
    @BindView(R.id.id_lv_content)
    ListView idLvContent;
    @BindView(R.id.id_pl_root)
    PanelListLayout idPlRoot;
    private MyPanelListAdapter adapter;
    private List<Map<String, String>> contentList = new ArrayList<>();
    @Override
    protected int getContentViewID() {
        return R.layout.fragment_form;
    }

    @Override
    protected void initViewsAndEvents(View rootView, Bundle savedInstanceState) {
        initContentDataList();
        adapter = new MyPanelListAdapter(getActivity(), idPlRoot, idLvContent, R.layout.item_content, contentList);
        adapter.setInitPosition(0);
        adapter.setRowDataList(getRowDataList());
        adapter.setTitle("时间");
        adapter.setTitleTwo("热换机名称");
        adapter.setTitleColor("#dddddd");
        adapter.setSwipeRefreshEnabled(false);
        adapter.setRowColor("#dddddd");
        adapter.setColumnDivider(null);
        // adapter.setRowDivider(getDrawable(R.drawable.row_item_divider));
        adapter.setColumnColor("#ffffff");
        idPlRoot.setAdapter(adapter);
    }
    /** 生成一份横向表头的内容
     *
     * @return List<String>
     */
    private List<String> getRowDataList(){
        List<String> rowDataList = new ArrayList<>();
        rowDataList.add("一次供温\n（℃）");
        rowDataList.add("一次供温\n（℃）");
        rowDataList.add("一次供温\n（℃）");
        rowDataList.add("一次供温\n（℃）");
        rowDataList.add("一次供温\n（℃）");
        rowDataList.add("一次供温\n（℃）");
        rowDataList.add("一次供温\n（℃）");
        rowDataList.add("一次供温\n（℃）");
        return rowDataList;
    }


    /**
     * 初始化content数据
     */
    private void initContentDataList() {
        for (int i = 1; i < 13; i++) {
            Map<String, String> data = new HashMap<>();
            data.put("1", "第" + i + "第一个");
            data.put("2", "第" + i + "第二个");
            data.put("3", "第" + i + "第三个");
            data.put("4", "第" + i + "第四个");
            data.put("5", "第" + i + "第五个");
            data.put("6", "第" + i + "第六个");
            data.put("7", "第" + i + "第七个");
            data.put("8", "第" + i + "第八个");
            contentList.add(data);
        }
    }
}
