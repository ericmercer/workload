package model;

import model.team.*;
import simulator.Simulator;
import simulator.Simulator.DebugMode;
import simulator.Simulator.DurationMode;

public class WiSARModel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Simulator sim = Simulator.getSim();
		
//		sim.setup(new OldWiSARTeam(), DebugMode.DEBUG, DurationMode.MIN_MAX);
//		
//		String checkSafe = sim.run();

		//find the first line that fails to match the fail safe
//		sim.setup(new NewWiSARTeam(), DebugMode.DEBUG, DurationMode.MIN_MAX);
//		String variable = sim.runSafe(checkSafe);
//		
		
//		//run the new code(everything below this line can be deleted once the model has been translated into the new language)
//		sim.setup(new NewWiSARTeam(), DebugMode.DEBUG, DurationMode.MIN_MAX);
//		
//		String variable = sim.run();
//		
//		if(checkSafe.equals(variable))
//			System.out.println("Identical Outputs");
//		else
//			System.out.println("difference in outputs");
		
		sim.setup(new NewWiSARTeam(), DebugMode.DEBUG, DurationMode.MIN_MAX);
		
		String checkSafe = sim.run();
	}
}
