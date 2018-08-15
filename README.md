# react-native-pinning-ssl
A react-native library for pinning SSL using SHA-1 public key.

## Getting started

`$ npm install react-native-pinning-ssl --save`

### Mostly automatic installation

`$ react-native link react-native-pinning-ssl`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-pinning-ssl` and add `RNPinningSsl.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNPinningSsl.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNPinningSslPackage;` to the imports at the top of the file
  - Add `new RNPinningSslPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-pinning-ssl'
  	project(':react-native-pinning-ssl').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-pinning-ssl/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-pinning-ssl')
  	```

## Get SHA-1 key
![](https://raw.githubusercontent.com/nlt2390/react-native-pinning-ssl/master/getKey1.jpg)

![](https://raw.githubusercontent.com/nlt2390/react-native-pinning-ssl/master/getKey2.jpg)


## Usage
```javascript
import { isSSLValid } from 'react-native-pinning-ssl';

async function runSSLPinning(){
	try{
		const result = await isSSLValid({
			url: 'https://www.google.com/',
			hashes: ['76 FB 50 5F 7C 81 7D 89 6B 42 14 24 43 DE 86 E7 3C D9 85 5F'],
			domainNames: ['google.com'],
		});
		
		return result;
	} catch (e) {
		console.log(e);
  }
}

runSSLPinning();
```

## References

NSURLAuthenticationChallenge+Fingerprint credited to [https://stackoverflow.com/a/26963381](https://stackoverflow.com/a/26963381)


#### Donation
<img src="https://www.cardanohub.org/wp-content/uploads/2017/07/cardano-symbol-150x150.png" width="70"/>
Help to make cryptocurrency world bigger :))

My <a href="https://www.cardanohub.org/en/home/" target="_blank">ADA</a> wallet: 

DdzFFzCqrht7vQmuSYrjn6PKDxW3on4cFznU4Jjjycp1bqVTvXngM8wN7sop1Haq7msc5NanULU8TyAG9UuLLZMynfroiHKr2aiiTaMM