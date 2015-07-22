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
		final String[][] data = new String[][] { header, { "Lucy", "Ů", "22" },
				{ "Tom", "��", "25" }, { "Lily", "Ů", "19" } };

		writerCsv( "����.csv", header, data );

		readerCsv( "����.csv" );
	}

	/**
	 * ��ȡcsv
	 * 
	 * @param csvFilePath
	 * @throws Exception
	 */
	public static void readerCsv( String csvFilePath ) throws Exception {

		CsvReader reader = new CsvReader( csvFilePath, ',', Charset
				.forName( "GBK" ) );// shift_jis��������
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
		 * �������
		 */

		for( int i = 0; i < headers.length; i++ ) {
			System.out.print( headers[ i ] + "\t" );
		}

		System.out.println( "" );

		for( int i = 0; i < datas.length; i++ ) {

			Object[] data = datas[ i ]; // ȡ��һ������

			for( int j = 0; j < data.length; j++ ) {

				Object cell = data[ j ];
				System.out.print( cell + "\t" );
			}

			System.out.println( "" );
		}
	}

	/**
	 * д��csv
	 * 
	 * @param csvFilePath�ļ���·��
	 *            +�ļ�����
	 * @param header���ݱ�ͷ
	 * @param data������
	 */
	public static void writerCsv( String csvFilePath, String[] header,
			String[][] data ) {

		CsvWriter writer = null;
		try {
			writer = new CsvWriter( csvFilePath, ',', Charset.forName( "GBK" ) );// shift_jis��������

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
