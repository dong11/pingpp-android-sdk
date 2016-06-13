package com.pingplusplus.demoapp;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.libone.PingppOne;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sunkai on 15/3/17.
 */
public class ExampleActivity extends FragmentActivity implements View.OnClickListener {

    private static String URL = "http://218.244.151.190/demo/charge";

    private ListView mListView;
    private GoodsAdapter myAdapter;
    private List<Good> mList;
    private TextView amountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        mListView = (ListView) findViewById(R.id.listView);
        amountView = (TextView) findViewById(R.id.textview_amount);
        mList = new ArrayList<Good>();

        mList.add(new Good("橡胶花盆", R.drawable.icon, 1, 0.02f));
        mList.add(new Good("搪瓷水壶", R.drawable.icon2, 1, 0.03f));
        mList.add(new Good("扫把和簸箕", R.drawable.icon3, 1, 0.05f));

        calculate();
        myAdapter = new GoodsAdapter(this, mList);
        mListView.setAdapter(myAdapter);
        findViewById(R.id.button).setOnClickListener(this);
        myAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                calculate();
                super.onChanged();
            }
        });

        //设置需要使用的支付方式
        PingppOne.enableChannels(new String[]{"wx", "alipay", "upacp", "cnp", "bfb", "jdpay_wap"});

        //设置是否支持外卡支付， true：支持， false：不支持， 默认不支持外卡
        PingppOne.SUPPORT_FOREIGN_CARD = true;

        //提交数据的格式，默认格式为json
//        PingppOne.CONTENT_TYPE = "application/x-www-form-urlencoded";
        PingppOne.CONTENT_TYPE = "application/json";

        //设置 APP_ID 和 PUBLISHABLE_KEY
        PingppOne.APP_ID = App.APP_ID;
        PingppOne.PUBLISHABLE_KEY = App.PUBLISHABLE_KEY;

        //是否开启日志
        PingppLog.DEBUG = true;
    }

    private void calculate() {
        float amount = 0;
        for (Good good : mList) {
            amount += good.getPrice() * good.getCount();
        }
        amountView.setText(String.format("%.2f", amount));
    }

    @Override
    public void onClick(View v) {
        // 产生个订单号
        String orderNo = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        //计算总金额（以分为单位）
        int amount = 0;
        for (Good good : mList) {
            amount += good.getPrice() * good.getCount() * 100;
        }
        //构建账单json对象
        JSONObject bill = new JSONObject();

        //自定义的额外信息 选填
        JSONObject extras = new JSONObject();
        try {
            extras.put("extra1", "extra1");
            extras.put("extra2", "extra2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            bill.put("order_no", orderNo);
            bill.put("amount", amount);
            bill.put("custom_params", extras);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //壹收款: 创建支付通道
        PingppOne.showPaymentChannels(getSupportFragmentManager(), bill.toString(), App.customer, URL, new MPaymentHandler());
    }
}
