#import <Foundation/Foundation.h>

@interface NSURLAuthenticationChallenge (Fingerprint)
- (NSString *)SHA1Fingerprint;
- (NSString *)DomainName;
@end
