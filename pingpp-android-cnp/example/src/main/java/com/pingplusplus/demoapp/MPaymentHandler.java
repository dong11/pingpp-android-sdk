package com.pingplusplus.demoapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.pingplusplus.android.PingppLog;
import com.pingplusplus.nocard.minterface.CardOperationCallback;
import com.pingplusplus.nocard.minterface.PaymentHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/5/17
 * @time 下午5:41
 */

public class MPaymentHandler implements PaymentHandler{

    private CardOperationCallback mCallback;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0x01){
                mCallback.continueOperation(App.customer, true, "");
                mCallback = null;
            }
        }
    };

    /**
     * 返回支付结果
     * @param data
     */
    @Override
    public void handlePaymentResult(Intent data) {
        if (data != null) {
            /**
             * result：支付结果信息
             * code：支付结果码  -2:用户自定义错误、 -1：失败、 0：取消、1：成功  2:无卡支付结果
             */
            if (data.getExtras().getInt("code") != 2) {
                PingppLog.d(data.getExtras().getString("result") + "  " + data.getExtras().getInt("code"));
            } else { //code=2：返回的是应用内快捷支付的结果
                String result = data.getStringExtra("result");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    if (resultJson.has("error")) {
                        result = resultJson.optJSONObject("error").toString();
                    } else if (resultJson.has("success")) {
                        result = resultJson.optJSONObject("success").toString();
                    }
                    PingppLog.d("result::" + result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 对卡片操作
     *
     * @param operate
     * @param info
     * @param callBack
     */
    @Override
    public void handleCardOperation(int operate, JSONObject info, CardOperationCallback callBack) {
        mCallback = callBack;
        try {
            if (operate == PINGPP_CARD_DELETE) {
                //操作删除卡片操作
                info.put("customer_id", App.deviceId);
                MThread("delete", info);
            } else if (operate == PINGPP_CARD_SET_DEFAULT) {
                //设置默认卡片操作
                info.put("customer_id", App.deviceId);
                String cardId = info.getString("card_id");
                info.put("default_source", cardId);
                info.remove("card_id");
                MThread("default", info);
            } else if (operate == PINGPP_CARD_ADD) {
                //添加卡片操作
                if(info != null) {
                    info.put("customer_id", App.deviceId);
                    String tokenId = info.getString("id");
                    info.put("source", tokenId);
                    info.remove("id");
                    if(App.customer != null){
                        MThread("add", info);
                    } else {
                        info.put("app", "app_1Gqj58ynP0mHeX1q");
                        info.put("description", "create test customer");
                        info.put("email", "zhangsan@pingxx.com");
                        JSONObject object = new JSONObject();
                        object.put("testkey","testkey");
                        info.put("metadata", object);

                        MThread("create_customer", info);
                    }
                }
            }
            /*
             * 返回绑卡结果
             * @param customer customer对象信息
             * @param isSuccess 是否绑卡成功
             * @param errorString 错误信息
             */
            //callBack.continueOperation(customer, isSuccess, errorString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void MThread(final String operate, final JSONObject info) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if("delete".equals(operate)){
                    App.customer = HttpUtils.deleteCard(info);
                } else if("add".equals(operate)){
                    App.customer = HttpUtils.addCard(info);
                } else if("default".equals(operate)){
                    App.customer = HttpUtils.setDefaultCard(info);
                } else if("create_customer".equals(operate)){
                    App.customer = HttpUtils.createCustomer(info);
                }

                if(mCallback != null){
                    mHandler.sendEmptyMessage(0x01);
                }
            }
        }).start();
    }
}
