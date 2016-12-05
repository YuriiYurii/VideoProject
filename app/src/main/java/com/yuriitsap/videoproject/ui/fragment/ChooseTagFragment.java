package com.yuriitsap.videoproject.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.yuriitsap.videoproject.Application;
import com.yuriitsap.videoproject.R;
import com.yuriitsap.videoproject.databinding.ChooseTagLayoutBinding;
import com.yuriitsap.videoproject.events.TagHasBeenChosenEvent;

/**
 * Created by yuriitsap on 05.12.16.
 */
public class ChooseTagFragment extends Fragment implements View.OnClickListener {

    private ChooseTagLayoutBinding mBinding;
    private Bus mBus;


    public static ChooseTagFragment newInstance() {
        return new ChooseTagFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBus = Application.self().getBus();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.choose_tag_layout, container,
                false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mBinding.actionButton.setOnClickListener(this);
        mBinding.tagEntryEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (TextUtils.isEmpty(mBinding.tagEntryEditText.getText())) {
                    Snackbar.make(mBinding.coordinatorLayout, "Please enter tag you are searching for!!!",
                            Snackbar.LENGTH_SHORT).show();
                    return true;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mBus.post(new TagHasBeenChosenEvent(textView.getText().toString()));
                    return true;
                }
                return false;
            }
        });
    }

    // TODO: 05.12.16 migrate to separate holder???
    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(mBinding.tagEntryEditText.getText())) {
            Snackbar.make(mBinding.coordinatorLayout, "Please enter tag you are searching for!!!",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        mBus.post(new TagHasBeenChosenEvent(mBinding.tagEntryEditText.getText().toString()));
    }
}
