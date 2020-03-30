package com.mailercore

data class Mail(val from: String, val recipients: List<String>, val subject: String, val body: String,
                val cc: List<String>?, val bcc: List<String>?, val attachments: List<Attachment>?)