package model;

import simulator.*;
import simulator.Simulator.*;

public class WiSARModel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Simulator sim = Simulator.getSim();
		
		sim.setup(new WiSARTeam(), DebugMode.PROD, DurationMode.MIN_MAX);
		
		String checkSafe = sim.run();
	}
}
