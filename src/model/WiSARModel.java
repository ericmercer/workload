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

			sim.setup(new NewWiSARTeam(), DebugMode.PROD, DurationMode.RAND, forJPF,manager);
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
			String graph = "python "+ f.substring(0, f.length()-2);
			python+=File.separator+"src"+File.separator+"python"+File.separator+"PostProcessing.py";
			graph += File.separator+"src"+File.separator+"python"+File.separator+"Graph.py";
			python ="python "+python +" -w 9";
				
			//gets actors and adds command line parameters
			
			f= f.substring(0, f.length()-2);
			f+=File.separator+"src"+File.separator+"model"+File.separator+"Header.h";
			File names = new File(f);
			try {
				BufferedReader br = new BufferedReader(new FileReader(names));
				String line;
				boolean actor = false;
				boolean graphs = false;
				while((line = br.readLine()) != null)
				{
					if(line.length() == 0)
						continue;
					else if (line.startsWith("/"))
						continue;
					else if (line.equals( "Actors:"))
					{
						actor = true;
						graphs = false;
					}
					else if (line.equals("Graphs:"))
					{
						actor = false;
						graphs = true;
					}
					else if (actor)
					{
						python+=" -a "+line;
					}
					else if (graphs)
					{
						graph+=" "+File.separator+line.substring(0, 1) + "C"+line.substring(1, 2)+"W"+File.separator+line.substring(3)+".csv";
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Runtime.getRuntime().exec(python);
			Thread.sleep(5000);
			Runtime.getRuntime().exec(graph);
		}
	}	
}