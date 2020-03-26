package com.mailercore

import java.security.*

class SecurityProvider : Provider("HarmonyJSSE", 1.0, "Harmony JSSE Provider") {

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        AccessController.doPrivileged<Void>(PrivilegedAction<Void?> {
            put("SSLContext.TLS",
                    "org.apache.harmony.xnet.provider.jsse.SSLContextImpl")
            put("Alg.Alias.SSLContext.TLSv1", "TLS")
            put("KeyManagerFactory.X509",
                    "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl")
            put("TrustManagerFactory.X509",
                    "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl")
            null
        })
    }

}