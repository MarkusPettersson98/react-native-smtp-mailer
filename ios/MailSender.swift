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

struct Mailconfig {
    let username: String
    let password: String
    let mailhost: String?
    let port: UInt32?
}

struct Attachment {
    let name: String
    let path: URL
}

protocol MCOAttachable {
  func intoAttachable() -> MCOAttachment?
}

extension Attachment : MCOAttachable  {
  func intoAttachable() -> MCOAttachment? {
    if let data = try? Data(contentsOf: self.path) {
        return MCOAttachment(data: data, filename: self.name)
    }
    return nil
  }
}
