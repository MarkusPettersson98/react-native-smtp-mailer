#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(RNSmtpMailer, NSObject)

RCT_EXTERN_METHOD(
                  sendMail: (NSDictionary *)maildata
                  resolver: (RCTPromiseResolveBlock)resolve
                  rejecter: (RCTPromiseRejectBlock)reject
                  )

@end

