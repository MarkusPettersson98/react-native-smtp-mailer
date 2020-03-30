//
//  mail.swift
//  RNSmtpMailer
//
//  Created by Markus Pettersson on 2020-03-31.
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
