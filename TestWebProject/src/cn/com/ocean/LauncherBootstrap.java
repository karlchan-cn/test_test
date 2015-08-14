package cn.com.ocean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

public class LauncherBootstrap {

	public static final String ANT_CLASSPATH_PROP_NAME = "ant.class.path";

	public static final String LAUNCHER_JAR_FILE_NAME = "commons-launcher.jar";

	public static final String LAUNCHER_PROPS_FILE_NAME = "launcher.properties";

	public static final String LAUNCHER_MAIN_CLASS_NAME = "org.apache.commons.launcher.Launcher";

	private static Class launcherClass = null;

	public static void main( String[] args ) {
		try {
			URL coreURL = LauncherBootstrap.class.getResource( "/commons-launcher.jar" );
			if( coreURL == null ) {
				throw new FileNotFoundException( "commons-launcher.jar" );
			}
			File coreDir = new File( URLDecoder.decode( coreURL.getFile() ) ).getCanonicalFile().getParentFile();

			File propsFile = new File( coreDir, "launcher.properties" );
			if( !propsFile.canRead() ) {
				throw new FileNotFoundException( propsFile.getPath() );
			}
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream( propsFile );
			props.load( fis );
			fis.close();

			URL[] antURLs = fileListToURLs( ( String )props.get( "ant.class.path" ) );
			URL[] urls = new URL[ 1 + antURLs.length ];
			urls[ 0 ] = coreURL;
			for( int i = 0; i < antURLs.length; i++ ) {
				urls[ ( i + 1 ) ] = antURLs[ i ];
			}
			ClassLoader parentLoader = Thread.currentThread().getContextClassLoader();
			URLClassLoader loader = null;
			if( parentLoader != null ) {
				loader = new URLClassLoader( urls, parentLoader );
			} else {
				loader = new URLClassLoader( urls );
			}
			launcherClass = loader.loadClass( "org.apache.commons.launcher.Launcher" );

			Method getLocalizedStringMethod = launcherClass.getDeclaredMethod( "getLocalizedString", new Class[] { String.class } );

			Method startMethod = launcherClass.getDeclaredMethod( "start", new Class[] { new String[ 0 ].getClass() } );
			int returnValue = ( ( Integer )startMethod.invoke( null, new Object[] { args } ) ).intValue();

			System.exit( returnValue );
		} catch( Throwable t ) {
			t.printStackTrace();
			System.exit( 1 );
		}
	}

	private static URL[] fileListToURLs( String fileList )
			throws MalformedURLException {
		if( ( fileList == null ) || ( "".equals( fileList ) ) ) {
			return new URL[ 0 ];
		}
		ArrayList list = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer( fileList, ":" );
		URL bootstrapURL = LauncherBootstrap.class.getResource( "/" + LauncherBootstrap.class.getName() + ".class" );
		while( tokenizer.hasMoreTokens() ) {
			list.add( new URL( bootstrapURL, tokenizer.nextToken() ) );
		}
		return ( URL[] )list.toArray( new URL[ list.size() ] );
	}
}
