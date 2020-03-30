package com.mailercore

import com.mailercore.internals.PropertiesProvider
import com.mailercore.internals.SecurityProvider
import java.util.*
import java.security.*
import javax.activation.*
import javax.mail.*
import javax.mail.internet.*

class MailSender(val username: String, val password: String, mailconfig: MailConfig) : Authenticator() {

    companion object {
        @JvmStatic
        val securityProvider: Int = Security.addProvider(SecurityProvider())
    }

    private val session: Session
    private val properties: Properties = PropertiesProvider(mailconfig).initProps()

    init {
        session = Session.getDefaultInstance(properties, this)
    }


    override fun getPasswordAuthentication(): PasswordAuthentication? {
        return PasswordAuthentication(username, password)
    }


    @Synchronized
    @Throws(Exception::class)
    fun sendMail(mail: Mail) {
        val message = MimeMessage(session)
        val transport = session.transport
        var messageBodyPart: BodyPart = MimeBodyPart()
        val multipart = MimeMultipart()

        message.setFrom(InternetAddress(mail.from))
        message.subject = mail.subject
        message.sentDate = Date()
        messageBodyPart.setContent(mail.body, "text/html; charset=utf-8")

        multipart.addBodyPart(messageBodyPart)
        // Add recipients
        mail.recipients.map { InternetAddress(it) }.also { message.setRecipients(Message.RecipientType.TO, it.toTypedArray()) }
        mail.cc?.map { InternetAddress(it) }.also { message.addRecipients(Message.RecipientType.CC, it?.toTypedArray()) }
        mail.bcc?.map { InternetAddress(it) }.also { message.addRecipients(Message.RecipientType.BCC, it?.toTypedArray()) }
        // Add any attachments
        mail.attachments?.let { multipart.addAttachments(it) }

        message.setContent(multipart)
        message.saveChanges()
        Transport.send(message)
        transport.close()
    }

    private fun MimeMultipart.addAttachments(attachments: List<Attachment>) {
        attachments.forEach { this.addAttachment(it) }
    }

    private fun MimeMultipart.addAttachment(attachment: Attachment) {
        val messageBodyPart = MimeBodyPart()
        val source: DataSource = FileDataSource(attachment.path)
        messageBodyPart.dataHandler = DataHandler(source)
        messageBodyPart.fileName = attachment.name ?: source.name
        messageBodyPart.setHeader("Content-Type", source.contentType)
        this.addBodyPart(messageBodyPart)
    }


}