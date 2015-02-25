class TaskWindow{
											// Operator will view TaskWindow('s variables) and will update them(by sending comm to enable workload?)
}

object Tasks instanceof TaskWindow{

	attributes:
		public int completeTasks;
		public int incompleteTasks;
		public string satisfaction;
	
	initial_beliefs:
		(current.completeTasks = 0);
		(current.incompleteTasks = 0);
		(current.satisfaction = "happy");		// satisfaction can be either: happy, neutral, or sad (depending on how many incomplete tasks there currently are); 0 tasks: happy, 1-2 tasks: neutral, 3+ tasks: sad
		(Scenario_Clock.time = 1);
		
	initial_facts:
		(current.completeTasks = 0);
		(current.incompleteTasks = 0);
		(current.satisfaction = "happy");
		(Scenario_Clock.time = 1);
	
	activities:
	
//		broadcast updateWindow() {
//			random: false;
//			max_duration: 1;
//
//			about: 
//				send(current.completeTasks = current.completeTasks),
//				send(current.incompleteTasks = current.incompleteTasks),
//				send(current.satisfaction = current.satisfaction);
//				
//			when: end;	 
//		}
	
	workframes:

//		workframe wf_happySatisfaction {
//			repeat: true;
//			
//			when(knownval(current.incompleteTasks = 0) and
//					knownval(Scenario_Clock.time < 511))
//			
//			do {
//				conclude((current.satisfaction = "happy"));
//				updateWindow();
//			}
//		}
//		
//		workframe wf_neutralSatisfaction {
//			repeat: true;
//			
//			when(knownval(current.incompleteTasks > 0) and 
//					knownval(current.incompleteTasks < 3) and
//					knownval(Scenario_Clock.time < 511))
//			
//			do {
//				conclude((current.satisfaction = "neutral"));
//				updateWindow();
//			}
//		}
//		
//		workframe wf_sadSatisfaction {
//			repeat: true;
//			
//			when(knownval(current.incompleteTasks > 2) and
//					knownval(Scenario_Clock.time < 511))
//			
//			do {
//				conclude((current.satisfaction = "sad"));
//				updateWindow();
//			}
//		}
}
