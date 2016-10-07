package com.snaillemon.rookiestore.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaSync;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snaillemon.rookiestore.R;

/**
 * Created by GoodBoy on 10/7/2016.
 */

public class NumberAddSubView extends LinearLayout implements View.OnClickListener{
    private LayoutInflater mLayoutInflater;
    private Button mAddBtn,mSubBtn;
    private TextView mWaresNumTv;
    private int value = 1;
    private int minValue = 1;
    private int maxValue;
    private OnButtonClickListener mListener;

    public NumberAddSubView(Context context) {
        this(context,null);
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutInflater = LayoutInflater.from(context);
        initView();
        if (attrs != null) {
            //get and set custom attribute
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(context,attrs,R.styleable.NumberAddSubView,defStyleAttr,0);
            value = a.getInt(R.styleable.NumberAddSubView_value, 0);
            setValue(value);
            mWaresNumTv.setText(String.valueOf(value));
            maxValue = a.getInt(R.styleable.NumberAddSubView_maxValue,0);
            setMaxValue(maxValue);
            minValue = a.getInt(R.styleable.NumberAddSubView_minValue,0);
            setMinValue(minValue);
            Drawable drawableAdd = a.getDrawable(R.styleable.NumberAddSubView_btnAddBackground);
            Drawable drawableSub = a.getDrawable(R.styleable.NumberAddSubView_btnSubBackground);
            Drawable drawableTxt = a.getDrawable(R.styleable.NumberAddSubView_textViewBackground);
            if (drawableAdd != null) setAddBtnBackground(drawableAdd);
            if (drawableSub != null) setSubBtnBackground(drawableSub);
            if (drawableTxt != null) setTxtBtnBackground(drawableTxt);
            a.recycle();
        }
    }

    private void setTxtBtnBackground(Drawable drawableTxt) {
        mWaresNumTv.setBackground(drawableTxt);
    }

    private void setSubBtnBackground(Drawable drawableSub) {
        mSubBtn.setBackground(drawableSub);
    }

    private void setAddBtnBackground(Drawable drawableAdd) {
        mAddBtn.setBackground(drawableAdd);
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        mListener = listener;
    }
    private void initView() {
        View view = mLayoutInflater.inflate(R.layout.widget_number_add_sub_view, this, true);
        mAddBtn = (Button) view.findViewById(R.id.addView_btn);
        mSubBtn = (Button) view.findViewById(R.id.subView_btn);
        mWaresNumTv = (TextView) view.findViewById(R.id.waresNum_tv);
        mAddBtn.setOnClickListener(this);
        mSubBtn.setOnClickListener(this);
    }

    public int getValue() {
        String val = mWaresNumTv.getText().toString();
        if (val != null && !"".equals(val)) {
            value = Integer.parseInt(val);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.addView_btn) {
            // TODO: 10/7/2016  update maxValue
            numAdd();
            if (mListener != null) {
                mListener.onBttonAddClick(v,value);
            }
        }else if (v.getId() == R.id.subView_btn) {
            // TODO: 10/7/2016 update minValue 
            numSub();
            if (mListener != null) {
                mListener.onBttonSubClick(v,value);
            }
        }
    }
    public void numAdd() {
        if (value < maxValue) value++;
        mWaresNumTv.setText(String.valueOf(value));
    }
    public void numSub() {
        if (value > minValue) value--;
        mWaresNumTv.setText(String.valueOf(value));
    }
    public interface OnButtonClickListener {
        void onBttonAddClick(View view,int value);
        void onBttonSubClick(View view,int value);
    }
}
