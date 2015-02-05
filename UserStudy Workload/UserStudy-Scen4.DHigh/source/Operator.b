agent Operator{

	attributes:
		public int incompleteTasks;			// # of incomplete tasks operator has
		public int internalTimer;				// used to regulate when operator will check message window
		public boolean checkMessages;		// used to control if he will check message window or not
		public int increment;						// value added to internalTimer in tf
		public boolean dontInterrupt;
		public int lastCheckedMessages;	// represents time that operator last checked the message window
		public int messageIterator;			// used to iterate over chatwindow's map from lastCheckedMessages time to current time
		public boolean seenFirstMessage; 		
	
	initial_beliefs:
		(current.incompleteTasks = 0);
		(current.checkMessages = true);
		(current.internalTimer = 5);		// this value gets incremented in t_f; once clock time equals this, operator will check message window
		(current.increment = 3);				// change this value to change how often operator will check message window
		(current.dontInterrupt = false);
		(current.lastCheckedMessages = 1);
		(current.messageIterator = 1);
		(current.seenFirstMessage = false);
		(Scenario_Clock.time = 1);
		(Tasks.incompleteTasks = 0);
		(Scenario_Chat.messageLength = "null");
		(Scenario_Chat.newMessage = false);
		(Scenario_Chat.newOpMessage = false);
		(Scenario_Chat.operatorMessages = 0);
		(Scenario_Chat.incompleteTasks = 0);
		(Scenario_Chat.messageList = unknown);
		(Map.terrain = unknown);
	
	initial_facts:
		(current.incompleteTasks = 0);
		(Scenario_Clock.time = 1);
		(Tasks.incompleteTasks = 0);
		(Scenario_Chat.messageLength = "null");
		(Scenario_Chat.newMessage = false);
		(Scenario_Chat.newOpMessage = false);
		(Scenario_Chat.operatorMessages = 0);
		(Scenario_Chat.incompleteTasks = 0);
		(Scenario_Chat.messageList = unknown);
		(Map.terrain = unknown);
	
	activities:
	
		composite_activity checkIfOpMessage(int time) {
			activities:
			
				communicate checkIfOpMessage() {
					random: false;
					max_duration: 2;
					with: Scenario_Chat;
					about:
						receive(Scenario_Chat.newOpMessage = unknown);
						
					when: end;
				}
			
			workframes:
				
				workframe wf_checkIfOpMessage {
					repeat: false;
					
					variables:
						forone(string) message;
					
					when(knownval(Scenario_Chat.messageList(time) = message))
					
					do {
						checkIfOpMessage();
					}
				}
		}
		// end of checkIfOpMessage ca
	
		composite_activity performTasks(int time) {
		
			activities:
			
				communicate checkOffTask() {
					random: true;
					min_duration: 10;
					max_duration: 20;
					with: Tasks;
					about:
						send(current.incompleteTasks = current.incompleteTasks);
		
					when: end;
				}
				
				communicate checkTaskWindow(){
					random: true;
					min_duration: 8;
					max_duration: 12;
					with: Tasks;
					about: 
						receive(Tasks.incompleteTasks = unknown);
						
					when: end;
				}
			
//				primitive_activity completeLongTask() {
//					priority: 6;
//					random: true;
//					min_duration: 45;
//					max_duration: 80;
//				}
//				
//				primitive_activity completeMediumTask() {
//					priority: 6;
//					random: true;
//					min_duration: 35;
//					max_duration: 60;
//				}
//				
//				primitive_activity completeShortTask() {
//					priority: 6;
//					random: true;
//					min_duration: 20;
//					max_duration: 30;
//				}
				
				communicate completeLongTask() {
					priority: 6;
					random: true;
					min_duration: 45;
					max_duration: 80;
					with: Map;
					about:
						receive(Map.terrain = unknown);
					when: end;
				}
				
				communicate completeMediumTask() {
					priority: 6;
					random: true;
					min_duration: 35;
					max_duration: 60;
					with: Map;
					about:
						receive(Map.terrain = unknown);
					when: end;
				}
				
				communicate completeShortTask() {
					priority: 6;
					random: true;
					min_duration: 20;
					max_duration: 30;
					with: Map;
					about:
						receive(Map.terrain = unknown);
					when: end;
				}
				
				communicate reportTaskComplete() {
					random: true;
					min_duration: 15;
					max_duration: 50;
					with: Scenario_Chat;
					about:
						send(current.incompleteTasks = current.incompleteTasks);
	
					when: end;
				}
			
			workframes:
		
				workframe wf_performingShortTask1 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage shortTask"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeShortTask();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}
				
				workframe wf_performingShortTask2 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage shortTask"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeShortTask();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}
				
				workframe wf_performingShortTaskReport1 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage shortTask report"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeShortTask();
						reportTaskComplete();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}

				workframe wf_performingShortTaskReport2 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage shortTask report"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeShortTask();
						reportTaskComplete();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}

				workframe wf_performingMediumTask1 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage mediumTask"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeMediumTask();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}
				
				workframe wf_performingMediumTask2 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage mediumTask"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeMediumTask();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}
				
				workframe wf_performingMediumTaskReport1 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage mediumTask report"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeMediumTask();
						reportTaskComplete();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}

				workframe wf_performingMediumTaskReport2 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage mediumTask report"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeMediumTask();
						reportTaskComplete();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}
				
				workframe wf_performingLongTask1 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage longTask"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeLongTask();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}
				
				workframe wf_performingLongTask2 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage longTask"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeLongTask();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}
				
				workframe wf_performingLongTaskReport1 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage longTask report"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeLongTask();
						reportTaskComplete();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}

				workframe wf_performingLongTaskReport2 {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage longTask report"))								
					
					do {
						checkTaskWindow();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
						completeLongTask();
						reportTaskComplete();
						checkOffTask();
						conclude((Scenario_Chat.incompleteTasks = Scenario_Chat.incompleteTasks - 1));
						conclude((current.incompleteTasks = current.incompleteTasks - time));
					}
				}
		}
		// end of performTasks ca
	
		composite_activity readMessage(int time) {
			activities:

				communicate readingShort() {
					priority: 6;
					random: true;
					min_duration: 30;
					max_duration: 55;
					with: Scenario_Chat;
					about:
						receive(Scenario_Chat.messageLength = unknown);
						
					when: end;
				}

				communicate readingLong() {
					priority: 6;
					random: true;
					min_duration: 65;
					max_duration: 90;
					with: Scenario_Chat;
					about:
						receive(Scenario_Chat.messageLength = unknown);
					when: end;
				}
				
			workframes:
				
				workframe wf_readingShort {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "short opMessage"))
					
					do {
						readingShort();
					}
				}
				
				workframe wf_readingLong {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "long opMessage"))
					
					do {
						readingLong();
					}
				}
				
				workframe wf_readingShortTask_Sh {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage shortTask"))
							
					do {
						readingShort();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingShortTask_ShRep {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage shortTask report"))
							
					do {
						readingShort();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingShortTask_Med {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage mediumTask"))
							
					do {
						readingShort();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingShortTask_MedRep {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage mediumTask report"))
							
					do {
						readingShort();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingShortTask_Lo {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage longTask"))
							
					do {
						readingShort();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingShortTask_LoRep {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "shortMessage longTask report"))
							
					do {
						readingShort();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingLongTask_Sh {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage shortTask"))	
					
					do {
						readingLong();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingLongTask_ShRep {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage shortTask report"))	
					
					do {
						readingLong();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingLongTask_Med {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage mediumTask"))	
					
					do {
						readingLong();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingLongTask_MedRep {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage mediumTask report"))	
					
					do {
						readingLong();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingLongTask_Lo {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage longTask"))	
					
					do {
						readingLong();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
				
				workframe wf_readingLongTask_LoRep {
					repeat: false;
					
					when(knownval(Scenario_Chat.messageList(time) = "longMessage longTask report"))	
					
					do {
						readingLong();
						conclude((current.incompleteTasks = current.incompleteTasks + time));
					}
				}
		}
		// end of readMessage ca

		communicate checkMessageWindow(){
			max_duration: 4;
			with: Scenario_Chat;
			about: 
				receive(Scenario_Chat.newOpMessage = unknown),
			 	receive(Scenario_Chat.newMessage = unknown),
				receive(Scenario_Chat.messageLength = unknown),
				receive(Scenario_Chat.incompleteTasks = unknown),
				receive(Scenario_Chat.messageList = unknown);
		}
		
	// End of Activities
	
	workframes:
	
		workframe wf_checkMessages {
			
			repeat: true;
			
			variables:
				//forone(MyClock) clock;
			
			when(knownval(current.checkMessages = true))
			
			do {
				checkMessageWindow();
				conclude((current.lastCheckedMessages = Scenario_Clock.time));
				conclude((current.checkMessages = false));
				conclude((current.seenFirstMessage = true));
				conclude((current.increment = 3), bc:50, fc:0);
				conclude((current.increment = 5), bc:25, fc:0);
			}
		}
		
		workframe wf_handleMessages {	
			
			repeat: true;
			
			variables:
				forone(int) curPosition;
			
			when(knownval(current.messageIterator <= current.lastCheckedMessages) and
						knownval(curPosition = current.messageIterator) and
						knownval(current.seenFirstMessage = true))
			
			do {
				conclude((current.dontInterrupt = true));
				
				checkIfOpMessage(curPosition);
				readMessage(curPosition);
				performTasks(curPosition);
				conclude((current.messageIterator = current.messageIterator + 1));

				conclude((current.dontInterrupt = false));
			}
		}	
	
	thoughtframes:

		thoughtframe tf_thinkToCheckMessages {
			repeat: true;
			
			when(knownval(Scenario_Clock.time >= current.internalTimer) and
					knownval(current.checkMessages = false) and 
					knownval(current.dontInterrupt = false))
			
			do {
				conclude((current.internalTimer = Scenario_Clock.time + current.increment));
				conclude((current.checkMessages = true));
			}
		}
		
}
