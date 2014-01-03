package listeners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
	static Printer _printer;
	
	public static Printer getInstance( ) {
		// TODO Auto-generated method stub
		if( _printer == null )
			_printer = new Printer( );
		return _printer;
	}
	
	protected void print( String filename, String string ) {
		try {
			FileWriter writer = new FileWriter( new File( "src/csvFiles/" + filename ) );
	
			writer.write( string );
			
			writer.close();
		} catch ( IOException e ) {
			System.err.println( "-- couldn't print " + filename + ".csv --" );
			e.printStackTrace( );
		}
	}
	
}
