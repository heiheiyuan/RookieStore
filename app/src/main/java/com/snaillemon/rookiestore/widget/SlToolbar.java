package com.snaillemon.rookiestore.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.snaillemon.rookiestore.R;

/**
 * Created by prince on 2016/9/19.
 */
public class SlToolbar extends Toolbar {
    private LayoutInflater mInflater;
    private View mView;
    private TextView mTextTitle;
    private EditText mSearchView;
    private Button mRightButton;
    public SlToolbar(Context context) {
        this(context,null);
    }
    public SlToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }
    public SlToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setContentInsetsRelative(10,10);
        if(attrs != null) {
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(getContext(), attrs, R.styleable.SlToolbar, defStyleAttr, 0);
            Drawable rightIcon = tintTypedArray.getDrawable(R.styleable.SlToolbar_rightButtonIcon);
            if (rightIcon != null) {
                setRightButtonIcon(rightIcon);
            }
            boolean isShowSearchView = tintTypedArray.getBoolean(R.styleable.SlToolbar_isShowSearchView,false);
            if (isShowSearchView) {
                showSearchView();
                hideTitleView();
            }
            CharSequence text = tintTypedArray.getText(R.styleable.SlToolbar_rightButtonText);
            if (text != null) {
                setRightButtonText(text);
            }
            tintTypedArray.recycle();
        }
    }

    public void hideTitleView() {
        if (mTextTitle != null) {
            mTextTitle.setVisibility(GONE);
        }
    }
    public void showTitleView() {
        if (mTextTitle != null) {
            mTextTitle.setVisibility(VISIBLE);
        }
    }
    public void showSearchView() {
        if (mSearchView != null) {
            mSearchView.setVisibility(VISIBLE);
        }
    }
    public void hideSearchView() {
        if (mSearchView != null) {
            mSearchView.setVisibility(GONE);
        }
    }

    private void initView() {
        if(mView == null) {
            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar, null);
            mSearchView = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mRightButton = (Button) mView.findViewById(R.id.toolbar_right_button);
            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView,lp);
        }
    }
    public  void setRightButtonOnClickListener(OnClickListener li){

        mRightButton.setOnClickListener(li);
    }
    public Button getRightButton(){

        return this.mRightButton;
    }

    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getResources().getText(resId));
    }
    @Override
    public void setTitle(CharSequence title) {
        initView();
        mTextTitle.setText(title);
        showTitleView();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void  setRightButtonIcon(Drawable icon){
        if(mRightButton !=null){
            mRightButton.setBackground(icon);
            mRightButton.setVisibility(VISIBLE);
        }
    }
    public void  setRightButtonIcon(int icon){
        setRightButtonIcon(getResources().getDrawable(icon,null));
    }

    public void setRightButtonText(CharSequence rightButtonText) {
        mRightButton.setText(rightButtonText);
        mRightButton.setVisibility(VISIBLE);
    }
    public void setRightButtonText(int id) {
        setRightButtonText(getResources().getText(id));
    }
}
