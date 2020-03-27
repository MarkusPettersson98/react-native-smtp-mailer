//
//  MailSender.swift
//  RNSmtpMailer
//
//  Created by Markus Pettersson on 2020-03-26.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation

struct Mail {
    let from: String
    let recipients: [String]
    let subject: String
    let body: String
    let cc: [String]?
    let bcc: [String]?
    let attachments: [Attachment]
}

struct Attachment {
    let name: String
    let path: String
    let type: String
}

struct Mailconfig {
    let username: String
    let password: String
    let mailhost: String?
    let port: UInt32?
}

func getAttachMentData(attachment: Attachment) -> (String, Data)? {
    var result: (String, Data)?;
    
    do {
        let dataUrl = URL(fileURLWithPath: attachment.path);
        let data = try Data(contentsOf: dataUrl);
        result = (attachment.name, data)
    }
    catch {
        result = nil
    }
    return result
}
