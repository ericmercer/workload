class MyClock  {

	attributes:
		public int time;
	
	activities:

			primitive_activity asTimeGoesBy(int pri) {
						priority: pri;
						random: false;
						max_duration: 9;		// this represents 10 centiseconds
			}

			broadcast announceTime() {
						random: false;
						max_duration: 1;

						about: 
							send(current.time = current.time);

						when: end;	 
			}
			
	workframes:
			
			workframe wf_asTimeGoesBy {
					repeat: true;
				            
					when(knownval(current.time < 512))	// (Scenario,length): (1,512), (2,541), (3,517), (4,451)

					do {
						asTimeGoesBy(1);
						conclude((current.time = current.time + 1), bc:100, fc:100);
						announceTime();
					}
			}	
		
}

object Scenario_Clock instanceof MyClock  {
		
	initial_beliefs:
		(current.time = 1);

	initial_facts:
		(current.time = 1);
}
