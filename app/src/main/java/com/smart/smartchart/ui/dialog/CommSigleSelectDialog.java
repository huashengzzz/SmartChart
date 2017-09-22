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


/**
 * Created by Gs on 2017/6/28.
 */

public class CommSigleSelectDialog extends BottomSheetDialog {
    private NumberPickerView numberPickerView;
    public CommSigleSelectDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_comm_sigle_select,null);
        numberPickerView= (NumberPickerView) view.findViewById(R.id.picker);
        TextView tvCancle= (TextView) view.findViewById(R.id.tv_cancle);
        TextView tvSure= (TextView) view.findViewById(R.id.tv_sure);
        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onSelectListener.onSelect(numberPickerView.getContentByCurrValue(),numberPickerView.getValue());
            }
        });
        setContentView(view);
    }

    /**
     * 刷新值
     * @param datas
     */
    public void setValue(String[] datas){
        numberPickerView.refreshByNewDisplayedValues(datas);
    }

    /**
     * 设置是否能循环转动
     * @param flag
     */
    public void setWrap(boolean flag){
        numberPickerView.setWrapSelectorWheel(flag);
    }

    public void setShowCount(int count){
        numberPickerView.setShowCount(count);
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
    private OnSelectListener onSelectListener;
    public void setOnSelectListener(OnSelectListener onSelectListener){
        this.onSelectListener=onSelectListener;
    }
   public interface OnSelectListener{
       void onSelect(String str, int value);
   }
}
