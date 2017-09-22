package com.smart.smartchart.ui.fragment.maintab;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.base.BaseFragment;
import com.smart.smartchart.ui.dialog.CommSigleSelectDialog;
import com.smart.smartchart.ui.dialog.SelectDateDialog;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gs on 2017/9/15.
 */

public class EnergyFragment extends BaseFragment {

    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_heat_consume)
    TextView tvHeatConsume;
    @BindView(R.id.tv_sort)
    TextView tvSort;


    @Override
    protected int getContentViewID() {
        return R.layout.fragment_energy;
    }

    @Override
    protected void initViewsAndEvents(View rootView, Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        tvTime.setText(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+(calendar.get(Calendar.DAY_OF_MONTH)-1));

    }
    @OnClick({R.id.tv_time,R.id.tv_heat_consume,R.id.tv_sort})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_time:
                selectDate();
                break;
            case R.id.tv_heat_consume:
                singleSelectDialog(tvHeatConsume,new String[]{"热能","水能","电能"});
                break;
            case R.id.tv_sort:
                singleSelectDialog(tvSort,new String[]{"默认排序","从大到小","从小到大"});
                break;
        }
    }

    private void selectDate() {
        SelectDateDialog selectDateDialog=new SelectDateDialog(getActivity());
        selectDateDialog.setOnSelectedDateListener(new SelectDateDialog.OnSelectedDateListener() {
            @Override
            public void selectedDate(int year, int month, int day) {
                String mon,da,birthday;
                mon = month < 10 ? "0" + month : String.valueOf(month);
                da = day < 10 ? "0" + day : String.valueOf(day);
                birthday = year + "-" + mon + "-" + da;
                tvTime.setText(birthday);


            }
        });
        selectDateDialog.show();
    }
    private void singleSelectDialog(final TextView tv, String[] strs) {
        CommSigleSelectDialog commSigleSelectDialog = new CommSigleSelectDialog(getActivity());
        commSigleSelectDialog.setValue(strs);
        commSigleSelectDialog.setShowCount(3);
        commSigleSelectDialog.setWrap(true);
        commSigleSelectDialog.setOnSelectListener(new CommSigleSelectDialog.OnSelectListener() {
            @Override
            public void onSelect(String str,int value) {
                tv.setText(str);
            }
        });
        commSigleSelectDialog.show();
    }


}
