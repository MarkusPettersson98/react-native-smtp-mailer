package com.reactlibrary

import com.facebook.react.bridge.ReadableMap
import com.mailercore.Attachment
import com.mailercore.Mail
import com.mailercore.MailConfig
import com.mailercore.MailSender

object ConversionHelper {
    fun toMail(maildata: ReadableMap): Mail {
        val from = maildata.getString("from")!!
        val recipients = maildata.getStringList("recipients")
        val subject = maildata.getString("subject")!!
        val body = maildata.getString("htmlBody")!!
        val cc: List<String> = maildata.getStringList("cc")
        val bcc: List<String> = maildata.getStringList("bcc")
        val attachmentPaths = maildata.getStringList("attachmentPaths")
        val attachmentNames = maildata.getStringList("attachmentNames")


        val attachments = attachmentNames.zip(attachmentPaths)
                .map { Attachment(name = it.first, path = it.second) }

        return Mail(
                from = from, recipients = recipients, subject = subject,
                body = body, cc = cc, bcc = bcc, attachments = attachments
        )
    }

    fun toMailSender(maildata: ReadableMap): MailSender {
        val username = maildata.getString("username")!!
        val password = maildata.getString("password")!!
        return MailSender(username = username, password = password, mailconfig = toMailConfig(maildata))
    }

    fun toMailConfig(maildata: ReadableMap): MailConfig {

        val mailhost = maildata.getStringSilent("mailhost")
        val port = maildata.getIntSilent("port")
        val ssl = maildata.getBooleanSilent("ssl")

        return MailConfig(mailhost = mailhost, port = port, ssl = ssl)
    }

}
