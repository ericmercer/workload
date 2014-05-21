package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import model.scaffold.Interpreter;
import model.team.*;
import simulator.*;
import simulator.Simulator.*;

public class WiSARModel {
	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		RunsJPF forJPF = RunsJPF.YES;
		int times = (forJPF == RunsJPF.NO) ? 500: 1;
		for(int i = 0; i < times; i++)
		{
			Simulator sim = Simulator.getSim();

			WorkloadManager manager = null;
			//If you are not running JPF this sets up the manager to collect and interpret the metrics
			manager = new WorkloadManager();

			sim.setup(new NewWiSARTeam(), DebugMode.PROD, DurationMode.MIN_MAX, forJPF,manager);
			String checkSafe = sim.run();
			sim.clean();
		}
		
		
		if(forJPF == RunsJPF.NO)
		{
			String f = null;
			try {
				f = new File(".").getAbsolutePath();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			String python = f;
			python+=File.separator+"PostProcessing.py";
			Process p = Runtime.getRuntime().exec("python "+python +" 9");
		}
	}	
}