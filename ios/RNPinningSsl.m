#import "RNPinningSsl.h"
#import "NSURLAuthenticationChallenge+Fingerprint.h"

@implementation RNPinningSsl

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()


static NSString *serviceName = @"RNPinningSsl";

NSArray *hashes;
NSArray *domainNames;
BOOL isValid;

NSError * sslPinningError(NSString *errMsg)
{
  NSError *error = [NSError errorWithDomain:serviceName code:200 userInfo:@{@"reason": errMsg}];
  return error;
}


RCT_EXPORT_METHOD(getStatus:(NSString *)inputUrl
                  forHashes:(NSArray *)inputHashes
                  forDomainNames:(NSArray *) inputDomainNames
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject)
{
  hashes = inputHashes;
  domainNames = inputDomainNames;
  isValid = NO;
  NSURL *url = [NSURL URLWithString: inputUrl];
  
  NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration defaultSessionConfiguration];
  NSURLSession *session = [NSURLSession sessionWithConfiguration:configuration delegate:self delegateQueue:nil];
  
  NSURLSessionDataTask *task = [session dataTaskWithURL:url completionHandler:^(NSData *data, NSURLResponse *response, NSError *error) {
  
    if(error){
      reject(@"9", error.description,  sslPinningError(error.description));
    } else {
      if(isValid){
        resolve(@YES);
      } else {
        NSString *msgError = @"SSL pinning failed";
        reject(@"9", msgError,  sslPinningError(msgError));
      }
    }
    hashes = [NSMutableArray array];
    domainNames = [NSMutableArray array];
  }];
  
  [task resume];
}

- (void)URLSession:(NSURLSession *)session didReceiveChallenge:(NSURLAuthenticationChallenge *)challenge completionHandler:(void (^)(NSURLSessionAuthChallengeDisposition disposition, NSURLCredential *credential))completionHandler
{
  
  if ([challenge.protectionSpace.authenticationMethod isEqualToString:NSURLAuthenticationMethodServerTrust]) {
    for (NSString* hash in hashes) {
        
      if ([hash caseInsensitiveCompare:challenge.SHA1Fingerprint] == NSOrderedSame) {
        if( [domainNames containsObject:challenge.DomainName]){
          isValid = YES;
          break;
        }
      }
    }
  }
  
  completionHandler(NSURLSessionAuthChallengePerformDefaultHandling, nil);
}


@end
  
