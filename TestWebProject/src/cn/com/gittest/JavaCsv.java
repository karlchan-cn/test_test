package cn.com.gittest;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JavaCsv {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception {

		final String[] header = new String[] { "name", "sex", "age" };
		final String[][] data = new String[][] { header, { "Lucy", "女", "22" },
				{ "Tom", "男", "25" }, { "Lily", "女", "19" } };

		writerCsv( "测试.csv", header, data );

		readerCsv( "测试.csv" );
	}

	/**
	 * 读取csv
	 * 
	 * @param csvFilePath
	 * @throws Exception
	 */
	public static void readerCsv( String csvFilePath ) throws Exception {

		CsvReader reader = new CsvReader( csvFilePath, ',', Charset
				.forName( "GBK" ) );// shift_jis日语字体
		reader.readHeaders();
		String[] headers = reader.getHeaders();

		List<Object[]> list = new ArrayList<Object[]>();
		while( reader.readRecord() ) {
			list.add( reader.getValues() );
		}
		Object[][] datas = new String[ list.size() ][];
		for( int i = 0; i < list.size(); i++ ) {
			datas[ i ] = list.get( i );
		}

		/*
		 * 以下输出
		 */

		for( int i = 0; i < headers.length; i++ ) {
			System.out.print( headers[ i ] + "\t" );
		}

		System.out.println( "" );

		for( int i = 0; i < datas.length; i++ ) {

			Object[] data = datas[ i ]; // 取出一组数据

			for( int j = 0; j < data.length; j++ ) {

				Object cell = data[ j ];
				System.out.print( cell + "\t" );
			}

			System.out.println( "" );
		}
	}

	/**
	 * 写入csv
	 * 
	 * @param csvFilePath文件名路径
	 *            +文件名字
	 * @param header数据标头
	 * @param data数据项
	 */
	public static void writerCsv( String csvFilePath, String[] header,
			String[][] data ) {

		CsvWriter writer = null;
		try {
			writer = new CsvWriter( csvFilePath, ',', Charset.forName( "GBK" ) );// shift_jis日语字体

			for( int i = 0; i < data.length; i++ ) {
				writer.writeRecord( data[ i ] );
			}
		} catch( IOException e ) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

}
