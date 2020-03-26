package com.reactlibrary

import com.facebook.react.bridge.ReadableMap
import com.mailercore.Attachment
import com.mailercore.Mail
import com.mailercore.MailSender

object ConversionHelper {
    fun toMail(maildata: ReadableMap): Mail {
        val from = maildata.getString("from")!!
        val recipients = maildata.getStringList("recipients")
        val bcc: List<String> = maildata.getStringList("bcc")
        val subject = maildata.getString("subject")!!
        val body = maildata.getString("htmlBody")!!
        val attachmentPaths = maildata.getStringList("attachmentPaths")
        val attachmentNames = maildata.getStringList("attachmentNames")
        val attachmentTypes = maildata.getStringList("attachmentTypes")


        val attachments = zipThree(attachmentNames, attachmentPaths, attachmentTypes)
                .map {
                    Attachment(
                            name = it.first,
                            path = it.second,
                            type = it.third)
                }

        return Mail(
                from = from, recipients = recipients, subject = subject,
                body = body, bcc = bcc, attachments = attachments
        )
    }

    fun toMailSender(maildata: ReadableMap): MailSender {
        val mailhost = maildata.getString("mailhost")!!
        val port = maildata.getString("port")!!
        val ssl = maildata.getBoolean("ssl")
        val username = maildata.getString("username")!!
        val password = maildata.getString("password")!!
        return MailSender(
                mailhost = mailhost, port = port, ssl = ssl,
                username = username, password = password
        )
    }

    private fun <A, B, C> zipThree(list1: List<A>, list2: List<B>, list3: List<C>): List<Triple<A, B, C>> {
        val ziptmp = list1.zip(list2).zip(list3)
        return ziptmp.fold(mutableListOf()) { acc, triple ->
            val first = triple.first.first
            val second = triple.first.second
            val third = triple.second
            acc.add(Triple(first, second, third))
            acc
        }
    }
}
