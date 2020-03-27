package com.reactlibrary

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.*
import com.facebook.react.uimanager.ViewManager

class RNSmtpMailerPackage : ReactPackage {
    override fun createJSModules(): MutableList<Class<out JavaScriptModule>> {
        TODO("not implemented. Deprecated since React-native 0.47")
    }

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        return listOf<NativeModule>(RNSmtpMailerModule(reactContext))
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }
}