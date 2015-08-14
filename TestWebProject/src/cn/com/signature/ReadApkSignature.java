package cn.com.signature;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ReadApkSignature {

	private static char[] toChars( byte[] mSignature ) {
		byte[] sig = mSignature;
		final int N = sig.length;
		final int N2 = N * 2;
		char[] text = new char[ N2 ];

		for( int j = 0; j < N; j++ ) {
			byte v = sig[ j ];
			int d = ( v >> 4 ) & 0xf;
			text[ j * 2 ] = ( char )( d >= 10 ? ( 'a' + d - 10 ) : ( '0' + d ) );
			d = v & 0xf;
			text[ j * 2 + 1 ] = ( char )( d >= 10 ? ( 'a' + d - 10 ) : ( '0' + d ) );
		}
		return text;
	}

	private static java.security.cert.Certificate[] loadCertificates( JarFile jarFile, JarEntry je, byte[] readBuffer ) {
		try {
			InputStream is = jarFile.getInputStream( je );
			while( is.read( readBuffer, 0, readBuffer.length ) != -1 ) {

			}
			is.close();
			return ( java.security.cert.Certificate[] )( je != null ? je.getCertificates() : null );
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( "Exception reading " + je.getName() + " in " + jarFile.getName() + ": " + e );
		}
		return null;
	}

	public static String getApkSignInfo( String apkFilePath ) {
		byte[] readBuffer = new byte[ 8192 ];
		java.security.cert.Certificate[] certs = null;
		try {
			JarFile jarFile = new JarFile( apkFilePath );
			Enumeration entries = jarFile.entries();
			while( entries.hasMoreElements() ) {
				JarEntry je = ( JarEntry )entries.nextElement();
				if( je.isDirectory() ) {
					continue;
				}
				if( je.getName().startsWith( "META-INF/" ) ) {
					continue;
				}
				java.security.cert.Certificate[] localCerts = loadCertificates( jarFile, je, readBuffer );
				// System.out.println( "File " + apkFilePath + " entry " +
				// je.getName() + ": certs=" + certs + " (" + ( certs != null ?
				// certs.length : 0 ) + ")" );
				if( certs == null ) {
					certs = localCerts;
				} else {
					for( int i = 0; i < certs.length; i++ ) {
						boolean found = false;
						for( int j = 0; j < localCerts.length; j++ ) {
							if( certs[ i ] != null && certs[ i ].equals( localCerts[ j ] ) ) {
								found = true;
								break;
							}
						}
						if( !found || certs.length != localCerts.length ) {
							jarFile.close();
							return null;
						}
					}
				}
			}
			jarFile.close();
			return new String( toChars( certs[ 0 ].getEncoded() ) );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main( String[] args ) {
		System.out.println( MD5Utils.digest( ReadApkSignature.getApkSignInfo( "E:\\Downloads\\com.tencent.mobileqq_260.apk" ) ) );
		System.out.println( MD5Utils.digest( ReadApkSignature.getApkSignInfo( "E:\\Downloads\\QQ-5.7.2-wandoujia.apk" ) ) );
	}
}
