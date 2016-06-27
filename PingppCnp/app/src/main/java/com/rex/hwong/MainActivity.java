package com.rex.hwong;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.pingplusplus.nocard.PingppCnp;
import com.pingplusplus.nocard.minterface.CardOperationCallback;
import com.pingplusplus.nocard.minterface.PaymentHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    private String customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        PingppCnp.CHARGE_URL = "http://218.244.151.190/tests/nocard_pingxx.php";
        //设置是否支持外卡
        PingppCnp.SUPPORT_FOREIGN_CARD = true;

        PingppCnp.APP_ID = "app_PmfffHParTiD84aj";
//        PingppCnp.APP_ID = "app_1azb18LK84u5iDSW";
        PingppCnp.PUBLISHABLE_KEY = "pk_live_iP0e10nb5e5GnXTunHn5808S";
//        PingppCnp.PUBLISHABLE_KEY = "pk_test_Te5SaPjfXLCCiLufHKTurP84";

        customer = createCustomer();
    }

    public void btnClick(View view) {

        switch (view.getId()) {
            case R.id.pay:
                // 产生个订单号
                String orderNo = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
                //支付金额：单位为分
                int amount = 1;
                try {
                    PingppCnp.payment(this, customer, orderNo, amount, paymentHandler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.manager:
                try {
                    PingppCnp.managerCard(this, customer, paymentHandler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 模拟服务端解绑银行卡
     * @param cardId
     * @return
     * @throws Exception
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String delete(String cardId, String customerId) throws Exception {
        JSONObject object = new JSONObject(customer);
        JSONObject sources = object.optJSONObject("sources");
        JSONArray data = sources.optJSONArray("data");

        for (int i = 0; i < data.length(); i++){
            JSONObject cardJson = data.getJSONObject(i);
            if(cardId.equals(cardJson.optString("id"))){
                data.remove(i);
                break;
            }
        }
        return object.toString();
    }

    /**
     * 模拟服务端设置默认银行卡
     * @param cardId
     * @return
     * @throws Exception
     */
    public String setDefault(String cardId, String customerId) throws Exception{
        JSONObject object = new JSONObject(customer);
        JSONObject sources = object.optJSONObject("sources");
        JSONArray data = sources.optJSONArray("data");
        List<JSONObject> list = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject cardJson = data.getJSONObject(i);
            if(cardId.equals(cardJson.optString("id"))) {
                list.add(0,cardJson);
            } else {
                list.add(cardJson);
            }
        }

        for(int i = 0; i < data.length(); i++){
            data.put(i,list.get(i));
        }
        object.put("default_source",list.get(0).optString("id"));
        return object.toString();
    }

    /**
     * 模拟创建一个customer
     * @return
     */
    public String createCustomer(){
        StringBuilder customer = new StringBuilder();
        try {
            InputStream in = getAssets().open("customer.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while((line = br.readLine()) != null){
                customer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customer.toString();
    }

    /**
     * 无卡返回结果
     */
    PaymentHandler paymentHandler = new PaymentHandler() {
        /**
         * 支付结果
         * @param intent
         */
        @Override
        public void handlePaymentResult(Intent intent) {

            Log.i("123", "::" + intent.getStringExtra("result"));
            Log.i("123", "::" + intent.getIntExtra("code", -1));

        }

        /**
         * 返回操作卡片的信息
         * @param operate
         * @param info
         * @param callBack
         */
        @Override
        public void handleCardOperation(int operate, JSONObject info, CardOperationCallback callBack) {
            try {
                if (operate == PINGPP_CARD_DELETE) {
                    customer = delete(info.getString("card_id"), info.getString("customer_id"));
                } else if (operate == PINGPP_CARD_SET_DEFAULT) {
                    customer = setDefault(info.getString("card_id"), info.getString("customer_id"));
                } else if (operate == PINGPP_CARD_ADD) {
                    String tokenId = info.getString("id");
                    JSONObject smsCodeJson = info.optJSONObject("sms_code");
                    customer = createCustomer();
                }

                callBack.continueOperation(customer, true, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
