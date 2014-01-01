package listeners;

import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.MethodInfo;
import gov.nasa.jpf.vm.ThreadInfo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Printer {
	static Printer _printer;
	
	Path HCDW;//Highest Cumulative Decision Workload
	Path HCRW;//Highest Cumulative Resource Workload
	Path HCTW;//Highest Cumulative Temporal Workload
	Path LCDW;//Lowest Cumulative Decision Workload
	Path LCRW;//Lowest Cumulative Resource Workload
	Path LCTW;//Lowest Cumulative Temporal Workload

	private Printer( ) {
		
	}
	
	public static Printer getInstance( ) {
		// TODO Auto-generated method stub
		if( _printer == null )
			_printer = new Printer();
		return _printer;
	}
	
	protected Path compareAndPrintCumlativeWorkload( Path path ) {
		
		return path;
	}

	private void write(String filename, String metrics) {
		try {
			FileWriter writer = new FileWriter(new File("src/csvFiles/" + filename));
	
			writer.write(metrics);
			
			writer.close();
		} catch (IOException e) {
			System.err.println("-- couldn't print " + filename + ".csv --");
			e.printStackTrace();
		}
	}
	
}
