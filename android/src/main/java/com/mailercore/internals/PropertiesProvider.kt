package com.mailercore.internals

import com.mailercore.MailConfig
import java.util.*

class PropertiesProvider(private val mailconfig: MailConfig) {

    fun initProps(): Properties {
        val properties = Properties()
        properties.setProperty("mail.transport.protocol", "smtp")
        properties.setProperty("mail.host", mailconfig.mailhost)
        properties.setProperty("mail.smtp.auth", "true")
        properties.setProperty("mail.smtp.port", mailconfig.port)
        properties.setProperty("mail.smtp.socketFactory.port", mailconfig.port)
        if (mailconfig.ssl) {
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        } else {
            properties.setProperty("mail.smtp.starttls.enable", "true")
        }
        properties.setProperty("mail.smtp.socketFactory.fallback", "false")
        properties.setProperty("mail.smtp.quitwait", "false")

        return properties
    }
}