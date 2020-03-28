//
//  ConversionHelper.swift
//  RNSmtpMailer
//
//  Created by Markus Pettersson on 2020-03-28.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation

/* Helper functions for extracting values passed from javascript */

func toMail(maildata: [String : Any]) -> Mail {
    // Required arguments
    let from: String = RCTConvert.nsString(maildata["from"])
    let recipients: [String] = RCTConvert.nsStringArray(maildata["recipients"])
    let subject: String = RCTConvert.nsString(maildata["subject"])
    let body: String = RCTConvert.nsString(maildata["htmlBody"])
    
    // Optional arguments
    let attachmentNames = RCTConvert.nsStringArray(maildata["attachmentNames"]) ?? [String]()
    let attachmentPaths =
        (RCTConvert.nsStringArray(maildata["attachmentPaths"]) ?? [String]())
        .map { path in URL(fileURLWithPath: path) }
    
    let attachments: [Attachment] = zip(attachmentNames, attachmentPaths)
        .map {(name, path) in Attachment(name: name, path: path) }
    
    let cc: [String]? = maildata["cc"] as! [String]?
    let bcc: [String]? = maildata["bcc"] as! [String]?
    
    return Mail(
        from: from,
        recipients: recipients,
        subject: subject,
        body: body,
        cc: cc,
        bcc: bcc,
        attachments: attachments)
}

func toMailConfig(maildata: [String : Any]) -> Mailconfig {
    // Required arguments
    let username: String = RCTConvert.nsString(maildata["username"])
    let password: String = RCTConvert.nsString(maildata["password"])
    
    // Optional arguments
    let mailhost: String? = maildata["mailhost"] as! String?
    let port: UInt32? = maildata["port"] as! UInt32?
    
    return Mailconfig(
        username: username,
        password: password,
        mailhost: mailhost,
        port: port)
}
