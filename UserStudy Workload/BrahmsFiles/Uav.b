class Uav {
							// UAVs will communicate with UavInterface
	attributes:
		public boolean active;
		public int batteryLevel;
		public boolean selfDestruct;
		public string status;				// can be 'on the ground', 'in the air', 'exploded', or message from MM
		public string control;			// can be 'start flight', 'return to base', 'set destination', 'initiate countdown'
	
	activities:
		primitive_activity decreaseBattery() {
			priority: 1;
			max_duration: 100;
		}
		
		primitive_activity travelToDestination() {
			priority: 2;
			max_duration: 100;
		}
		
		primitive_activity selfDestruct() {
			priority: 3;
			max_duration: 30;
		}
	
	workframes:

		workframe wf_becomeActive {
			
			repeat: false;
			
			when(knownval(current.control = "start flight"))
			
			do{
				conclude((current.status = "in the air"), bc:100, fc:100);
				conclude((current.control = ""), bc:100, fc:100);
			}
		}
		
		workframe wf_setDestination {
			
			repeat: true;
			
			when(knownval(current.control = "set destination"))
			
			do{
				conclude((current.control = ""), bc:100, fc:100);
				travelToDestination();
				conclude((current.batteryLevel = current.batteryLevel - 1));
			}
		}
		
		workframe wf_flying{
			
			repeat: true;
			
			detectables:
				detectable deadBattery {
					when(whenever)
						detect((current.batteryLevel = 0), dc:100)
						then abort;				
				}
			
			when(knownval(current.status = "in the air"))
			
			do{
				decreaseBattery();
				conclude((current.batteryLevel = current.batteryLevel - 1));
			}
		}
		
		workframe wf_explode {
			
			repeat: false;
			
			when(knownval(current.batteryLevel = 0))
			
			do {
				conclude((current.status = "exploded"));
				conclude((current.active = false));
			}
		
		}
		
		workframe wf_selfDestruct {
			repeat: false;
			
			when(knownval(current.control = "initiate countdown"))
			
			do{
				selfDestruct();
				conclude((current.status = "exploded"));
				conclude((current.control = ""));
				conclude((current.active = false));
			}
		
		}

}

// Scenario 1 uses Uav 1 and 3 out of possible 1-3
// Scenario 2 uses Uav 1-4 out of possible 1-4
// Scenario 3 uses Uav 1-5
// Scenario 4 uses Uav 1-5

//object Uav1 instanceof Uav {
//
//	initial_beliefs:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//	
//	initial_facts:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//		
//}
//
//object Uav2 instanceof Uav {
//	
//	initial_beliefs:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//	
//	initial_facts:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//		
//}
//
//object Uav3 instanceof Uav {
//	
//	initial_beliefs:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//	
//	initial_facts:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//		
//}

//object Uav4 instanceof Uav {
//	
//	initial_beliefs:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//
//	initial_facts:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//
//}
//
//object Uav5 instanceof Uav {
//	
//	initial_beliefs:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//
//	initial_facts:
//		(current.active = false);
//		(current.batteryLevel = 100);
//		(current.selfDestruct = false);
//		(current.status = "on the ground");
//		(current.control = "");
//			
//}
