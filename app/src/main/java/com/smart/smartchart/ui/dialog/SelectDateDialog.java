package com.smart.smartchart.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.widget.NumberPickerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Gs on 2017/6/16.
 */

public class SelectDateDialog extends BottomSheetDialog {
    private Context context;
    private int selectedYear, selectedMonth, selectedDay;
    private int curMonth, curDay;
    private int startYear;
    private int endYear;
    private ArrayList<String> yearData = new ArrayList<>();
    private ArrayList<String> monthData = new ArrayList<>();
    private ArrayList<String> dayData = new ArrayList<>();

    private OnSelectedDateListener selectedDateListener;

    public SelectDateDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_select_date, null);
        NumberPickerView pickerYear = (NumberPickerView) view.findViewById(R.id.picker_year);
        final NumberPickerView pickerMonth = (NumberPickerView) view.findViewById(R.id.picker_month);
        final NumberPickerView pickerDay = (NumberPickerView) view.findViewById(R.id.picker_day);
        TextView tvCancle = (TextView) view.findViewById(R.id.tv_cancle);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_sure);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDateListener.selectedDate(selectedYear, selectedMonth, selectedDay);
                dismiss();
            }
        });

        initData();
        pickerYear.refreshByNewDisplayedValues((String[]) yearData.toArray(new String[0]));
        pickerMonth.refreshByNewDisplayedValues((String[]) monthData.toArray(new String[0]));
        pickerDay.refreshByNewDisplayedValues((String[]) dayData.toArray(new String[0]));
        pickerYear.setValue(yearData.indexOf(selectedYear + ""));
        pickerMonth.setValue(monthData.indexOf(selectedMonth + ""));
        pickerDay.setValue(dayData.indexOf(selectedDay + ""));
        pickerYear.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                selectedYear = Integer.parseInt(picker.getContentByCurrValue());
                if (selectedYear==endYear){
                    //刷新月
                    monthData.clear();
                    for (int i = 1; i <= curMonth; i++) {
                        monthData.add(i + "");
                    }
                }else{
                    monthData.clear();
                    for (int i = 1; i <= 12; i++) {
                        monthData.add(i + "");
                    }
                }
                pickerMonth.refreshByNewDisplayedValues((String[]) monthData.toArray(new String[0]));
                if (monthData.indexOf(selectedMonth + "") < 0) {
                    pickerMonth.setValue(monthData.size()-1);
                    selectedMonth = Integer.parseInt(monthData.get(monthData.size()-1));
                } else {
                    pickerMonth.setValue(monthData.indexOf(selectedMonth + ""));
                }

                if (selectedYear==endYear){
                    dayData.clear();
                    for (int i = 1; i <= curDay; i++) {
                        dayData.add(i + "");
                    }
                }else{
                    setDays(selectedYear, selectedMonth);
                }
                pickerDay.refreshByNewDisplayedValues((String[]) dayData.toArray(new String[0]));
                if (dayData.indexOf(selectedDay + "") < 0) {
                    pickerDay.setValue(dayData.size() - 1);
                    selectedDay=Integer.parseInt(dayData.get(dayData.size()-1));
                } else {
                    pickerDay.setValue(dayData.indexOf(selectedDay + ""));
                }
            }
        });
        pickerMonth.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                selectedMonth = Integer.valueOf(picker.getContentByCurrValue());
                if (selectedYear==endYear&&selectedMonth==curMonth){
                    dayData.clear();
                    for (int i = 1; i <= curDay; i++) {
                        dayData.add(i + "");
                    }
                }else{
                    setDays(selectedYear, selectedMonth);
                }
                pickerDay.refreshByNewDisplayedValues((String[]) dayData.toArray(new String[0]));
                if (dayData.indexOf(selectedDay + "") < 0) {
                    pickerDay.setValue(dayData.size() - 1);
                    selectedDay=Integer.parseInt(dayData.get(dayData.size()-1));
                } else {
                    pickerDay.setValue(dayData.indexOf(selectedDay + ""));
                }


            }
        });
        pickerDay.setOnValueChangedListener(new NumberPickerView.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
                selectedDay = Integer.valueOf(picker.getContentByCurrValue());

            }
        });
        setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        onWindowAttributesChanged(wl);
    }

    private void initData() {
        Calendar calendar = Calendar.getInstance();
        startYear = calendar.get(Calendar.YEAR) - 100;
        endYear = calendar.get(Calendar.YEAR);
        selectedYear = endYear;
        if (endYear < startYear) {
            throw new IllegalArgumentException("startYear has to be less than endYear");
        }
        if (selectedMonth == 0) {
            selectedMonth = calendar.get(Calendar.MONTH) + 1;
            curMonth=selectedMonth;
        }
        if (selectedDay == 0) {
            selectedDay = calendar.get(Calendar.DAY_OF_MONTH);
            curDay=selectedDay;
        }
        for (int i = startYear; i <= endYear; i++) {
            yearData.add(i + "");
        }
        for (int i = 1; i <=selectedMonth; i++) {
            monthData.add(i + "");
        }

        dayData.clear();
        for (int i = 1; i <= selectedDay; i++) {
            dayData.add(i + "");
        }


    }

    private void setDays(int year, int month) {

        int day = 31;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            day = 30;
        } else if (month == 2) {
            if (isLeapYear(year)) {
                day = 29;
            } else {
                day = 28;
            }
        }

        dayData.clear();

        for (int i = 0; i < day; i++) {
            dayData.add((i + 1) + "");
        }

        Logger.i("ChooseDateDialog:setDays:>>>>>" + dayData.toString());
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && !(year % 100 == 0) || year % 400 == 0);
    }

    public interface OnSelectedDateListener {
        void selectedDate(int year, int month, int day);
    }


    public void setOnSelectedDateListener(OnSelectedDateListener listener) {
        this.selectedDateListener = listener;
    }


}
