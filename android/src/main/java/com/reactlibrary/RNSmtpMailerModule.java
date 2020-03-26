
package com.reactlibrary;

import android.os.AsyncTask;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.mailercore.Mail;
import com.mailercore.MailSender;

public class RNSmtpMailerModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNSmtpMailerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNSmtpMailer";
    }

    @ReactMethod
    public void sendMail(final ReadableMap obj, final Promise promise) {
        AsyncTask.execute(new Runnable() {


            @Override
            public void run() {
                try {
                    MailSender sender = ConversionHelper.INSTANCE.toMailSender(obj);
                    Mail mail = ConversionHelper.INSTANCE.toMail(obj);
                    sender.sendMail(mail);

                    WritableMap success = new WritableNativeMap();
                    success.putString("status", "SUCCESS");
                    promise.resolve(success);
                } catch (Exception e) {
                    promise.reject("ERROR", e.getMessage());
                }
            }
        });
    }
}
