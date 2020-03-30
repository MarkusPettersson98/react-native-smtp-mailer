//
//  MailSender.swift
//  RNSmtpMailer
//
//  Created by Markus Pettersson on 2020-03-26.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation

struct Attachment {
    let path: URL
    let name: String?
}

protocol MCOAttachable {
  func intoAttachable() -> MCOAttachment?
}

extension Attachment : MCOAttachable  {
  func intoAttachable() -> MCOAttachment? {
    if let name = self.name, let data = try? Data(contentsOf: self.path) {
        return MCOAttachment(data: data, filename: name)
    }
    if let data = try? Data(contentsOf: self.path) {
        return MCOAttachment(data:data, filename: self.path.lastPathComponent)
    }
    return nil
  }
}
