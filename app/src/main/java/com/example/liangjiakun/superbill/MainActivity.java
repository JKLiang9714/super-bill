package com.example.liangjiakun.superbill;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.liangjiakun.superbill.fragment.WriteFagment;
import com.example.liangjiakun.superbill.fragment.BillFagment;
import com.example.liangjiakun.superbill.fragment.StaticFagment;

public class MainActivity extends FragmentActivity {
    private FrameLayout mHomeContent;
    private RadioGroup mHomeRadioGroup;
    private RadioButton mHomeBillRb;
    private RadioButton mHomeWriteRb;
    private RadioButton mHomeStaticRb;
    private TextView titleString;
    static final int NUM_ITEMS = 3;//一共三个fragment
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
    }

    protected void initView() {
        mHomeContent = (FrameLayout) findViewById(R.id.mHomeContent); //tab上方的区域
        mHomeRadioGroup = (RadioGroup) findViewById(R.id.mHomeRadioGroup);  //底部的三个tab
        mHomeBillRb = (RadioButton) findViewById(R.id.mHomeBillRb);
        mHomeWriteRb = (RadioButton) findViewById(R.id.mHomeWriteRb);
        mHomeStaticRb = (RadioButton) findViewById(R.id.mHomeStaticRb);
        titleString = (TextView) findViewById(R.id.text_title);

        //监听事件：为“账本”页面底部的RadioGroup绑定状态改变的监听事件
        mHomeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = 0;
                switch (checkedId) {
                    case R.id.mHomeBillRb:
                        index = 0;
                        titleString.setText("账本");
                        break;
                    case R.id.mHomeWriteRb:
                        index = 1;
                        titleString.setText("记一笔");
                        break;
                    case R.id.mHomeStaticRb:
                        index = 2;
                        titleString.setText("统计");
                        break;
                }
                //通过fragments这个adapter还有index来替换帧布局中的内容
                fragment = (Fragment) fragments.instantiateItem(mHomeContent, index);
                //一开始将帧布局中的内容设置为第一个
                fragments.setPrimaryItem(mHomeContent, 0, fragment);
                fragments.finishUpdate(mHomeContent);
            }
        });
    }

    //第一次启动时，我们让mHomeBillRb这个radiobutton处于选中状态。
    //当然了，在这之前，先要在布局文件中设置其他的某一个radiobutton（只要不是mHomeBillRb就行）
    //的属性为android:checked="true"，才会出发下面的这个check方法切换到mHomeBillRb
    @Override
    protected void onStart() {
        super.onStart();
        mHomeRadioGroup.check(R.id.mHomeBillRb);
    }

    //用adapter来管理三个Fragment界面的变化。注意，这里用的Fragment都是v4包里面的
    FragmentStatePagerAdapter fragments = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return NUM_ITEMS;//一共有三个Fragment
        }

        //进行Fragment的初始化
        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case 0://账本
                    fragment = new BillFagment();
                    break;
                case 1://记一笔
                    fragment = new WriteFagment();
                    break;
                case 2://统计
                    fragment = new StaticFagment();
                    break;
            }
            return fragment;
        }
    };
}