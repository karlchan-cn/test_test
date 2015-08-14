package cn.com.signature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import sun.security.pkcs.PKCS7;

public class ApkSignatureToolsMain {

	private static String MD5 = "MD5";

	public static void main( String[] args )
			throws Exception {
		// String strSig =
		// "30820253308201bca00302010202044bbb0361300d06092a864886f70d0101050500306d310e300c06035504061305436"
		// +
		// "8696e61310f300d06035504080c06e58c97e4baac310f300d06035504070c06e58c97e4baac310f300d060355040a0c06e88"
		// +
		// "5bee8aeaf311b3019060355040b0c12e697a0e7babfe4b89ae58aa1e7b3bbe7bb9f310b30090603550403130251513020170d313"
		// +
		// "0303430363039343831375a180f32323834303132303039343831375a306d310e300c060355040613054368696e6"
		// +
		// "1310f300d06035504080c06e58c97e4baac310f300d06035504070c06e58c97e4baac310f300d060355040a0"
		// +
		// "c06e885bee8aeaf311b3019060355040b0c12e697a0e7babfe4b89ae58aa1e7b3bbe7bb9f310b300906035504031302515130"
		// +
		// "819f300d06092a864886f70d010101050003818d0030818902818100a15e9756216f694c5915e0b52909525436"
		// +
		// "7c4e64faeff07ae13488d946615a58ddc31a415f717d019edc6d30b9603d3e2a7b3de0ab7e0cf52dfee39373b"
		// +
		// "c472fa997027d798d59f81d525a69ecf156e885fd1e2790924386b2230cc90e3b7adc95603ddcf4c40bdc72f22db0f216"
		// +
		// "a99c371d3bf89cba6578c60699e8a0d536950203010001300d06092a864886f70d01010505000381810094a9b80e8"
		// +
		// "0691645dd42d6611775a855f71bcd4d77cb60a8e29404035a5e00b21bcc5d4a562482126bd91b6b0e50709377ceb9ef8c2"
		// +
		// "efd12cc8b16afd9a159f350bb270b14204ff065d843832720702e28b41491fbc3a205f5f2f42526d67f17614d8a974de6487b"
		// + "2c866efede3b4e49a0f916baa3c1336fd2ee1b1629652049";
		// String strWandou =
		// "30820253308201BCA00302010202044BBB0361300D06092A864886F70D0101050500306D310E300C0603550406130543"
		// +
		// "68696E61310F300D06035504080C06E58C97E4BAAC310F300D06035504070C06E58C97E4BAAC310F300D060355040A0C06E885B"
		// +
		// "EE8AEAF311B3019060355040B0C12E697A0E7BABFE4B89AE58AA1E7B3BBE7BB9F310B30090603550403130251513020170D3130"
		// +
		// "303430363039343831375A180F32323834303132303039343831375A306D310E300C060355040613054368696E61310F300D06035"
		// +
		// "504080C06E58C97E4BAAC310F300D06035504070C06E58C97E4BAAC310F300D060355040A0C06E885BEE8AEAF311B301906035504"
		// +
		// "0B0C12E697A0E7BABFE4B89AE58AA1E7B3BBE7BB9F310B300906035504031302515130819F300D06092A864886F70D0101010500"
		// +
		// "03818D0030818902818100A15E9756216F694C5915E0B529095254367C4E64FAEFF07AE13488D946615A58DDC31A415F717D019ED"
		// +
		// "C6D30B9603D3E2A7B3DE0AB7E0CF52DFEE39373BC472FA997027D798D59F81D525A69ECF156E885FD1E2790924386B2230CC90E3B"
		// +
		// "7ADC95603DDCF4C40BDC72F22DB0F216A99C371D3BF89CBA6578C60699E8A0D536950203010001300D06092A864886F70D0101050"
		// +
		// "5000381810094A9B80E80691645DD42D6611775A855F71BCD4D77CB60A8E29404035A5E00B21BCC5D4A562482126BD91B6B0E5070"
		// +
		// "9377CEB9EF8C2EFD12CC8B16AFD9A159F350BB270B14204FF065D843832720702E28B41491FBC3A205F5F2F42526D67F17614D8A974"
		// + "DE6487B2C866EFEDE3B4E49A0F916BAA3C1336FD2EE1B1629652049";
		// System.out.println( "equals ignore caset :" +
		// strWandou.equalsIgnoreCase( strSig ) );
		// System.out.println( md5Digest( strSig ) );
		// System.out.println( md5Digest( strWandou ) );
		String apkPath = "http://apps.wandoujia.com/redirect?signature=b1c9f18&url=http%3A%2F%2Fapk.wandoujia.com%2F6%2Fba%2F406fcf7b3d17567f"
				+ "61a8858b3b5f9ba6.apk&pn=com.qzone&md5=406fcf7b3d17567f61a8858b3b5f9ba6&apkid=14704662&vc=83&size=20860248&pos=t/detail";
		ApkSignatureToolsMain.printSignatureInfo( apkPath );

		httpDownload( "http://a2.res.meizu.com/source/35/66efa47b153b4f7faa17843ad7923e91?fname=com.qzone_83", "com.qzone_83.apk" );
		ApkSignatureToolsMain.printSignatureInfo( "com.qzone_83.apk" );

	}

	/**http下载*/
	public static boolean httpDownload( String httpUrl, String saveFile ) {
		// 下载网络文件
		int byteread = 0;

		URL url = null;
		try {
			url = new URL( httpUrl );
		} catch( MalformedURLException e ) {

			return false;
		}
		URLConnection conn = null;
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			conn = url.openConnection();
			inStream = conn.getInputStream();
			fs = new FileOutputStream( saveFile );

			byte[] buffer = new byte[ 1204 ];
			while( ( byteread = inStream.read( buffer ) ) != -1 ) {
				fs.write( buffer, 0, byteread );
			}
			return true;
		} catch( FileNotFoundException e ) {

			return false;
		} catch( IOException e ) {

			return false;
		} finally {
			try {
				if( inStream != null ) {
					inStream.close();
				}

			} catch( Exception e ) {
				e.printStackTrace();
			}
			try {
				if( fs != null ) {
					fs.close();
					fs.flush();
				}

			} catch( Exception e ) {
				e.printStackTrace();
			}

		}
	}

	public static void printSignatureInfo( String filePath ) {
		try {
			String apkPath = filePath;
			File apkFile = new File( apkPath );
			if( apkFile.exists() ) {
				String signature = getApkSignature( apkPath );

				String md5 = md5DigestStr( FileUtils.readFileToByteArray( apkFile ) );
				System.out.println( "signatureMd5=" + signature );
				System.out.println( "md5=" + md5 );
			} else {
				System.out.println( "Apk file not existed! " + apkPath );
			}
		} catch( Exception e ) {
			e.printStackTrace();
			throw new RuntimeException( e );
		}

	}

	public static String getApkSignature( String apkPath )
			throws IOException {
		List<String> signature = null;

		ZipFile zf = null;
		try {
			zf = new ZipFile( apkPath );
			Enumeration<ZipArchiveEntry> enu = zf.getEntries();
			while( enu.hasMoreElements() ) {
				ZipArchiveEntry entry = ( ZipArchiveEntry )enu.nextElement();
				if( ( !entry.isDirectory() ) && ( ( entry.getName().toUpperCase().endsWith( ".RSA" ) ) ||
						( entry.getName().toUpperCase().endsWith( ".DSA" ) ) ) ) {
					InputStream is = zf.getInputStream( entry );
					try {
						if( signature == null ) {
							signature = new ArrayList();
						}
						PKCS7 pkcs7 = new PKCS7( IOUtils.toByteArray( is ) );
						X509Certificate[] publicKey = pkcs7.getCertificates();
						for( X509Certificate cetificate : publicKey ) {
							String publicKeyString = byteToHexString( cetificate.getEncoded() );
							String md5PublicKeyStringApk = md5Digest( publicKeyString );
							if( !signature.contains( md5PublicKeyStringApk ) ) {
								signature.add( md5PublicKeyStringApk );
							}
						}
					} catch( GeneralSecurityException e ) {
						throw new IOException( e );
					} finally {
						IOUtils.closeQuietly( is );
					}
				}
			}
		} catch( IOException e ) {
			throw new IOException( e );
		} finally {
			ZipFile.closeQuietly( zf );
		}
		Collections.sort( signature );

		String matchContentString = StringUtils.join( signature.toArray( new String[ 0 ] ), "," );
		if( matchContentString == null ) {
			System.out.print( "NOMATCHCONTENT" );
		}
		return matchContentString;
	}

	private static String byteToHexString( byte[] bArray ) {
		StringBuffer sb = new StringBuffer( bArray.length );
		for( int i = 0; i < bArray.length; i++ ) {
			String sTemp = Integer.toHexString( 0xFF & ( char )bArray[ i ] );
			if( sTemp.length() < 2 ) {
				sb.append( 0 );
			}
			sb.append( sTemp.toUpperCase() );
		}
		return sb.toString();
	}

	private static MessageDigest getDigest( String algorithm ) {
		try {
			return MessageDigest.getInstance( algorithm );
		} catch( NoSuchAlgorithmException e ) {
			throw new RuntimeException( e.getMessage() );
		}
	}

	private static String md5DigestStr( byte[] input )
			throws IOException {
		return getHexString( md5Digest( input ) );
	}

	private static byte[] md5Digest( byte[] input )
			throws IOException {
		MessageDigest digest = getDigest( MD5 );
		digest.update( input );
		return digest.digest();
	}

	private static String md5Digest( String input )
			throws IOException {
		MessageDigest digest = getDigest( "MD5" );
		digest.update( input.getBytes() );
		return getHexString( digest.digest() );
	}

	private static String getHexString( byte[] digest ) {
		BigInteger bi = new BigInteger( 1, digest );
		return String.format( "%032x", new Object[] { bi } );
	}
}
