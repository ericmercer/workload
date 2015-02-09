class UavInterface {
												// UavInterface will communicate with operator (visually, not actually sending comms to him
}

object UserMap instanceof UavInterface {

	attributes:
		public string terrain;
		public int dots;
		public int currentUavTab;
	
	initial_beliefs:
		(current.terrain = "");
		(current.dots = 0);
		(current.currentUavTab = 0);
	
	initial_facts:
		(current.terrain = "");
		(current.dots = 0);
		(current.currentUavTab = 0);
	
	activities:
	
	
	workframes:

		// do I even need to do anything here? we don't care about work performed by this 
		// and it is the other agents that update its variables

}

//object Map Scenario1 UavInterface {
//
//	initial_beliefs:
//		(current.terrain = "nature");
//		(current.dots = 6);
//		(current.currentUavTab = 0);
//	
//	initial_facts:
//		(current.terrain = "nature");
//		(current.dots = 6);
//		(current.currentUavTab = 0);
//
//}

//object Scenario2 instanceof UavInterface {
//
//	initial_beliefs:
//		(current.terrain = "nature");
//		(current.dots = 4);
//		(current.currentUavTab = 0);
//	
//	initial_facts:
//		(current.terrain = "nature");
//		(current.dots = 4);
//		(current.currentUavTab = 0);
//
//}
//
//object Scenario3 instanceof UavInterface {
//
//	initial_beliefs:
//		(current.terrain = "disney");
//		(current.dots = 0);
//		(current.currentUavTab = 0);
//	
//	initial_facts:
//		(current.terrain = "disney");
//		(current.dots = 0);
//		(current.currentUavTab = 0);
//
//}
//
//object Scenario4 instanceof UavInterface {
//
//	initial_beliefs:
//		(current.terrain = "disney");
//		(current.dots = 0);
//		(current.currentUavTab = 0);
//	
//	initial_facts:
//		(current.terrain = "disney");
//		(current.dots = 0);
//		(current.currentUavTab = 0);
//
//}
