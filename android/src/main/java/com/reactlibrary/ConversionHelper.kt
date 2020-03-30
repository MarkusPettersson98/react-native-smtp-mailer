package com.reactlibrary

import com.facebook.react.bridge.ReadableMap
import com.mailercore.*

object ConversionHelper {
    fun toMail(maildata: ReadableMap): Mail {
        val from = maildata.getString("from")!!
        val recipients = maildata.getArray("recipients")!!.toStringIterable().toList()
        val subject = maildata.getString("subject")!!
        val body = maildata.getString("htmlBody")!!
        val cc: List<String> = maildata.getStringList("cc")
        val bcc: List<String> = maildata.getStringList("bcc")
        val attachments = maildata.getMapList("attachments")
                .map { toAttachment(it) }

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
        val mailhost = maildata.getNullableString("mailhost")
        val port = maildata.getNullableInt("port")
        val ssl = maildata.getNullableBoolean("ssl")

        return MailConfig(mailhost = mailhost, port = port, ssl = ssl)
    }

    fun toAttachment(attachmentObject: ReadableMap): Attachment {
        val path: String = attachmentObject.getString("path")!!
        val name: String? = attachmentObject.getNullableString("name")
        return Attachment(path = path, name = name)
    }

}
