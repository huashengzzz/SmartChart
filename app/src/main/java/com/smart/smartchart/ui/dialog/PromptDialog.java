package com.smart.smartchart.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.smart.smartchart.R;
import com.smart.smartchart.utils.DisplayUtil;

/**
 * Created by gaosheng on 2016/7/29.
 */
public class PromptDialog extends AppCompatDialog {

    public PromptDialog(Context context) {
        super(context);
    }

    public PromptDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    public static class Builder {
        private int positiveColorResId;
        private int negativeColorResId;
        private int themeResId;
        private int gravity = Gravity.CENTER_VERTICAL;
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private boolean cancel = true;
        private boolean cancelableTouchOutSide = true;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setTheme(int themeResId) {
            this.themeResId = themeResId;
            return this;
        }

        public Builder setNegativeColorResId(int negativeColorResId) {
            this.negativeColorResId = negativeColorResId;
            return this;
        }

        public Builder setPositiveColorResId(int positiveColorResId) {
            this.positiveColorResId = positiveColorResId;
            return this;
        }

        public Builder setCancelableTouchOutSide(boolean cancelableTouchOutSide) {
            this.cancelableTouchOutSide = cancelableTouchOutSide;
            return this;
        }

        public Builder setCancel(boolean cancel) {
            this.cancel = cancel;
            return this;
        }


        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public PromptDialog create() {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final PromptDialog dialog = new PromptDialog(context, themeResId == 0 ? R.style.DefaultDialogStyle : themeResId);
            View layout = inflater.inflate(R.layout.dialog_wanxiang_confirm, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView titleView = (TextView) layout.findViewById(R.id.tv_confirm_title);
            final TextView messageTextView = (TextView) layout.findViewById(R.id.message);
            TextView positiveButton = (TextView) layout.findViewById(R.id.positiveButton);
            TextView negativeButton = (TextView) layout.findViewById(R.id.negativeButton);
            View vertical_line = layout.findViewById(R.id.vertical_line);
            LinearLayout ll_dialog_title = (LinearLayout) layout.findViewById(R.id.ll_dialog_title);
            LinearLayout ll_dialog_content = (LinearLayout) layout.findViewById(R.id.ll_dialog_content);
            final ScrollView scrollView = (ScrollView) layout.findViewById(R.id.scrollView);

            //set the dialog cancel
            dialog.setCancelable(cancel);
            dialog.setCanceledOnTouchOutside(cancelableTouchOutSide);

            // set the dialog title
            if (TextUtils.isEmpty(title)) {
                ll_dialog_title.setVisibility(View.GONE);
            } else {
                ll_dialog_title.setVisibility(View.VISIBLE);
                titleView.setText(title);
            }

            // set the confirm button
            if (positiveButtonText != null) {
                positiveButton.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {
                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
                if (positiveColorResId != 0) {
                    positiveButton.setTextColor(context.getResources().getColor(positiveColorResId));
                }

            } else {
                // if no confirm button just set the visibility to GONE
                positiveButton.setVisibility(View.GONE);
                vertical_line.setVisibility(View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {
                negativeButton.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
                if (negativeColorResId != 0) {
                    negativeButton.setTextColor(context.getResources().getColor(negativeColorResId));
                }
            } else {
                // if no confirm button just set the visibility to GONE
                negativeButton.setVisibility(View.GONE);
                vertical_line.setVisibility(View.GONE);
            }


            messageTextView.setGravity(gravity);
            // set the content message
            if (!TextUtils.isEmpty(message)) {
                messageTextView.setText(message);
                final ViewGroup.LayoutParams params = scrollView.getLayoutParams();
                onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int height = messageTextView.getHeight();
                        if (height < DisplayUtil.dp2px(context, 300)) {
                            params.height = LayoutParams.WRAP_CONTENT;
                            params.width = LayoutParams.MATCH_PARENT;
                            scrollView.setLayoutParams(params);
                        } else {
                            params.height = DisplayUtil.dp2px(context, 300);
                            params.width = LayoutParams.MATCH_PARENT;
                            scrollView.setLayoutParams(params);
                        }
                    }
                };
                messageTextView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);

            } else if (contentView != null) {
                // if no message set
                // add the contentView to the dialog body
                ((LinearLayout) layout.findViewById(R.id.ll_dialog_content)).removeAllViews();
                ((LinearLayout) layout.findViewById(R.id.ll_dialog_content)).addView(contentView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
            dialog.setContentView(layout);
            dialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        messageTextView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
                    } else {
                        messageTextView.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
                    }
                }
            });
            return dialog;
        }
    }
}
