package model;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import model.team.*;
import simulator.*;
import simulator.Simulator.*;

public class WiSARModel {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, InterruptedException {
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

	}
	
}