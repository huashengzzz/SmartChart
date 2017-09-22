package com.smart.smartchart.ui.fragment.other;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.ui.activity.form.FormDetailsActivity;
import com.smart.smartchart.ui.base.BaseFragment;
import com.smart.smartchart.ui.dialog.CommSigleSelectDialog;
import com.smart.smartchart.ui.dialog.SelectDateDialog;
import com.smart.smartchart.utils.CommonUtils;
import com.smart.smartchart.utils.ToastHelper;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by gs on 2017/9/20.
 */

public class FormMainFragment extends BaseFragment {


    @BindView(R.id.linear_select_root)
    LinearLayout linearSelectRoot;
    @BindView(R.id.tv_max)
    TextView tvMax;
    @BindView(R.id.tv_min)
    TextView tvMin;
    @BindView(R.id.tv_ave)
    TextView tvave;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_form_type)
    TextView tvFormType;
    @BindView(R.id.tv_group)
    TextView tvGroup;
    @BindView(R.id.tv_heat_group)
    TextView tvHeatGroup;


    public static Fragment newInstance(String status) {
        FormMainFragment fragment = new FormMainFragment();
        Bundle args = new Bundle();
        args.putString("status", status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_form_main;
    }

    @Override
    protected void initViewsAndEvents(View rootView, Bundle savedInstanceState) {
        View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_form_main, null);
        itemView.findViewById(R.id.linear_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        itemView.findViewById(R.id.linear_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        itemView.findViewById(R.id.linear_three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
            }
        });
        linearSelectRoot.addView(itemView);
    }

    @OnClick({R.id.tv_start_time, R.id.tv_end_time, R.id.tv_form_type, R.id.tv_group, R.id.tv_heat_group,
            R.id.tv_max, R.id.tv_min, R.id.tv_ave, R.id.tv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_time:
                selectDate(tvStartTime);
                break;
            case R.id.tv_end_time:
                selectDate(tvEndTime);
                break;
            case R.id.tv_form_type:
                singleSelectDialog(tvFormType, new String[]{"1", "2", "3"});
                break;
            case R.id.tv_group:
                singleSelectDialog(tvGroup, new String[]{"1", "2", "3"});
                break;
            case R.id.tv_heat_group:
                singleSelectDialog(tvHeatGroup, new String[]{"1", "2", "3"});
                break;
            case R.id.tv_max:
                tvMax.setSelected(true);
                tvMin.setSelected(false);
                tvave.setSelected(false);
                break;
            case R.id.tv_min:
                tvMax.setSelected(false);
                tvMin.setSelected(true);
                tvave.setSelected(false);
                break;
            case R.id.tv_ave:
                tvMax.setSelected(false);
                tvMin.setSelected(false);
                tvave.setSelected(true);
                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), FormDetailsActivity.class));
                break;
        }
    }

    private void selectDate(final TextView tv) {
        SelectDateDialog selectDateDialog = new SelectDateDialog(getActivity());
        selectDateDialog.setOnSelectedDateListener(new SelectDateDialog.OnSelectedDateListener() {
            @Override
            public void selectedDate(int year, int month, int day) {
                String mon, da, birthday;
                mon = month < 10 ? "0" + month : String.valueOf(month);
                da = day < 10 ? "0" + day : String.valueOf(day);
                birthday = year + "-" + mon + "-" + da;
                switch (tv.getId()) {
                    case R.id.tv_start_time:
                        try {
                            if (!TextUtils.isEmpty(tvEndTime.getText().toString())
                                    && (CommonUtils.dateToStamp(tvEndTime.getText().toString(), "yyyy-MM-dd") <
                                    CommonUtils.dateToStamp(birthday, "yyyy-MM-dd"))) {
                                ToastHelper.shortToast(getActivity(), "开始时间要不于结束时间");
                                return;
                            } else {
                                tvStartTime.setText(birthday);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case R.id.tv_end_time:
                        try {
                            if (!TextUtils.isEmpty(tvStartTime.getText().toString())
                                    && (CommonUtils.dateToStamp(tvStartTime.getText().toString(), "yyyy-MM-dd") >
                                    CommonUtils.dateToStamp(birthday, "yyyy-MM-dd"))) {
                                ToastHelper.shortToast(getActivity(), "结束时间要不小于开始时间");
                                return;
                            } else {
                                tvEndTime.setText(birthday);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;

                }


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
            public void onSelect(String str, int value) {
                tv.setText(str);
            }
        });
        commSigleSelectDialog.show();
    }


}
