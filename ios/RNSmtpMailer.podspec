
Pod::Spec.new do |s|
  s.name         = "RNSmtpMailer"
  s.version      = "1.2.1"
  s.summary      = "RNSmtpMailer"
  s.description  = <<-DESC
                  RNSmtpMailer
                   DESC
  s.homepage     = "https://github.com/markuspettersson98/react-native-smtp-mailer"
  s.license      = "MIT"
  # s.license      = { :type => "MIT", :file => "FILE_LICENSE" }
  s.author             = { "author" => "angelos3lex, Markus Pettersson" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/markuspettersson98/react-native-smtp-mailer.git", :tag => "master" }
  s.source_files  = "*.{h,m,swift}"
  s.requires_arc = true


  s.dependency "React"
  s.dependency "mailcore2-ios"

end
