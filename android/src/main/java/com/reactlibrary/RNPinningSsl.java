package com.reactlibrary;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableArray;

import org.json.JSONArray;
import org.json.JSONException;

import javax.net.ssl.HttpsURLConnection;
import javax.security.cert.CertificateException;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;


public class RNPinningSsl extends ReactContextBaseJavaModule {
  private ReadableArray hashes;
  private ReadableArray domainNames;
  private String domainName;
  private String strCert;

  private static char[] HEX_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

  public RNPinningSsl(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
    return "RNPinningSsl";
  }

  @ReactMethod
  public void getStatus(String url, ReadableArray hashes, ReadableArray domainNames, final Promise promise)
  {
    try {
      strCert = "";
      final String serverURL = url;
      final String serverCertFingerprint = getFingerprint(serverURL);
      Boolean isValid = false;

      for(int i =0; i<domainNames.size(); i++){
        if( strCert.contains(domainNames.getString(i))){
          for (int j=0; j<hashes.size(); j++) {
            if (hashes.getString(j).equalsIgnoreCase(serverCertFingerprint)) {
              promise.resolve(true); 
              isValid = true;
              break;
            }
          }
          break;
        }
      }

      if(isValid == false){
        return false;
      }

    } catch (Exception e) {
      promise.reject("SSL Pinning", "Failed");
    }

  }

  private String getFingerprint(String httpsURL) throws IOException, NoSuchAlgorithmException, CertificateException, CertificateEncodingException {
    final HttpsURLConnection con = (HttpsURLConnection) new URL(httpsURL).openConnection();
    con.setConnectTimeout(5000);
    con.connect();
    final Certificate cert = con.getServerCertificates()[0];
    final MessageDigest mdSHA1 = MessageDigest.getInstance("SHA1");

    strCert = cert.toString();

    mdSHA1.update(cert.getEncoded());
    return dumpHex(mdSHA1.digest());
  }

  private String dumpHex(byte[] data) {
    final int n = data.length;
    final StringBuilder sb = new StringBuilder(n * 3 - 1);
    for (int i = 0; i < n; i++) {
      if (i > 0) {
        sb.append(' ');
      }
      sb.append(HEX_CHARS[(data[i] >> 4) & 0x0F]);
      sb.append(HEX_CHARS[data[i] & 0x0F]);
    }
    return sb.toString();
  }

}