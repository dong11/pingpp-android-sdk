package com.pingplusplus.demoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.pingplusplus.libone.PingppOne;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置 APP_ID 和 PUBLISHABLE_KEY (应用内快捷支付所需要)
        PingppOne.APP_ID = App.APP_ID;
        PingppOne.PUBLISHABLE_KEY = App.PUBLISHABLE_KEY;
    }

    public void btnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_pay:
                /**
                 * 跳转支付渠道页面
                 */
                Intent intent = new Intent(MainActivity.this, ExampleActivity.class);
                intent.putExtra("customer", App.customer);
                startActivity(intent);
                break;
            case R.id.btn_manager_card:
                /**
                 * 跳转卡片管理页面
                 */
                PingppOne.openCardManager(this, App.customer, new MPaymentHandler());
                break;
        }
    }
}
