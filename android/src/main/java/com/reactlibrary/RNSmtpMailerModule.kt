package com.reactlibrary

import android.os.AsyncTask
import com.facebook.react.bridge.*
import com.reactlibrary.ConversionHelper.toMail
import com.reactlibrary.ConversionHelper.toMailSender

class RNSmtpMailerModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    override fun getName(): String {
        return "RNSmtpMailer"
    }

    @ReactMethod
    fun sendMail(obj: ReadableMap?, promise: Promise) {
        AsyncTask.execute {
            try {
                obj?.let {
                    val sender = toMailSender(it)
                    val mail = toMail(it)
                    sender.sendMail(mail)
                }
                val success: WritableMap = WritableNativeMap()
                success.putString("status", "SUCCESS")
                promise.resolve(success)
            } catch (e: Exception) {
                promise.reject("ERROR", e.message)
            }
        }
    }

}