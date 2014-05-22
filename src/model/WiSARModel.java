package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
		RunsJPF forJPF = RunsJPF.NO;
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
			//gets location to run python post processing correctly
			String f = null;
			try {
				f = new File(".").getAbsolutePath();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String python = f.substring(0, f.length()-2);
			python+=File.separator+"PostProcessing.py";
			python ="python "+python +" -w 9";
				
			//gets actors and adds command line parameters
			try {
				f = new File(".").getAbsolutePath();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			f= f.substring(0, f.length()-2);
			f+=File.separator+"src"+File.separator+"model"+File.separator+"Header.h";
			File names = new File(f);
			try {
				BufferedReader br = new BufferedReader(new FileReader(names));
				String line;
				while((line = br.readLine()) != null)
				{
					python+=" -a "+line;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Process p = Runtime.getRuntime().exec(python);
		}
	}	
}