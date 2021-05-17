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
<img src="https://raw.githubusercontent.com/nlt2390/react-native-pinning-ssl/master/getKey1.jpg" width="500"/>
<img src="https://raw.githubusercontent.com/nlt2390/react-native-pinning-ssl/master/getKey2.jpg" width="500"/>


## Usage
```javascript
import { isSSLValid } from 'react-native-pinning-ssl';

async function runSSLPinning(){
	try{
		const result = await isSSLValid({
			url: 'https://github.com/',
			hashes: ['CA 06 F5 6B 25 8B 7A 0D 4F 2B 05 47 09 39 47 86 51 15 19 84'],
			domainNames: ['github.com'],
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
<img src="https://www.pngjoy.com/pngm/90/1911973_bitcoin-logo-ada-cardano-logo-png-download.png" width="70"/>
Help to make cryptocurrency world bigger :))

My <a href="https://www.cardanohub.org/en/home/" target="_blank">ADA</a> wallet: 

addr1qyfv8tge2m5a6zxnxk9mga7n435tfpllrpxjy2ywljva367japfsnnvef3ttszwfdgphyhzcrw9tq0xpfjkpx4v2zydqcf52av
