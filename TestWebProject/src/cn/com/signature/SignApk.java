/**
 * 
 */
package cn.com.signature;

/**
 * <p>Project:			<p>
 * <p>Module:			<p>
 * <p>Description:		<p>
 *
 * @author chenjinlong
 * @date 2015年7月27日 下午12:28:11
 */
public class SignApk {

	// public static void main( String[] args ) {
	// if( args.length != 4 && args.length != 5 ) {
	// System.err.println( "Usage: signapk [-w] " +
	// "publickey.x509[.pem] privatekey.pk8 " +
	// "input.jar output.jar" );
	// System.exit( 2 );
	// }
	//
	// BouncyCastleProvider BouncyCastleProvider = new BouncyCastleProvider();
	// Security.addProvider( sBouncyCastleProvider );
	//
	// boolean signWholeFile = false;
	// int argstart = 0;
	// if( args[ 0 ].equals( "-w" ) ) {
	// signWholeFile = true;
	// argstart = 1;
	// }
	//
	// JarFile inputJar = null;
	// JarOutputStream outputJar = null;
	// FileOutputStream outputFile = null;
	//
	// try {
	// File publicKeyFile = new File( args[ argstart + 0 ] );
	// X509Certificate publicKey = readPublicKey( publicKeyFile );
	//
	// // Assume the certificate is valid for at least an hour.
	// long timestamp = publicKey.getNotBefore().getTime() + 3600L * 1000;
	//
	// PrivateKey privateKey = readPrivateKey( new File( args[ argstart + 1 ] )
	// );
	// inputJar = new JarFile( new File( args[ argstart + 2 ] ), false ); //
	// Don't
	// // verify.
	//
	// OutputStream outputStream = null;
	// if( signWholeFile ) {
	// outputStream = new ByteArrayOutputStream();
	// } else {
	// outputStream = outputFile = new FileOutputStream( args[ argstart + 3 ] );
	// }
	// outputJar = new JarOutputStream( outputStream );
	//
	// // For signing .apks, use the maximum compression to make
	// // them as small as possible (since they live forever on
	// // the system partition). For OTA packages, use the
	// // default compression level, which is much much faster
	// // and produces output that is only a tiny bit larger
	// // (~0.1% on full OTA packages I tested).
	// if( !signWholeFile ) {
	// outputJar.setLevel( 9 );
	// }
	//
	// JarEntry je;
	//
	// Manifest manifest = addDigestsToManifest( inputJar );
	//
	// // Everything else
	// copyFiles( manifest, inputJar, outputJar, timestamp );
	//
	// // otacert
	// if( signWholeFile ) {
	// addOtacert( outputJar, publicKeyFile, timestamp, manifest );
	// }
	//
	// // MANIFEST.MF
	// je = new JarEntry( JarFile.MANIFEST_NAME );
	// je.setTime( timestamp );
	// outputJar.putNextEntry( je );
	// manifest.write( outputJar );
	//
	// // CERT.SF
	// je = new JarEntry( CERT_SF_NAME );
	// je.setTime( timestamp );
	// outputJar.putNextEntry( je );
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// writeSignatureFile( manifest, baos );
	// byte[] signedData = baos.toByteArray();
	// outputJar.write( signedData );
	//
	// // CERT.RSA
	// je = new JarEntry( CERT_RSA_NAME );
	// je.setTime( timestamp );
	// outputJar.putNextEntry( je );
	// writeSignatureBlock( new CMSProcessableByteArray( signedData ),
	// publicKey, privateKey, outputJar );
	//
	// outputJar.close();
	// outputJar = null;
	// outputStream.flush();
	//
	// if( signWholeFile ) {
	// outputFile = new FileOutputStream( args[ argstart + 3 ] );
	// signWholeOutputFile( ( ( ByteArrayOutputStream )outputStream
	// ).toByteArray(),
	// outputFile, publicKey, privateKey );
	// }
	// } catch( Exception e ) {
	// e.printStackTrace();
	// System.exit( 1 );
	// } finally {
	// try {
	// if( inputJar != null )
	// inputJar.close();
	// if( outputFile != null )
	// outputFile.close();
	// } catch( IOException e ) {
	// e.printStackTrace();
	// System.exit( 1 );
	// }
	// }
	// }
}
