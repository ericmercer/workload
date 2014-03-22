package listeners.copy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
	private static Printer _printer;
	
	public static Printer getInstance( ) {
		if( _printer == null )
			_printer = new Printer( );
		return _printer;
	}
	
	protected void print( String filename, String string, int workload ) {
		System.out.println("printed " + filename + " with " + workload + " workload");
		try {
			FileWriter writer = new FileWriter( new File( "src/csvFiles/" + filename ) );
	
			writer.write( string );
			
			writer.close();
		} catch ( IOException e ) {
			System.err.println( "-- couldn't print " + filename );
			e.printStackTrace( );
		}
	}
	
	protected void print( String filename, String string ) {
		try {
			FileWriter writer = new FileWriter( new File( "src/txtFiles/" + filename ) );
	
			writer.write( string );
			
			writer.close();
		} catch ( IOException e ) {
			System.err.println( "-- couldn't print " + filename );
			e.printStackTrace( );
		}
	}
	
}