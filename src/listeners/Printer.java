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
		if ( HCDW == null || HCDW._cumulativeDecisionWorkload < path._cumulativeDecisionWorkload) {
			write( "HCDW.csv", path.toString() );
			HCDW = new Path(path._parentPath, path._cumulativeDecisionWorkload, path._cumulativeResourceWorkload, path._cumulativeTemporalWorkload, path._cumulativeDecisionMetrics, path._cumulativeResourceMetrics, path._cumulativeTemporalMetrics);
		}
		
		if ( HCRW == null || HCRW._cumulativeResourceWorkload < path._cumulativeResourceWorkload) {
			write( "HCRW.csv", path.toString() );
			HCRW = new Path(path._parentPath, path._cumulativeDecisionWorkload, path._cumulativeResourceWorkload, path._cumulativeTemporalWorkload, path._cumulativeDecisionMetrics, path._cumulativeResourceMetrics, path._cumulativeTemporalMetrics);
		}
		
		if ( HCTW == null || HCTW._cumulativeTemporalWorkload < path._cumulativeTemporalWorkload) {
			write( "HCTW.csv", path.toString() );
			HCTW = new Path(path._parentPath, path._cumulativeDecisionWorkload, path._cumulativeResourceWorkload, path._cumulativeTemporalWorkload, path._cumulativeDecisionMetrics, path._cumulativeResourceMetrics, path._cumulativeTemporalMetrics);
		}
		
		if ( LCDW == null || LCDW._cumulativeDecisionWorkload > path._cumulativeDecisionWorkload) {
			write( "LCDW.csv", path.toString() );
			LCDW = new Path(path._parentPath, path._cumulativeDecisionWorkload, path._cumulativeResourceWorkload, path._cumulativeTemporalWorkload, path._cumulativeDecisionMetrics, path._cumulativeResourceMetrics, path._cumulativeTemporalMetrics);
		}
		
		if ( LCRW == null || LCRW._cumulativeResourceWorkload > path._cumulativeResourceWorkload) {
			write( "LCRW.csv", path.toString() );
			LCRW = new Path(path._parentPath, path._cumulativeDecisionWorkload, path._cumulativeResourceWorkload, path._cumulativeTemporalWorkload, path._cumulativeDecisionMetrics, path._cumulativeResourceMetrics, path._cumulativeTemporalMetrics);
		}
		
		if ( LCTW == null || LCTW._cumulativeTemporalWorkload > path._cumulativeTemporalWorkload) {
			write( "LCTW.csv", path.toString() );
			LCTW = new Path(path._parentPath, path._cumulativeDecisionWorkload, path._cumulativeResourceWorkload, path._cumulativeTemporalWorkload, path._cumulativeDecisionMetrics, path._cumulativeResourceMetrics, path._cumulativeTemporalMetrics);
		}
		
		return path;
	}
	
	private boolean isMock( String actor ) {
		if (actor.contains("ater"))
			return true;
		return false;
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
