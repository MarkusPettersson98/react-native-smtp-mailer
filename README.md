# react-native-simple-mailer

A simple library enabling non-obtrusive mailing from React Native!

## Getting started

TODO

## Installation

TODO

Install this package with with `npm` or `yarn` !

This library has a peer-dependency on `react-native ^0.47.0` .

### iOS

Make sure to run `pod install` inside of your project's ios folder! This library depends on `mailcore2-ios` for the bridge between React Native and iOS.

### Android

No extra steps are necessary for Android, just install and go!

### Problems?

See docs

## Usage

``` javascript
import RNSmtpMailer from "react-native-smtp-mailer";

RNSmtpMailer.sendMail({
        // Required fields
        username: "mail-to-send-from@gmail.com",
        password: "password-for-above-mail",
        from: "address-sending-this-mail", // Probably the same as `username` 
        recipients: ["reciever1@gmail.com, reciever2@outlook.com"],
        subject: "subject",
        htmlBody: "<h1>header</h1><p>body</p>",

        // Optional fields
        mailhost: "smtp.gmail.com", // Defaults to "smtp.gmail.com" if nothing is specified
        port: 465, // Defaults to 465 if nothing is specified
        ssl: true, // defaults to true if nothing is specified. If ssl is set to false, TLS is enabled.

        cc: ["ccEmail1@gmail.com", "ccEmail2@outlook.com"],
        bcc: ["bccEmail1@gmail.com", "bccEmail2@outlook.com"],

        attachmentPaths: [
            RNFS.ExternalDirectoryPath + "/image.jpg",
            RNFS.DocumentDirectoryPath + "/test.txt",
            RNFS.DocumentDirectoryPath + "/test2.csv",
            RNFS.DocumentDirectoryPath + "/pdfFile.pdf",
            RNFS.DocumentDirectoryPath + "/zipFile.zip",
            RNFS.DocumentDirectoryPath + "/image.png"
        ],
        attachmentNames: [ // Names of the attached files
            "image.jpg",
            "firstFile.txt",
            "secondFile.csv",
            "pdfFile.pdf",
            "zipExample.zip",
            "pngImage.png"
        ],
        attachmentTypes: ["img", "txt", "csv", "pdf", "zip", "img"], // Necessary to preserve the order as the files in attachmentPaths
    })
    .then(success => console.log(success))
    .catch(err => console.log(err));
```

### Note

RNFS is from <a href="https://github.com/itinance/react-native-fs">react-native-fs</a> library, used just to demonstrate a way of accessing files on the phones local filesystem.

### Breakdown

#### Required arguments

##### `username` 

`type: String` 

The username of the email account to send the mail from. The library needs this to authenticate against the mail server.

##### `password` 

`type: String` 

Provide the password for the host email account. The library needs this to authenticate against the
mail server.

##### `from` 

`type: String` 

Specify from which email to send the mail from. This is probably the same as `username` .

##### `recipients` 

`type: String[]` 

All the recievers of your mail. You can specify one or more recievers in the list.

#### `subject` 

`type: String` 

The visible subject of the mail

#### `htmlBody` 

`type: String` 

The text provided in the mail. As the name of this fields conveys you can specify custom html to be
displayed as the message of your email.

#### Optional arguments

##### `mailhost` 

`type: String` 

`default value: "smtp.gmail.com"` 

The mail server that hosts the `username` email address. Depending on your situation you might need to
change this, e.g.if you are using Microsoft Outlook.

##### `port` 

`type: Integer (Javascript Number)` 

`default value: 465` 

The port which the mail server listens to. Default for Google's mail servers is port 465. Again, this 
might be dependant on your specific mail server.

##### `ssl` 

`type: boolean` 

`default value: true` 

SSL is recommended to have enabled. It allows for a secure encrypted communication between the mobile client and the mail server which is authenticated against.

##### `cc` 

`type: String[]` 

`default value: []` 

Allows to specify recievers which should recieve a copy of the mail.

##### `bcc` 

`type: String[]` 

`default value: []` 

Allows to specify recievers which should be concealed / not visible to other people in the communication.

##### `attachmentPaths` 

`type: String[]` 

`default value: []` 

Paths to files on the local file system.

##### `attachmentNames` 

`type: String[]` 

`default value: []` 

Only used in Android, these are renames of original files. In iOS filenames will be same as specified in path. In iOS-only application, `attachmentNames` can be left unprovided or empty.

##### `attachmentTypes` 

`type: String[]` 

`default value: []` 

Only used in Android.

In iOS-only application, you may leave `attachmentTypes` empty. Generally every image (either jpg, png, jpeg etc..) file should have "img", and every other file should have its corresponding type.

### Usage with Proguard

Add the following into android/app/proguard-rules.pro

``` 
-dontshrink
-keep class javax.** {*;}
-keep class com.sun.** {*;}
-keep class myjava.** {*;}
-keep class org.apache.harmony.** {*;}
-dontwarn java.awt.**
-dontwarn java.beans.Beans
-dontwarn javax.security.**
-dontwarn javax.activation.**
```

