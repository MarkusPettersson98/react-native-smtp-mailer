//
//  mailconfig.swift
//  RNSmtpMailer
//
//  Created by Markus Pettersson on 2020-03-31.
//  Copyright © 2020 Facebook. All rights reserved.
//

import Foundation

struct Mailconfig {
    let username: String
    let password: String
    let mailhost: String?
    let port: UInt32?
}
