# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/xufeng/Documents/android-sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-keepclassmembers class com.pingplusplus.android.PaymentActivity {
    public java.lang.String EXTRA_CHARGE;
}

-keep public class com.pingplusplus.android.PaymentActivity{
    public java.lang.String getVersion();
}

-keep public class com.pingplusplus.android.Pingpp{
    *;
}

-keep class com.pingplusplus.android.PingppLog{
    public static void d(java.lang.String);
}

-keep class com.pingplusplus.android.PingppObject{
    *;
}

-keep class com.pingplusplus.android.PingppDataCollection{
    public void sendToServer();
    public PingppDataCollection(android.content.Context);
}

-keepclassmembers class com.pingplusplus.android.PingppDataCollection {
    public <fields>;
}

-keepclassmembers class com.pingplusplus.android.PingppLog{
    public boolean DEBUG;
}

-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}

-dontwarn  com.unionpay.**
-keep class com.unionpay.** {*;}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}