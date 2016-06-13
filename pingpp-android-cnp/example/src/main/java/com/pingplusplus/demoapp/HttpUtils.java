package com.pingplusplus.demoapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author dong {hwongrex@gmail.com}
 * @date 16/5/16
 * @time 下午4:28
 */

/**
 * 模拟操作卡支付
 */
public class HttpUtils {

    private static String urlStr = "";
    private static String urlAddCard = "";
    private static String urlCreateCustomer = "";
    private static String urlDeleteCard = "";
    private static String urlDefaultCard = "";

    public static String getCustomer(String customerId) {

        try {
            URL url = new URL(urlStr+customerId);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String response = "";
            String line;
            while ((line = br.readLine()) != null) {
                response += line;
            }
            return response;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String createCustomer(JSONObject jsonObject){
        try {
            HttpURLConnection conn = createPostConnection(urlCreateCustomer);
            conn.getOutputStream().write(jsonObject.toString().getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String response = "";
            String line;
            while ((line = br.readLine()) != null) {
                response += line;
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addCard(JSONObject jsonObject){
        try {
            HttpURLConnection conn = createPostConnection(urlAddCard);
            conn.getOutputStream().write(jsonObject.toString().getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String response = "";
            String line;
            while ((line = br.readLine()) != null) {
                response += line;
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String deleteCard(JSONObject jsonObject){
        try {
            HttpURLConnection conn = createPostConnection(urlDeleteCard);
            conn.getOutputStream().write(jsonObject.toString().getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String response = "";
            String line;
            while ((line = br.readLine()) != null) {
                response += line;
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String setDefaultCard(JSONObject jsonObject) {
        try {
            HttpURLConnection conn = createPutConnection(urlDefaultCard);
            conn.getOutputStream().write(jsonObject.toString().getBytes());
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String response = "";
            String line;
            while ((line = br.readLine()) != null) {
                response += line;
            }
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建一个 delete HttpURLConnection
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static HttpURLConnection createDeleteConnection(String url) throws Exception {
        URL my_url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + App.PUBLISHABLE_KEY);
        connection.setRequestMethod("DELETE");
        return connection;
    }

    /**
     * 创建一个 post HttpURLConnection
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static HttpURLConnection createPostConnection(String url) throws Exception {
        URL my_url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + App.PUBLISHABLE_KEY);
        connection.setRequestMethod("POST");
        return connection;
    }

    /**
     * 创建一个 get HttpURLConnection
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static HttpURLConnection createGetConnection(String url) throws Exception {
        URL my_url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + App.PUBLISHABLE_KEY);
        connection.setRequestMethod("GET");
        return connection;
    }

    /**
     * 创建一个 put HttpURLConnection
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static HttpURLConnection createPutConnection(String url) throws Exception {
        URL my_url = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) my_url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setConnectTimeout(8000);
        connection.setReadTimeout(8000);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + App.PUBLISHABLE_KEY);
        connection.setRequestMethod("PUT");
        return connection;
    }
}