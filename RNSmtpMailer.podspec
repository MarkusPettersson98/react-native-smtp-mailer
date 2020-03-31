require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "RNSmtpMailer"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.description  = <<-DESC
                  react-native-simple-mailer
                   DESC
  s.homepage     = "https://github.com/markuspettersson98/react-native-smtp-mailer"
  s.license      = { :type => "MIT", :file => "LICENSE" }
  s.author             = { "author" => "Markus Pettersson, angelos3lex" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/markuspettersson98/react-native-smtp-mailer.git", :tag => "master" }
  s.swift_version = "5.0"

  s.source_files  = "*.{h,m,swift}"
  s.requires_arc = true
  s.dependency "React"
  s.dependency "mailcore2-ios"
end
