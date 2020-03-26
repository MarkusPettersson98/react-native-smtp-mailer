package com.mailercore

import java.util.*
import java.security.*
import java.security.Provider
import javax.activation.*
import javax.mail.*
import javax.mail.internet.*

class MailSender(val mailhost: String, val username: String, val password: String, val port: String,
                 val ssl: Boolean) : Authenticator() {

    companion object {
        @JvmStatic
        val securityProvider: Int = Security.addProvider(JSSEProvider())
    }

    private val session: Session
    private val properties: Properties

    init {
        properties = initProps()
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

        message.setFrom(InternetAddress(mail.from, ""))
        message.subject = mail.subject
        message.sentDate = Date()
        messageBodyPart.setContent(mail.body, "text/html; charset=utf-8")

        multipart.addBodyPart(messageBodyPart)
        // Add recipients
        mail.recipients.map { InternetAddress(it) }.also { message.setRecipients(Message.RecipientType.TO, it.toTypedArray()) }
        mail.bcc.map { InternetAddress(it) }.also { message.addRecipients(Message.RecipientType.BCC, it.toTypedArray()) }
        // Add any attachments
        multipart.addAttachments(mail.attachments)

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
        messageBodyPart.fileName = attachment.name
        if (attachment.type == "img") {
            messageBodyPart.setHeader("Content-ID", "<image>")
        }
        this.addBodyPart(messageBodyPart)
    }


    private fun initProps(): Properties {
        val properties = Properties()
        properties.setProperty("mail.transport.protocol", "smtp")
        properties.setProperty("mail.host", mailhost)
        properties.setProperty("mail.smtp.auth", "true")
        properties.setProperty("mail.smtp.port", port)
        properties.setProperty("mail.smtp.socketFactory.port", port)
        if (ssl) {
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
        } else {
            properties.setProperty("mail.smtp.starttls.enable", "true")
        }
        properties.setProperty("mail.smtp.socketFactory.fallback", "false")
        properties.setProperty("mail.smtp.quitwait", "false")

        return properties
    }


    internal class JSSEProvider : Provider("HarmonyJSSE", 1.0, "Harmony JSSE Provider") {
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

}