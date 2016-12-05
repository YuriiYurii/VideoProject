package com.yuriitsap.videoproject.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.yuriitsap.videoproject.Application;
import com.yuriitsap.videoproject.R;
import com.yuriitsap.videoproject.databinding.ActivityMainBinding;
import com.yuriitsap.videoproject.events.TagHasBeenChosenEvent;
import com.yuriitsap.videoproject.ui.adapter.MainViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int CHOOSE_TAG_FRAGMENT_INDEX = 0;
    private static final int GALLERY_FRAGMENT_INDEX = 1;
    private ActivityMainBinding mBinding;
    private MainViewPagerAdapter mAdapter;
    private Bus mBus;
    private int mCurrentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mBus = Application.self().getBus();
        mBus.register(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.viewPager.setOffscreenPageLimit(2);
        mBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
                showHideKeyboard(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }

    @Subscribe
    public void onTagHasBeenChosen(TagHasBeenChosenEvent event) {
        //warning : magic numbers
        mBinding.viewPager.setCurrentItem(GALLERY_FRAGMENT_INDEX, true);
    }

    @Override
    public void onBackPressed() {
        //a hack for going to previous screen
        if (mCurrentPage > 0) {
            mBinding.viewPager.setCurrentItem(--mCurrentPage, true);
            return;
        }
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBus.unregister(this);
    }

    //warning : another hardcode
    private void showHideKeyboard(int position) {
        InputMethodManager imm;
        switch (position) {
            case CHOOSE_TAG_FRAGMENT_INDEX:
                imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.showSoftInputFromInputMethod(mBinding.viewPager.getWindowToken(), 0);
            case GALLERY_FRAGMENT_INDEX:
                imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInputFromWindow(mBinding.viewPager.getWindowToken(),
                        InputMethodManager.SHOW_FORCED, 0);
        }
    }
}
