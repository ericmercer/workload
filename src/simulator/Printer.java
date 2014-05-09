package simulator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class Printer {
	private static Printer _printer;
	
	public static Printer getInstance( ) {
		if( _printer == null )
			_printer = new Printer( );
		return _printer;
	}
	
	public void print( String filename, String string, int workload ) {
		//filename+= Calendar.getInstance();
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
	public void printNoOutput( String filename, String string ) {
		//filename+= Calendar.getInstance();
		//System.out.println("printed " + filename + " with " + workload + " workload");
		try {
			FileWriter writer = new FileWriter( new File( "src/csvFiles/" + filename ) );
	
			writer.write( string );
			
			writer.close();
		} catch ( IOException e ) {
			System.err.println( "-- couldn't print " + filename );
			e.printStackTrace( );
		}
	}
	
	public void print( String filename, String string ) {
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
