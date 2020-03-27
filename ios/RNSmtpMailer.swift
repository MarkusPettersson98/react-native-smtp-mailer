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
        
        //resolve(["status":"SUCCESS"])
        
        // Get data
        let mail = toMail(maildata: maildata)
        let mailConfig = toMailConfig(maildata: maildata)
        
        
        // Send email
        let smtpSession = configureSession(mailConfig)
        
        let builder = MCOMessageBuilder()
        //TODO: Maybe add displayname in future? E.g. MCOAddress(displayName: "Rool", mailbox: "itsrool@gmail.com")]
        builder.header.from = MCOAddress(mailbox: mail.from) //TDO: Same as above
        builder.header.to = mail.recipients.compactMap { MCOAddress(mailbox: $0) } // Remove any possible nil values
        builder.header.bcc = mail.bcc.compactMap { MCOAddress(mailbox: $0) }

        builder.header.subject = mail.subject
        builder.htmlBody = mail.body
        // Add attachments
        mail.attachments
            .compactMap { getAttachMentData(attachment: $0)}
            .compactMap { MCOAttachment(data: $0.1, filename: $0.0) }
            .forEach { builder.addAttachment($0) }
        
        let rfc822Data = builder.data()
        let sendOperation = smtpSession.sendOperation(with: rfc822Data)
        
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
    
    let attachmentNames = maildata["attachmentNames"] as! [String]
    let attachmentPaths = maildata["attachmentPaths"] as! [String]
    let attachmentTypes = maildata["attachmentTypes"] as! [String]
    
    let attachments: [Attachment] = zip(attachmentNames, zip(attachmentPaths, attachmentTypes))
        .map { Attachment(name: $0.0 , path: $0.1.0, type: $0.1.1) }
    
    // If bcc is nothing, just pass empty array
    let bcc: [String] = maildata["bcc"] as? [String] ?? [String]()
    
    return Mail(
        from: maildata["from"] as! String,
        recipients: maildata["recipients"] as! [String],
        subject: maildata["subject"] as! String,
        body: maildata["htmlBody"] as! String,
        bcc: bcc,
        attachments: attachments
    )
    
}

func toMailConfig(maildata: [String : Any]) -> Mailconfig { // Maildata is a map from String to Any
    return Mailconfig(
        username: maildata["username"] as! String,
        password: maildata["password"] as! String,
        mailhost: maildata["mailhost"] as! String,
        port: UInt32(maildata["port"] as! String) ?? 465,
        ssl: maildata["ssl"] as! Bool)
}


func configureSession(_ mailConfig: Mailconfig) -> MCOSMTPSession {
    let smtpSession = MCOSMTPSession()
    smtpSession.hostname = mailConfig.mailhost
    smtpSession.username = mailConfig.username
    smtpSession.password = mailConfig.password
    smtpSession.port = mailConfig.port
    smtpSession.authType = MCOAuthType.saslPlain
    smtpSession.connectionType = MCOConnectionType.TLS
    return smtpSession
}
