package com.mailercore

data class MailConfig(val mailhost: String, val port: Int, val ssl: Boolean) {
    constructor(
            mailhost: String? = null,
            port: Int? = null,
            ssl: Boolean? = null
    ) : this(
            mailhost = mailhost ?: "smtp.gmail.com",
            port = port ?: 465,
            ssl = ssl ?: true
    )
}