package com.smart.smartchart.widget.panellist;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : zyb
 *     e-mail : hbdxzyb@hotmail.com
 *     time   : 2017/05/23
 *     desc   : 整个页面的Adapter，内部使用了两个子Adapter
 *              开发者可自行定义两个子Adapter
 *     version: 1.0
 * </pre>
 */

public class MyPanelListAdapter extends PanelListAdapter {

    private Context context;
    private ListView lv_content;
    private int contentResourceId;
    private List<Map<String, String>> contentList = new ArrayList<>();

    /**
     * constructor
     *
     * @param context 上下文
     * @param pl_root 根布局（PanelListLayout）
     * @param lv_content content部分的布局（ListView）
     * @param contentResourceId content 部分的 item 布局
     * @param contentList content 部分的数据
     */
    public MyPanelListAdapter(Context context, PanelListLayout pl_root, ListView lv_content,
                              int contentResourceId, List<Map<String,String>> contentList) {
        super(context, pl_root, lv_content);
        this.context = context;
        this.lv_content = lv_content;
        this.contentResourceId = contentResourceId;
        this.contentList = contentList;
    }

    /**
     * 给该方法添加实现，返回Content部分的适配器
     *
     * @return adapter of content
     */
    @Override
    protected BaseAdapter getContentAdapter() {
        return new ContentAdapter(context,contentResourceId,contentList);
    }

    /**
     * 给该方法添加实现，返回content部分的数据个数
     *
     * @return size of content data
     */
    @Override
    protected int getCount() {
        return contentList.size();
    }

    /**
     * content部分的adapter
     *
     * 这里可以自由发挥，和普通的 ListView 的 Adapter 没区别
     */
    private class ContentAdapter extends ArrayAdapter {

        private List<Map<String, String>> contentList;
        private int resourceId;
        private int itemHeight=80;//单位dp
        ContentAdapter(Context context, int resourceId, List<Map<String, String>> contentList) {
            super(context, resourceId);
            this.contentList = contentList;
            this.resourceId = resourceId;
        }

        @Override
        public int getCount() {
            return contentList.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final Map<String, String> data = contentList.get(position);
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder=new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
                viewHolder.linearLayout = (LinearLayout) convertView.findViewById(R.id.linear_root);
                int textWeight=CommonUtils.dp2px(context,itemHeight);
                for (int i=0;i<data.size();i++){
                    TextView item = new TextView(context);
                    item.setWidth(textWeight);//设置宽度
                    item.setSingleLine();
                    item.setGravity(Gravity.CENTER);
                    item.setTextSize(15);
                    item.setTextColor(context.getResources().getColor(R.color.color_323232));
                    item.setPadding(5,5,5,5);
                    viewHolder.linearLayout.addView(item);
                }
                convertView.setTag(viewHolder);

            }else{
                viewHolder= (ViewHolder) convertView.getTag();
            }
            for (int i=0;i<data.size();i++){
                ((TextView)viewHolder.linearLayout.getChildAt(i)).setText(data.get(i+1+""));
            }

            if (lv_content.isItemChecked(position)){
                convertView.setBackgroundColor(context.getResources().getColor(R.color.color_FBE382));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
            if (position%2==0){
                convertView.setBackgroundColor(context.getResources().getColor(R.color.color_f6f6f6));
            }else{
                convertView.setBackgroundColor(Color.WHITE);
            }
            return convertView;
        }

        private class ViewHolder {
            LinearLayout linearLayout;

        }
    }
}
