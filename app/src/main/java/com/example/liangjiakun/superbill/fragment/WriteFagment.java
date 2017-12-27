package com.example.liangjiakun.superbill.fragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.voicerecognition.android.ui.BaiduASRDigitalDialog;
import com.baidu.voicerecognition.android.ui.DialogRecognitionListener;
import com.example.liangjiakun.superbill.R;
import com.example.liangjiakun.superbill.Utility.Bill;
import com.example.liangjiakun.superbill.Utility.BillAdapter;
import com.example.liangjiakun.superbill.Utility.BillData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;


public class WriteFagment extends Fragment {
    public TextView txv_kind, txt_monty;
    public Button timeButton, save_button;
    public EditText memo;
    public RadioButton radio_out, radio_in;
    public LinearLayout layout_out, layout_in;
    public RadioGroup mWriteRadioGroup;
    public ImageView imgv1, imgv2, imgv3, imgv4, imgv5, imgv6, imgv7, imgv8, imgv9, imgv10, imgv11, imgv12, imgv13, imgv14, imgv15, imgv16, imgv17, imgv18, imgv19, imgv20, imgv21, imgv22, imgv23, imgv24, imgv25;
    public ListView billlist;
    public List<Map<String, Bill>> list;
    public TextView inMoney;
    public TextView outMoney;
    public TextView sumMoney;
    private LineChartView lineChart;
    private PieChartView pieChart1, pieChart2;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    private List<Map<Integer, Float>> outList = new ArrayList<>();
    private List<Map<Integer, Float>> inList = new ArrayList<>();
    private Float sumOut = Float.valueOf(0), sumIn = Float.valueOf(0), orgin1 = Float.valueOf(0), orgin2 = Float.valueOf(0), orgin0 = Float.valueOf(0), now0 = Float.valueOf(0), now1 = Float.valueOf(0);
    private int out_in_page = 1;

    private Button button_start;//开始按钮
    private EditText text_input;//语音识别对话框
    private BaiduASRDigitalDialog mDialog = null;
    private DialogRecognitionListener mDialogListener = null;
    private String API_KEY = "DAwQ8vXGp3yqZPvbZWqvoQyO";
    private String SECRET_KEY = "f4780063042eca99c30b40753659a662";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_write, null);
        return view;
    }

    //重写setMenuVisibility方法，不然会出现叠层的现象
    @Override
    public void setMenuVisibility(boolean menuVisibile) {
        super.setMenuVisibility(menuVisibile);
        if (this.getView() != null) {
            this.getView().setVisibility(menuVisibile ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mDialog == null) {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            Bundle params = new Bundle();
            //设置API_KEY
            params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, API_KEY);
            params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, SECRET_KEY);
            //设置百度对话框主题
            params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_BLUE_LIGHTBG);
            //实例化百度语音识别
            mDialog = new BaiduASRDigitalDialog(getContext(), params);
            //设置百度语音回调接口
            mDialogListener = new DialogRecognitionListener() {
                @Override
                public void onResults(Bundle mResults) {
                    ArrayList<String> rs = mResults != null ? mResults.getStringArrayList(RESULTS_RECOGNITION) : null;
                    if (rs != null && rs.size() > 0) {
                        text_input.setText(rs.get(0));
                    }
                }
            };
            mDialog.setDialogRecognitionListener(mDialogListener);
        }

        //界面
        button_start = (Button) getActivity().findViewById(R.id.button_start);
        text_input = (EditText) getActivity().findViewById(R.id.memo);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });

        txv_kind = (TextView) getActivity().findViewById(R.id.txv_kind);
        timeButton = (Button) getActivity().findViewById(R.id.time_button);
        memo = (EditText) getActivity().findViewById(R.id.memo);
        radio_out = (RadioButton) getActivity().findViewById(R.id.mWriteOutRb);
        radio_in = (RadioButton) getActivity().findViewById(R.id.mWriteInRb);
        txt_monty = (TextView) getActivity().findViewById(R.id.txt_monty);
        save_button = (Button) getActivity().findViewById(R.id.save_button);
        layout_out = (LinearLayout) getActivity().findViewById(R.id.out_kinds);
        layout_in = (LinearLayout) getActivity().findViewById(R.id.in_kinds);
        mWriteRadioGroup = (RadioGroup) getActivity().findViewById(R.id.mWriteRadioGroup);
        imgv1 = (ImageView) getActivity().findViewById(R.id.imgv1);
        imgv2 = (ImageView) getActivity().findViewById(R.id.imgv2);
        imgv3 = (ImageView) getActivity().findViewById(R.id.imgv3);
        imgv4 = (ImageView) getActivity().findViewById(R.id.imgv4);
        imgv5 = (ImageView) getActivity().findViewById(R.id.imgv5);
        imgv6 = (ImageView) getActivity().findViewById(R.id.imgv6);
        imgv7 = (ImageView) getActivity().findViewById(R.id.imgv7);
        imgv8 = (ImageView) getActivity().findViewById(R.id.imgv8);
        imgv9 = (ImageView) getActivity().findViewById(R.id.imgv9);
        imgv10 = (ImageView) getActivity().findViewById(R.id.imgv10);
        imgv11 = (ImageView) getActivity().findViewById(R.id.imgv11);
        imgv12 = (ImageView) getActivity().findViewById(R.id.imgv12);
        imgv13 = (ImageView) getActivity().findViewById(R.id.imgv13);
        imgv14 = (ImageView) getActivity().findViewById(R.id.imgv14);
        imgv15 = (ImageView) getActivity().findViewById(R.id.imgv15);
        imgv16 = (ImageView) getActivity().findViewById(R.id.imgv16);
        imgv17 = (ImageView) getActivity().findViewById(R.id.imgv17);
        imgv18 = (ImageView) getActivity().findViewById(R.id.imgv18);
        imgv19 = (ImageView) getActivity().findViewById(R.id.imgv19);
        imgv20 = (ImageView) getActivity().findViewById(R.id.imgv20);
        imgv21 = (ImageView) getActivity().findViewById(R.id.imgv21);
        imgv22 = (ImageView) getActivity().findViewById(R.id.imgv22);
        imgv23 = (ImageView) getActivity().findViewById(R.id.imgv23);
        imgv24 = (ImageView) getActivity().findViewById(R.id.imgv24);
        imgv25 = (ImageView) getActivity().findViewById(R.id.imgv25);
        billlist = (ListView) getActivity().findViewById(R.id.billlist);
        list = new ArrayList<>();
        inMoney = (TextView) getActivity().findViewById(R.id.in_money);
        outMoney = (TextView) getActivity().findViewById(R.id.out_money);
        sumMoney = (TextView) getActivity().findViewById(R.id.sum_money);
        lineChart = (LineChartView) getActivity().findViewById(R.id.line_chart);
        pieChart1 = (PieChartView) getActivity().findViewById(R.id.pie_chart1);
        pieChart2 = (PieChartView) getActivity().findViewById(R.id.pie_chart2);

        timeButton.setText("点击选择日期");
        timeButton.setOnClickListener(new ClickListener());
        save_button.setOnClickListener(new ClickListener());

        mWriteRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.mWriteOutRb:
                        layout_in.setVisibility(View.GONE);
                        layout_out.setVisibility(View.VISIBLE);
                        if (out_in_page == 2) {
                            txv_kind.setText("");
                            out_in_page = 1;
                        }
                        break;
                    case R.id.mWriteInRb:
                        layout_out.setVisibility(View.GONE);
                        layout_in.setVisibility(View.VISIBLE);
                        if (out_in_page == 1) {
                            txv_kind.setText("");
                            out_in_page = 2;
                        }
                        break;
                }
            }
        });

        imgv1.setOnClickListener(new ClickListener());
        imgv2.setOnClickListener(new ClickListener());
        imgv3.setOnClickListener(new ClickListener());
        imgv4.setOnClickListener(new ClickListener());
        imgv5.setOnClickListener(new ClickListener());
        imgv6.setOnClickListener(new ClickListener());
        imgv7.setOnClickListener(new ClickListener());
        imgv8.setOnClickListener(new ClickListener());
        imgv9.setOnClickListener(new ClickListener());
        imgv10.setOnClickListener(new ClickListener());
        imgv11.setOnClickListener(new ClickListener());
        imgv12.setOnClickListener(new ClickListener());
        imgv13.setOnClickListener(new ClickListener());
        imgv14.setOnClickListener(new ClickListener());
        imgv15.setOnClickListener(new ClickListener());
        imgv16.setOnClickListener(new ClickListener());
        imgv17.setOnClickListener(new ClickListener());
        imgv18.setOnClickListener(new ClickListener());
        imgv19.setOnClickListener(new ClickListener());
        imgv20.setOnClickListener(new ClickListener());
        imgv21.setOnClickListener(new ClickListener());
        imgv22.setOnClickListener(new ClickListener());
        imgv23.setOnClickListener(new ClickListener());
        imgv24.setOnClickListener(new ClickListener());
        imgv25.setOnClickListener(new ClickListener());
    }

    public void changeImage(int id, ImageView imgv) {
        imgv1.setImageResource(R.mipmap.pic1);
        imgv2.setImageResource(R.mipmap.pic2);
        imgv3.setImageResource(R.mipmap.pic3);
        imgv4.setImageResource(R.mipmap.pic4);
        imgv5.setImageResource(R.mipmap.pic5);
        imgv6.setImageResource(R.mipmap.pic6);
        imgv7.setImageResource(R.mipmap.pic7);
        imgv8.setImageResource(R.mipmap.pic8);
        imgv9.setImageResource(R.mipmap.pic9);
        imgv10.setImageResource(R.mipmap.pic10);
        imgv11.setImageResource(R.mipmap.pic11);
        imgv12.setImageResource(R.mipmap.pic12);
        imgv13.setImageResource(R.mipmap.pic13);
        imgv14.setImageResource(R.mipmap.pic14);
        imgv15.setImageResource(R.mipmap.pic15);
        imgv16.setImageResource(R.mipmap.pic16);
        imgv17.setImageResource(R.mipmap.pic17);
        imgv18.setImageResource(R.mipmap.pic18);
        imgv19.setImageResource(R.mipmap.pic19);
        imgv20.setImageResource(R.mipmap.pic20);
        imgv21.setImageResource(R.mipmap.pic10);
        imgv22.setImageResource(R.mipmap.pic11);
        imgv23.setImageResource(R.mipmap.pic12);
        imgv24.setImageResource(R.mipmap.pic13);
        imgv25.setImageResource(R.mipmap.pic15);
        if (id != 0 && imgv != null) {
            imgv.setImageResource(id);
        }
    }

    private void updateUI() {
        getAxisXLables();//获取x轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化

        initPieChart1(); //初始化扇形图1
        initPieChart2(); //初始化扇形图2
    }

    private void getAxisXLables() {   //X轴的标注
        mAxisXValues.clear();
        for (int i = 0; i < BillData.date.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(BillData.date.get(i)));
        }
    }

    private void getAxisPoints() {   //图表的数据点
        mPointValues.clear();
        for (int i = 0; i < BillData.score.size(); i++) {
            mPointValues.add(new PointValue(i, BillData.score.get(i)));
        }
    }

    private void initPieChart1() {
        PieChartData pie_data = new PieChartData();
        boolean hasLabels = true; // 是否显示数据
        boolean hasLabelsOutside = false; // 数据是否显示在外面
        boolean hasCenterCircle = true; // 是否含有中圈，显示下面的内容这个必须为true
        boolean hasCenterText1 = true; // 圆中是否含有内容1
        boolean hasCenterText2 = true; // 圆中是否含有内容2
        boolean isExploded = false; // 是否爆破形式
        boolean hasLabelForSelected = false; // 是否选中显示数据，一般为false

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (Map<Integer, Float> m : outList) {
            for (Integer k : m.keySet()) {
                SliceValue sliceValue = new SliceValue(m.get(k), ChartUtils.pickColor());
                values.add(sliceValue);
            }
        }

        pie_data = new PieChartData(values);
        pie_data.setHasLabels(hasLabels);
        pie_data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        pie_data.setHasLabelsOutside(hasLabelsOutside);
        pie_data.setHasCenterCircle(hasCenterCircle);
        // 设置不显示数据的背景颜色
        pie_data.setValueLabelBackgroundEnabled(false);

        if (isExploded) {
            pie_data.setSlicesSpacing(24);
        }

        if (hasCenterText1) {
            Float a = sumOut / (sumIn + sumOut) * 100;
            Float b = (float) (Math.round(a * 100)) / 100;
            pie_data.setCenterText1(b.toString() + "%");
            pie_data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity, (int) getResources().getDimension(R.dimen.activity_horizontal_margin)));
            pie_data.setCenterText1Color(getResources().getColor(R.color.green));
        }

        if (hasCenterText2) {
            pie_data.setCenterText2("支出");
            pie_data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity, (int) getResources().getDimension(R.dimen.activity_horizontal_margin)));
            pie_data.setCenterText2Color(getResources().getColor(R.color.green));
        }

        pieChart1.setPieChartData(pie_data);
    }

    private void initPieChart2() {
        PieChartData pie_data = new PieChartData();
        boolean hasLabels = true; // 是否显示数据
        boolean hasLabelsOutside = false; // 数据是否显示在外面
        boolean hasCenterCircle = true; // 是否含有中圈，显示下面的内容这个必须为true
        boolean hasCenterText1 = true; // 圆中是否含有内容1
        boolean hasCenterText2 = true; // 圆中是否含有内容2
        boolean isExploded = false; // 是否爆破形式
        boolean hasLabelForSelected = false; // 是否选中显示数据，一般为false

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (Map<Integer, Float> m : inList) {
            for (Integer k : m.keySet()) {
                SliceValue sliceValue = new SliceValue(m.get(k), ChartUtils.pickColor());
                values.add(sliceValue);
            }
        }

        pie_data = new PieChartData(values);
        pie_data.setHasLabels(hasLabels);
        pie_data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        pie_data.setHasLabelsOutside(hasLabelsOutside);
        pie_data.setHasCenterCircle(hasCenterCircle);
        // 设置不显示数据的背景颜色
        pie_data.setValueLabelBackgroundEnabled(false);

        if (isExploded) {
            pie_data.setSlicesSpacing(24);
        }

        if (hasCenterText1) {
            Float a = sumIn / (sumIn + sumOut) * 100;
            Float b = (float) (Math.round(a * 100)) / 100;
            pie_data.setCenterText1(b.toString() + "%");
            pie_data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity, (int) getResources().getDimension(R.dimen.activity_horizontal_margin)));
            pie_data.setCenterText1Color(getResources().getColor(R.color.green));
        }

        if (hasCenterText2) {
            pie_data.setCenterText2("收入");
            pie_data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity, (int) getResources().getDimension(R.dimen.activity_horizontal_margin)));
            pie_data.setCenterText2Color(getResources().getColor(R.color.green));
        }

        pieChart2.setPieChartData(pie_data);
    }

    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.GRAY);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        //Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);
    }

    private class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id;
            switch (v.getId()) {
                case R.id.save_button:
                    int out_in = 0;
                    if (txt_monty.getText().toString().trim().length() <= 0) {
                        Toast.makeText(getActivity(), "非法操作，账目金额不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (txv_kind.getText().toString().trim().length() <= 0) {
                        Toast.makeText(getActivity(), "非法操作，必须选择一个账目分类", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (timeButton.getText().toString().trim() == "点击选择日期") {
                        Toast.makeText(getActivity(), "非法操作，账目日期不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Float money = Float.parseFloat(txt_monty.getText().toString());
                    String kind = txv_kind.getText().toString();
                    String time = timeButton.getText().toString();
                    String memoString = memo.getText().toString();

                    if (radio_out.isChecked()) {
                        out_in = 0;
                        orgin0 = Float.parseFloat(outMoney.getText().toString());
                        now0 = money + orgin0;
                        outMoney.setText(Float.toString(now0));
                        sumOut += now0;
                        orgin2 = Float.parseFloat(sumMoney.getText().toString());
                        BillData.score.add(-1 * money);
                    } else {
                        out_in = 1;
                        orgin1 = Float.parseFloat(inMoney.getText().toString());
                        now1 = money + orgin1;
                        inMoney.setText(Float.toString(now1));
                        sumIn += now1;
                        orgin2 = Float.parseFloat(sumMoney.getText().toString());
                        BillData.score.add(money);
                    }
                    sumMoney.setText(Float.toString(now1 - now0));
                    BillData.date.add(time);
                    Bill bill = new Bill(out_in, money, kind, time, memoString);

                    Map<String, Bill> map = new HashMap<>();
                    map.put("onebill", bill);
                    list.add(map);
                    billlist.setAdapter(new BillAdapter(getActivity(), list));

                    Map<Integer, Float> mapPie = new HashMap<>();
                    if (out_in == 0) {
                        if (kind == "服装") mapPie.put(1, money);
                        else if (kind == "饮食") mapPie.put(2, money);
                        else if (kind == "住房") mapPie.put(3, money);
                        else if (kind == "交通") mapPie.put(4, money);
                        else if (kind == "生活") mapPie.put(5, money);
                        else if (kind == "健康") mapPie.put(6, money);
                        else if (kind == "通讯") mapPie.put(7, money);
                        else if (kind == "娱乐") mapPie.put(8, money);
                        else if (kind == "孩子") mapPie.put(9, money);
                        else if (kind == "父母") mapPie.put(10, money);
                        else if (kind == "红包") mapPie.put(11, money);
                        else if (kind == "彩票") mapPie.put(12, money);
                        else if (kind == "交易") mapPie.put(13, money);
                        else if (kind == "人情") mapPie.put(14, money);
                        else if (kind == "其他") mapPie.put(15, money);
                        outList.add(mapPie);
                    } else {
                        if (kind == "工资") mapPie.put(16, money);
                        else if (kind == "奖金") mapPie.put(17, money);
                        else if (kind == "补助") mapPie.put(18, money);
                        else if (kind == "报销") mapPie.put(19, money);
                        else if (kind == "兼职") mapPie.put(20, money);
                        else if (kind == "父母") mapPie.put(21, money);
                        else if (kind == "红包") mapPie.put(22, money);
                        else if (kind == "彩票") mapPie.put(23, money);
                        else if (kind == "交易") mapPie.put(24, money);
                        else if (kind == "其他") mapPie.put(25, money);
                        inList.add(mapPie);
                    }

                    updateUI();

                    Toast.makeText(getActivity(), "保存添加账单成功", Toast.LENGTH_SHORT).show();
                    txt_monty.setText("");
                    txv_kind.setText("");
                    timeButton.setText("点击选择日期");
                    memo.setText("");
                    changeImage(0, null);
                    break;
                case R.id.time_button:
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            System.out.println(String.format("%d-%d-%d", year, month + 1, day));
                            timeButton.setText(String.format("%d-%d-%d", year, month + 1, day));
                        }
                    }, 2017, 5, 1).show();
                    break;
                case R.id.imgv1:
                    id = R.mipmap.new1;
                    txv_kind.setText("服装");
                    changeImage(id, imgv1);
                    break;
                case R.id.imgv2:
                    id = R.mipmap.new2;
                    txv_kind.setText("饮食");
                    changeImage(id, imgv2);
                    break;
                case R.id.imgv3:
                    id = R.mipmap.new3;
                    txv_kind.setText("住房");
                    changeImage(id, imgv3);
                    break;
                case R.id.imgv4:
                    id = R.mipmap.new4;
                    txv_kind.setText("交通");
                    changeImage(id, imgv4);
                    break;
                case R.id.imgv5:
                    id = R.mipmap.new5;
                    txv_kind.setText("生活");
                    changeImage(id, imgv5);
                    break;
                case R.id.imgv6:
                    id = R.mipmap.new6;
                    txv_kind.setText("健康");
                    changeImage(id, imgv6);
                    break;
                case R.id.imgv7:
                    id = R.mipmap.new7;
                    txv_kind.setText("通讯");
                    changeImage(id, imgv7);
                    break;
                case R.id.imgv8:
                    id = R.mipmap.new8;
                    txv_kind.setText("娱乐");
                    changeImage(id, imgv8);
                    break;
                case R.id.imgv9:
                    id = R.mipmap.new9;
                    txv_kind.setText("孩子");
                    changeImage(id, imgv9);
                    break;
                case R.id.imgv10:
                    id = R.mipmap.new10;
                    txv_kind.setText("父母");
                    changeImage(id, imgv10);
                    break;
                case R.id.imgv11:
                    id = R.mipmap.new11;
                    txv_kind.setText("红包");
                    changeImage(id, imgv11);
                    break;
                case R.id.imgv12:
                    id = R.mipmap.new12;
                    txv_kind.setText("彩票");
                    changeImage(id, imgv12);
                    break;
                case R.id.imgv13:
                    id = R.mipmap.new13;
                    txv_kind.setText("交易");
                    changeImage(id, imgv13);
                    break;
                case R.id.imgv14:
                    id = R.mipmap.new14;
                    txv_kind.setText("人情");
                    changeImage(id, imgv14);
                    break;
                case R.id.imgv15:
                    id = R.mipmap.new15;
                    txv_kind.setText("其他");
                    changeImage(id, imgv15);
                    break;
                case R.id.imgv16:
                    id = R.mipmap.new16;
                    txv_kind.setText("工资");
                    changeImage(id, imgv16);
                    break;
                case R.id.imgv17:
                    id = R.mipmap.new17;
                    txv_kind.setText("奖金");
                    changeImage(id, imgv17);
                    break;
                case R.id.imgv18:
                    id = R.mipmap.new18;
                    txv_kind.setText("补助");
                    changeImage(id, imgv18);
                    break;
                case R.id.imgv19:
                    id = R.mipmap.new19;
                    txv_kind.setText("报销");
                    changeImage(id, imgv19);
                    break;
                case R.id.imgv20:
                    id = R.mipmap.new20;
                    txv_kind.setText("兼职");
                    changeImage(id, imgv20);
                    break;
                case R.id.imgv21:
                    id = R.mipmap.new10;
                    txv_kind.setText("父母");
                    changeImage(id, imgv21);
                    break;
                case R.id.imgv22:
                    id = R.mipmap.new11;
                    txv_kind.setText("红包");
                    changeImage(id, imgv22);
                    break;
                case R.id.imgv23:
                    id = R.mipmap.new12;
                    txv_kind.setText("彩票");
                    changeImage(id, imgv23);
                    break;
                case R.id.imgv24:
                    id = R.mipmap.new13;
                    txv_kind.setText("交易");
                    changeImage(id, imgv24);
                    break;
                case R.id.imgv25:
                    id = R.mipmap.new15;
                    txv_kind.setText("其他");
                    changeImage(id, imgv25);
                    break;
            }
        }
    }
}
