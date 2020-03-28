//
//  RNSmtpMailer.swift
//  RNSmtpMailer
//
//  Created by Markus Pettersson on 2020-03-26.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation


@objc(RNSmtpMailer)
class RNSmtpMailer : NSObject {
    
    @objc class func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    @objc
    func sendMail(_ maildata: [String: Any],
                  resolver resolve: @escaping (RCTPromiseResolveBlock),
                  rejecter reject: @escaping (RCTPromiseRejectBlock)) -> Void {
        
        // Get data
        let mail = toMail(maildata: maildata)
        let mailConfig = toMailConfig(maildata: maildata)
        
        
        // Configure SMTP-session
        let smtpSession = configureSession(mailConfig)
        
        let builder = MCOMessageBuilder()
        //TODO: Maybe add displayname in future? E.g. MCOAddress(displayName: "Rool", mailbox: "itsrool@gmail.com")]
        builder.header.from = MCOAddress(mailbox: mail.from) //TDO: Same as above
        // Add recipients
        builder.header.to = mail.recipients.compactMap { MCOAddress(mailbox: $0) } // Remove any possible nil values
        if let ccs = mail.cc {
            builder.header.cc = ccs.compactMap { MCOAddress(mailbox: $0) }
        }
        if let bccs = mail.bcc {
            builder.header.bcc = bccs.compactMap { MCOAddress(mailbox: $0) }
        }


        builder.header.subject = mail.subject
        builder.htmlBody = mail.body
        // Add attachments
        mail.attachments
            .compactMap { attachment in attachment.intoAttachable() }
            .forEach { builder.addAttachment($0) }
        
        // Create sendable email
        let rfc822Data = builder.data()
        let sendOperation = smtpSession.sendOperation(with: rfc822Data)
        
        // Try to send the mail
        sendOperation?.start { (error) -> Void in
            if (error != nil) {
                NSLog("Error sending email: \(String(describing: error))")
                reject("Error", error?.localizedDescription, error)
            } else {
                NSLog("Successfully sent email!")
                let result = ["status": "SUCCESS"]
                resolve(result)
            }
        }
 
    }
    
}

/* Helper functions for extracting values passed from javascript */

func toMail(maildata: [String : Any]) -> Mail {
    
    let attachmentNames = RCTConvert.nsStringArray(maildata["attachmentNames"]) ?? [String]()
    let attachmentPaths =
        (RCTConvert.nsStringArray(maildata["attachmentPaths"]) ?? [String]())
        .map { path in URL(fileURLWithPath: path) }
    
    let attachments: [Attachment] = zip(attachmentNames, attachmentPaths)
        .map {(name, path) in Attachment(name: name, path: path) }
    
    // Optional arguments
    let cc: [String]? = maildata["cc"] as! [String]?
    let bcc: [String]? = maildata["bcc"] as! [String]?
    
    // Required
    let from: String = RCTConvert.nsString(maildata["from"])
    let recipients: [String] = RCTConvert.nsStringArray(maildata["recipients"])
    let subject: String = RCTConvert.nsString(maildata["subject"])
    let body: String = RCTConvert.nsString(maildata["htmlBody"])
    
    return Mail(
        from: from,
        recipients: recipients,
        subject: subject,
        body: body,
        cc: cc,
        bcc: bcc,
        attachments: attachments
    )
    
}

func toMailConfig(maildata: [String : Any]) -> Mailconfig { // Maildata is a map from String to Any
    
    let username: String = RCTConvert.nsString(maildata["username"])
    let password: String = RCTConvert.nsString(maildata["password"])
    
    let mailhost: String? = maildata["mailhost"] as! String?
    let port: UInt32? = maildata["port"] as! UInt32?
    
    return Mailconfig(
        username: username,
        password: password,
        mailhost: mailhost,
        port: port)
    
}

/*
 Set default values and config settings for SMTP-session
 **/
func configureSession(_ mailConfig: Mailconfig) -> MCOSMTPSession {
    let smtpSession = MCOSMTPSession()
    smtpSession.username = mailConfig.username
    smtpSession.password = mailConfig.password
    smtpSession.hostname = mailConfig.mailhost ?? "smtp.gmail.com"
    smtpSession.port = mailConfig.port ?? 465
    smtpSession.authType = MCOAuthType.saslPlain
    smtpSession.connectionType = MCOConnectionType.TLS
    return smtpSession
}
