package com.example.liangjiakun.superbill.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liangjiakun.superbill.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Liang Jiakun on 2017/5/13.
 */

public class BillAdapter extends BaseAdapter {
    private List<Map<String, Bill>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public BillAdapter(Context context, List<Map<String, Bill>> data) {
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class Zujian {
        public ImageView billImage;
        public TextView billMemo;
        public TextView billOutIn;
        public TextView billMoney;
        public TextView billKind;
        public TextView billDate;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Zujian zujian = null;
        if (convertView == null) {
            zujian = new Zujian();
            //获得组件，实例化组件
            convertView = layoutInflater.inflate(R.layout.bill_item, null);
            zujian.billImage = (ImageView) convertView.findViewById(R.id.bill_image);
            zujian.billMemo = (TextView) convertView.findViewById(R.id.bill_memo);
            zujian.billOutIn = (TextView) convertView.findViewById(R.id.bill_outin);
            zujian.billMoney = (TextView) convertView.findViewById(R.id.bill_money);
            zujian.billKind = (TextView) convertView.findViewById(R.id.bill_kind);
            zujian.billDate = (TextView) convertView.findViewById(R.id.bill_date);
            convertView.setTag(zujian);
        } else {
            zujian = (Zujian) convertView.getTag();
        }
        Bill bill = null;

        bill = data.get(position).get("onebill");
        zujian.billMemo.setText(bill.getMemo());
        if (bill.getOut_in() == 0) {
            zujian.billOutIn.setText("支出");
        } else {
            zujian.billOutIn.setText("收入");
        }
        zujian.billMoney.setText(Double.toString(bill.getMoney()));
        zujian.billKind.setText(bill.getKind());
        zujian.billDate.setText(bill.getDate());
        int id = R.mipmap.pic1;
        switch (bill.getKind()) {
            case "服装":
                id = R.mipmap.pic1;
                break;
            case "饮食":
                id = R.mipmap.pic2;
                break;
            case "住房":
                id = R.mipmap.pic3;
                break;
            case "交通":
                id = R.mipmap.pic4;
                break;
            case "生活":
                id = R.mipmap.pic5;
                break;
            case "健康":
                id = R.mipmap.pic6;
                break;
            case "通讯":
                id = R.mipmap.pic7;
                break;
            case "娱乐":
                id = R.mipmap.pic8;
                break;
            case "孩子":
                id = R.mipmap.pic9;
                break;
            case "父母":
                id = R.mipmap.pic10;
                break;
            case "红包":
                id = R.mipmap.pic11;
                break;
            case "彩票":
                id = R.mipmap.pic12;
                break;
            case "交易":
                id = R.mipmap.pic13;
                break;
            case "人情":
                id = R.mipmap.pic14;
                break;
            case "其他":
                id = R.mipmap.pic15;
                break;
            case "工资":
                id = R.mipmap.pic16;
                break;
            case "奖金":
                id = R.mipmap.pic17;
                break;
            case "补助":
                id = R.mipmap.pic18;
                break;
            case "报销":
                id = R.mipmap.pic19;
                break;
            case "兼职":
                id = R.mipmap.pic20;
                break;
        }
        zujian.billImage.setImageResource(id);

        return convertView;
    }
}
