package model;

import model.team.*;
import simulator.*;
import simulator.Simulator.*;

public class WiSARModel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Simulator sim = Simulator.getSim();
		
		sim.setup(new TestWiSARTeam(), DebugMode.DEBUG, DurationMode.MIN);
		
		sim.run();
	}
}
