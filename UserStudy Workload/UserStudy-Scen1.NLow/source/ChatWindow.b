
class ChatWindow {

	attributes:
		
		public int operatorMessages;		// # of new messages to the operator in the window
		public boolean newOpMessage;		// whether or not any new messages to operator on visible screen since last time it was checked
		public boolean newMessage;			// whether or not any new messages to anyone on visible screen since last time it was checked
		public string messageLength;		// length of message to operator(to determine how long it should take for op to read it); can be: null(if no message), short(5 sec), long(8 sec)
		public map messageList;					// list of all messages for the scenario
		public map visibleWindow;				// list of all messages 'visible' to operator (list of messages up to given point of time that haven't been read by operator previously)
		public int incompleteTasks;
		public int Time;
	
	initial_beliefs:
		(current.Time = 1);
		(current.incompleteTasks = 0);
		(Scenario_Clock.time = 1);
		(Tasks.incompleteTasks = 0);
		
	initial_facts:
		(current.Time = 1);
		(current.incompleteTasks = 0);
		(Scenario_Clock.time = 1);
		(Tasks.incompleteTasks = 0);
	
	activities:
		
		composite_activity displayMessage(int time) {
		
			activities:
			
//				communicate newMessage(int pri) {
//					priority: pri;
//					max_duration: 1;
//					with: Operator;
//					about:
//						send(current.messageLength = current.messageLength),
//						send(current.newOpMessage = current.newOpMessage),
//						send(current.newMessage = current.newMessage),
//						send(current.operatorMessages = current.operatorMessages),
//						send(current.messageList = current.messageList);
//	
//					when: end;
//				}

				broadcast newMessage(int pri) {
					priority: pri;
					random: false;
					max_duration: 10;
					
					about:
						send(current.messageLength = current.messageLength),
						send(current.newOpMessage = current.newOpMessage),
						send(current.newMessage = current.newMessage),
						send(current.operatorMessages = current.operatorMessages),
						send(current.incompleteTasks = current.incompleteTasks),
						send(current.messageList = current.messageList);
	
					when: start;
				}
			
			workframes:
			
				workframe wf_newOpMessage {
					repeat: false;
					
					variables:
						forone(string) opMessage;

					when(knownval(current.messageList(time) = opMessage) and 
							knownval(current.messageList(time) != "short message") and
							knownval(current.messageList(time) != "long message") and
							knownval(current.messageList(time) != "shortMessage shortTask") and
							knownval(current.messageList(time) != "longMessage shortTask") and
							knownval(current.messageList(time) != "shortMessage mediumTask") and
							knownval(current.messageList(time) != "longMessage mediumTask") and
							knownval(current.messageList(time) != "shortMessage longTask") and
							knownval(current.messageList(time) != "longMessage longTask") and
							knownval(current.messageList(time) != "shortMessage shortTask report") and
							knownval(current.messageList(time) != "longMessage shortTask report") and
							knownval(current.messageList(time) != "shortMessage mediumTask report") and
							knownval(current.messageList(time) != "longMessage mediumTask report") and
							knownval(current.messageList(time) != "shortMessage longTask report") and
							knownval(current.messageList(time) != "longMessage longTask report"))

					
					do {
						conclude((current.messageLength = opMessage));
						conclude((current.newOpMessage = true));
						conclude((current.newMessage = true));
						conclude((current.operatorMessages = current.operatorMessages + 1));
						conclude((current.Time = current.Time + 1));
						newMessage(time);
						conclude((current.messageLength = ""));
						conclude((current.newOpMessage = false));
						conclude((current.newMessage = false));
					}
				}
				
				workframe wf_newOpTask {
					repeat: false;
					
					variables:
						forone(string) opMessage;
					
					when(knownval(current.messageList(time) = opMessage) and 
							knownval(current.messageList(time) != "short message") and
							knownval(current.messageList(time) != "long message") and
							knownval(current.messageList(time) != "short opMessage") and
							knownval(current.messageList(time) != "long opMessage"))
					
					do {
						conclude((current.messageLength = opMessage));
						conclude((current.newOpMessage = true));
						conclude((current.newMessage = true));
						conclude((current.operatorMessages = current.operatorMessages + 1));
						conclude((current.incompleteTasks = current.incompleteTasks + 1));
						conclude((Tasks.incompleteTasks = Tasks.incompleteTasks + 1));
						conclude((current.Time = current.Time + 1));
						newMessage(time);
						conclude((current.messageLength = ""));
						conclude((current.newOpMessage = false));
						conclude((current.newMessage = false));
					}
				}
				
				workframe wf_newMessage {
					repeat: false;
					
					variables:
						forone(string) opMessage;
					
					when(knownval(current.messageList(time) = opMessage) and
							knownval(current.messageList(time) != "short opMessage") and
							knownval(current.messageList(time) != "long opMessage") and
							knownval(current.messageList(time) != "shortMessage shortTask") and
							knownval(current.messageList(time) != "longMessage shortTask") and
							knownval(current.messageList(time) != "shortMessage mediumTask") and
							knownval(current.messageList(time) != "longMessage mediumTask") and
							knownval(current.messageList(time) != "shortMessage longTask") and
							knownval(current.messageList(time) != "longMessage longTask") and
							knownval(current.messageList(time) != "shortMessage shortTask report") and
							knownval(current.messageList(time) != "longMessage shortTask report") and
							knownval(current.messageList(time) != "shortMessage mediumTask report") and
							knownval(current.messageList(time) != "longMessage mediumTask report") and
							knownval(current.messageList(time) != "shortMessage longTask report") and
							knownval(current.messageList(time) != "longMessage longTask report"))
					
					do {
						conclude((current.messageLength = opMessage));
						conclude((current.newMessage = true));
						conclude((current.Time = current.Time + 1));
						newMessage(time);
						conclude((current.messageLength = ""));
						conclude((current.newMessage = false));
					}
				}
		}
	
	workframes:

		workframe wf_displayMessage {
			repeat: true;
			
			variables:
				forone(int) newTime;
			
			when(knownval(current.Time = Scenario_Clock.time) and
					knownval(newTime = Scenario_Clock.time))
			
			do{
				conclude((current.Time = Scenario_Clock.time + 1));
				displayMessage(newTime);
			}
		}

}

//	"shortMessage shortTask", "shortMessage mediumTask", "shortMessage longTask", "longMessage shortTask", "longMessage mediumTask", "longMessage longTask"


//////////////////////////////////////////////////////////////
///////////////////////// SCENARIO 1 /////////////////////////
//////////////////////////////////////////////////////////////

object Scenario_Chat instanceof ChatWindow {

	initial_beliefs:
		(current.operatorMessages = 0);
		(current.newOpMessage = false);
		(current.newMessage = false);
		(current.messageLength = "null");
		(current.messageList(1) = "long opMessage");	
		(current.messageList(10) = "long opMessage");
		(current.messageList(18) = "short opMessage");
		(current.messageList(25) = "long opMessage");
		(current.messageList(30) = "short message");
		(current.messageList(40) = "long opMessage");
		(current.messageList(50) = "longMessage shortTask");
		(current.messageList(70) = "short opMessage");
		(current.messageList(80) = "shortMessage shortTask report");
		(current.messageList(95) = "short message");	
		(current.messageList(110) = "long message");
		(current.messageList(125) = "short message");
		(current.messageList(140) = "shortMessage shortTask report");
		(current.messageList(155) = "short message");
		(current.messageList(165) = "long opMessage");
		(current.messageList(180) = "short message");
		(current.messageList(190) = "short message");
		(current.messageList(196) = "shortMessage shortTask");
		(current.messageList(206) = "shortMessage mediumTask");
		(current.messageList(216) = "short message");
		(current.messageList(221) = "shortMessage shortTask report");
		(current.messageList(231) = "short message");
		(current.messageList(241) = "short message");
		(current.messageList(251) = "shortMessage shortTask");
		(current.messageList(261) = "short message");
		(current.messageList(266) = "short message");
		(current.messageList(271) = "short message");
		(current.messageList(276) = "shortMessage mediumTask");
		(current.messageList(286) = "long opMessage");
		(current.messageList(326) = "short message");
		(current.messageList(331) = "short message");
		(current.messageList(337) = "short message");
		(current.messageList(347) = "short message");
		(current.messageList(352) = "short message");
		(current.messageList(362) = "short message");
		(current.messageList(377) = "longMessage mediumTask");
		(current.messageList(387) = "short message");
		(current.messageList(402) = "shortMessage mediumTask");
		(current.messageList(417) = "short message");
		(current.messageList(430) = "shortMessage mediumTask");
		(current.messageList(442) = "short message");
		(current.messageList(452) = "short message");
		(current.messageList(457) = "long message");
		(current.messageList(462) = "shortMessage shortTask report");
		(current.messageList(492) = "short message");
		(current.messageList(502) = "long message");
						
	initial_facts:
		(current.operatorMessages = 0);
		(current.newOpMessage = false);
		(current.newMessage = false);
		(current.messageLength = "null");
		(current.messageList(1) = "long opMessage");	
		(current.messageList(10) = "long opMessage");
		(current.messageList(18) = "short opMessage");
		(current.messageList(25) = "long opMessage");
		(current.messageList(30) = "short message");
		(current.messageList(40) = "long opMessage");
		(current.messageList(50) = "longMessage shortTask");
		(current.messageList(70) = "short opMessage");
		(current.messageList(80) = "shortMessage shortTask report");
		(current.messageList(95) = "short message");	
		(current.messageList(110) = "long message");
		(current.messageList(125) = "short message");
		(current.messageList(140) = "shortMessage shortTask report");
		(current.messageList(155) = "short message");
		(current.messageList(165) = "long opMessage");
		(current.messageList(180) = "short message");
		(current.messageList(190) = "short message");
		(current.messageList(196) = "shortMessage shortTask");
		(current.messageList(206) = "shortMessage mediumTask");
		(current.messageList(216) = "short message");
		(current.messageList(221) = "shortMessage shortTask report");
		(current.messageList(231) = "short message");
		(current.messageList(241) = "short message");
		(current.messageList(251) = "shortMessage shortTask");
		(current.messageList(261) = "short message");
		(current.messageList(266) = "short message");
		(current.messageList(271) = "short message");
		(current.messageList(276) = "shortMessage mediumTask");
		(current.messageList(286) = "long opMessage");
		(current.messageList(326) = "short message");
		(current.messageList(331) = "short message");
		(current.messageList(337) = "short message");
		(current.messageList(347) = "short message");
		(current.messageList(352) = "short message");
		(current.messageList(362) = "short message");
		(current.messageList(377) = "longMessage mediumTask");
		(current.messageList(387) = "short message");
		(current.messageList(402) = "shortMessage mediumTask");
		(current.messageList(417) = "short message");
		(current.messageList(430) = "shortMessage mediumTask");
		(current.messageList(442) = "short message");
		(current.messageList(452) = "short message");
		(current.messageList(457) = "long message");
		(current.messageList(462) = "shortMessage shortTask report");
		(current.messageList(492) = "short message");
		(current.messageList(502) = "long message");
	
}


//////////////////////////////////////////////////////////////
///////////////////////// SCENARIO 2 /////////////////////////
//////////////////////////////////////////////////////////////

//object Scenario_Chat instanceof ChatWindow {
//
//	initial_beliefs:
//		(current.operatorMessages = 0);
//		(current.newOpMessage = false);
//		(current.newMessage = false);
//		(current.messageLength = "null");
//		(current.messageList(1) = "short opMessage");	
//		(current.messageList(5) = "short opMessage");
//		(current.messageList(15) = "long opMessage");
//		(current.messageList(25) = "short message");
//		(current.messageList(35) = "short opMessage");
//		(current.messageList(45) = "longMessage shortTask");
//		(current.messageList(65) = "long opMessage");
//		(current.messageList(80) = "shortMessage shortTask report");
//		(current.messageList(90) = "shortMessage shortTask report");
//		(current.messageList(100) = "short message");	
//		(current.messageList(105) = "long message");
//		(current.messageList(115) = "short message");
//		(current.messageList(125) = "shortMessage shortTask report");
//		(current.messageList(140) = "short message");
//		(current.messageList(150) = "short opMessage");
//		(current.messageList(155) = "short message");
//		(current.messageList(160) = "short message");
//		(current.messageList(166) = "shortMessage shortTask");
//		(current.messageList(176) = "shortMessage mediumTask");
//		(current.messageList(191) = "short message");
//		(current.messageList(196) = "shortMessage shortTask report");
//		(current.messageList(201) = "shortMessage shortTask");
//		(current.messageList(211) = "shortMessage mediumTask");
//		(current.messageList(216) = "short message");
//		(current.messageList(221) = "short message");
//		(current.messageList(226) = "shortMessage shortTask");
//		(current.messageList(236) = "shortMessage mediumTask");
//		(current.messageList(246) = "short message");
//		(current.messageList(251) = "long opMessage");
//		(current.messageList(286) = "short message");
//		(current.messageList(296) = "shortMessage shortTask");
//		(current.messageList(306) = "short message");
//		(current.messageList(311) = "shortMessage mediumTask");
//		(current.messageList(326) = "shortMessage shortTask report");
//		(current.messageList(336) = "long message");
//		(current.messageList(341) = "short message");
//		(current.messageList(351) = "shortMessage shortTask");
//		(current.messageList(371) = "shortMessage shortTask report");
//		(current.messageList(381) = "short message");
//		(current.messageList(386) = "short message");
//		(current.messageList(396) = "shortMessage shortTask report");
//		(current.messageList(406) = "short message");
//		(current.messageList(411) = "short message");
//		(current.messageList(421) = "longMessage mediumTask");
//		(current.messageList(436) = "short message");
//		(current.messageList(441) = "shortMessage mediumTask");
//		(current.messageList(451) = "short message");
//		(current.messageList(461) = "short message");
//		(current.messageList(466) = "short message");
//		(current.messageList(476) = "shortMessage mediumTask");
//		(current.messageList(486) = "short message");
//		(current.messageList(491) = "shortMessage shortTask report");
//		(current.messageList(511) = "short message");
//		(current.messageList(521) = "long message");
//		(current.messageList(526) = "shortMessage mediumTask");
//						
//	initial_facts:
//		(current.operatorMessages = 0);
//		(current.newOpMessage = false);
//		(current.newMessage = false);
//		(current.messageLength = "null");
//		(current.messageList(1) = "short opMessage");	
//		(current.messageList(5) = "short opMessage");
//		(current.messageList(15) = "long opMessage");
//		(current.messageList(25) = "short message");
//		(current.messageList(35) = "short opMessage");
//		(current.messageList(45) = "longMessage shortTask");
//		(current.messageList(65) = "long opMessage");
//		(current.messageList(80) = "shortMessage shortTask report");
//		(current.messageList(90) = "shortMessage shortTask report");
//		(current.messageList(100) = "short message");	
//		(current.messageList(105) = "long message");
//		(current.messageList(115) = "short message");
//		(current.messageList(125) = "shortMessage shortTask report");
//		(current.messageList(140) = "short message");
//		(current.messageList(150) = "short opMessage");
//		(current.messageList(155) = "short message");
//		(current.messageList(160) = "short message");
//		(current.messageList(166) = "shortMessage shortTask");
//		(current.messageList(176) = "shortMessage mediumTask");
//		(current.messageList(191) = "short message");
//		(current.messageList(196) = "shortMessage shortTask report");
//		(current.messageList(201) = "shortMessage shortTask");
//		(current.messageList(211) = "shortMessage mediumTask");
//		(current.messageList(216) = "short message");
//		(current.messageList(221) = "short message");
//		(current.messageList(226) = "shortMessage shortTask");
//		(current.messageList(236) = "shortMessage mediumTask");
//		(current.messageList(246) = "short message");
//		(current.messageList(251) = "long opMessage");
//		(current.messageList(286) = "short message");
//		(current.messageList(296) = "shortMessage shortTask");
//		(current.messageList(306) = "short message");
//		(current.messageList(311) = "shortMessage mediumTask");
//		(current.messageList(326) = "shortMessage shortTask report");
//		(current.messageList(336) = "long message");
//		(current.messageList(341) = "short message");
//		(current.messageList(351) = "shortMessage shortTask");
//		(current.messageList(371) = "shortMessage shortTask report");
//		(current.messageList(381) = "short message");
//		(current.messageList(386) = "short message");
//		(current.messageList(396) = "shortMessage shortTask report");
//		(current.messageList(406) = "short message");
//		(current.messageList(411) = "short message");
//		(current.messageList(421) = "longMessage mediumTask");
//		(current.messageList(436) = "short message");
//		(current.messageList(441) = "shortMessage mediumTask");
//		(current.messageList(451) = "short message");
//		(current.messageList(461) = "short message");
//		(current.messageList(466) = "short message");
//		(current.messageList(476) = "shortMessage mediumTask");
//		(current.messageList(486) = "short message");
//		(current.messageList(491) = "shortMessage shortTask report");
//		(current.messageList(511) = "short message");
//		(current.messageList(521) = "long message");
//		(current.messageList(526) = "shortMessage mediumTask");
//	
//}

//////////////////////////////////////////////////////////////
///////////////////////// SCENARIO 3 /////////////////////////
//////////////////////////////////////////////////////////////

//object Scenario_Chat instanceof ChatWindow {
//
//	initial_beliefs:
//		(current.operatorMessages = 0);
//		(current.newOpMessage = false);
//		(current.newMessage = false);
//		(current.messageLength = "null");
//		(current.messageList(1) = "long opMessage");	
//		(current.messageList(5) = "short opMessage");
//		(current.messageList(10) = "short message");
//		(current.messageList(15) = "short message");
//		(current.messageList(20) = "short message");
//		(current.messageList(25) = "long message");
//		(current.messageList(30) = "shortMessage shortTask");
//		(current.messageList(40) = "shortMessage mediumTask");
//		(current.messageList(50) = "long message");
//		(current.messageList(55) = "short message");	
//		(current.messageList(65) = "shortMessage shortTask");
//		(current.messageList(80) = "long message");
//		(current.messageList(91) = "short message");
//		(current.messageList(96) = "shortMessage mediumTask");
//		(current.messageList(106) = "short opMessage");
//		(current.messageList(116) = "long message");
//		(current.messageList(126) = "short message");
//		(current.messageList(136) = "long opMessage");
//		(current.messageList(146) = "long opMessage");
//		(current.messageList(151) = "short opMessage");
//		(current.messageList(196) = "short message");
//		(current.messageList(206) = "longMessage shortTask");
//		(current.messageList(216) = "short message");
//		(current.messageList(221) = "short message");
//		(current.messageList(226) = "shortMessage mediumTask");
//		(current.messageList(236) = "short message");
//		(current.messageList(246) = "long message");
//		(current.messageList(256) = "short message");
//		(current.messageList(266) = "shortMessage mediumTask report");
//		(current.messageList(296) = "shortMessage mediumTask");
//		(current.messageList(304) = "short message");
//		(current.messageList(314) = "short message");
//		(current.messageList(319) = "short message");
//		(current.messageList(336) = "shortMessage mediumTask");
//		(current.messageList(349) = "long message");
//		(current.messageList(354) = "short message");
//		(current.messageList(364) = "short message");
//		(current.messageList(374) = "shortMessage mediumTask");
//		(current.messageList(384) = "shortMessage mediumTask");
//		(current.messageList(404) = "short message");
//		(current.messageList(409) = "short message");
//		(current.messageList(414) = "shortMessage mediumTask");
//		(current.messageList(439) = "short message");
//		(current.messageList(449) = "shortMessage shortTask");
//		(current.messageList(459) = "short message");
//		(current.messageList(464) = "short message");
//		(current.messageList(469) = "shortMessage shortTask report");
//		(current.messageList(474) = "short message");
//		(current.messageList(484) = "short message");
//		(current.messageList(494) = "short message");
//		(current.messageList(499) = "shortMessage shortTask");
//		(current.messageList(504) = "shortMessage shortTask");
//						
//	initial_facts:
//		(current.operatorMessages = 0);
//		(current.newOpMessage = false);
//		(current.newMessage = false);
//		(current.messageLength = "null");
//		(current.messageList(1) = "long opMessage");	
//		(current.messageList(5) = "short opMessage");
//		(current.messageList(10) = "short message");
//		(current.messageList(15) = "short message");
//		(current.messageList(20) = "short message");
//		(current.messageList(25) = "long message");
//		(current.messageList(30) = "shortMessage shortTask");
//		(current.messageList(40) = "shortMessage mediumTask");
//		(current.messageList(50) = "long message");
//		(current.messageList(55) = "short message");	
//		(current.messageList(65) = "shortMessage shortTask");
//		(current.messageList(80) = "long message");
//		(current.messageList(91) = "short message");
//		(current.messageList(96) = "shortMessage mediumTask");
//		(current.messageList(106) = "short opMessage");
//		(current.messageList(116) = "long message");
//		(current.messageList(126) = "short message");
//		(current.messageList(136) = "long opMessage");
//		(current.messageList(146) = "long opMessage");
//		(current.messageList(151) = "short opMessage");
//		(current.messageList(196) = "short message");
//		(current.messageList(206) = "longMessage shortTask");
//		(current.messageList(216) = "short message");
//		(current.messageList(221) = "short message");
//		(current.messageList(226) = "shortMessage mediumTask");
//		(current.messageList(236) = "short message");
//		(current.messageList(246) = "long message");
//		(current.messageList(256) = "short message");
//		(current.messageList(266) = "shortMessage mediumTask report");
//		(current.messageList(296) = "shortMessage mediumTask");
//		(current.messageList(304) = "short message");
//		(current.messageList(314) = "short message");
//		(current.messageList(319) = "short message");
//		(current.messageList(336) = "shortMessage mediumTask");
//		(current.messageList(349) = "long message");
//		(current.messageList(354) = "short message");
//		(current.messageList(364) = "short message");
//		(current.messageList(374) = "shortMessage mediumTask");
//		(current.messageList(384) = "shortMessage mediumTask");
//		(current.messageList(404) = "short message");
//		(current.messageList(409) = "short message");
//		(current.messageList(414) = "shortMessage mediumTask");
//		(current.messageList(439) = "short message");
//		(current.messageList(449) = "shortMessage shortTask");
//		(current.messageList(459) = "short message");
//		(current.messageList(464) = "short message");
//		(current.messageList(469) = "shortMessage shortTask report");
//		(current.messageList(474) = "short message");
//		(current.messageList(484) = "short message");
//		(current.messageList(494) = "short message");
//		(current.messageList(499) = "shortMessage shortTask");
//		(current.messageList(504) = "shortMessage shortTask");
//	
//}


//////////////////////////////////////////////////////////////
///////////////////////// SCENARIO 4 /////////////////////////
//////////////////////////////////////////////////////////////

//object Scenario_Chat instanceof ChatWindow {
//
//	initial_beliefs:
//		(current.operatorMessages = 0);
//		(current.newOpMessage = false);
//		(current.newMessage = false);
//		(current.messageLength = "null");
//		(Tasks.incompleteTasks = 0);
//		(current.messageList(1) = "long opMessage");	
//		(current.messageList(5) = "short opMessage");
//		(current.messageList(10) = "short message");
//		(current.messageList(15) = "short message");
//		(current.messageList(20) = "short message");
//		(current.messageList(25) = "short message");
//		(current.messageList(30) = "shortMessage shortTask");
//		(current.messageList(40) = "shortMessage mediumTask");
//		(current.messageList(50) = "long message");
//		(current.messageList(58) = "short message");	
//		(current.messageList(65) = "shortMessage shortTask");
//		(current.messageList(75) = "long message");
//		(current.messageList(81) = "short message");
//		(current.messageList(86) = "shortMessage mediumTask");
//		(current.messageList(96) = "short opMessage");
//		(current.messageList(106) = "shortMessage shortTask");
//		(current.messageList(111) = "shortMessage shortTask report");
//		(current.messageList(126) = "long message");
//		(current.messageList(133) = "short message");
//		(current.messageList(141) = "shortMessage mediumTask");
//		(current.messageList(156) = "long opMessage");
//		(current.messageList(161) = "long opMessage");
//		(current.messageList(166) = "short opMessage");
//		(current.messageList(206) = "short message");
//		(current.messageList(211) = "longMessage shortTask");
//		(current.messageList(221) = "short message");
//		(current.messageList(226) = "shortMessage mediumTask");
//		(current.messageList(236) = "short message");
//		(current.messageList(241) = "shortMessage mediumTask");
//		(current.messageList(246) = "short message");
//		(current.messageList(251) = "long message");
//		(current.messageList(261) = "short message");
//		(current.messageList(266) = "shortMessage mediumTask report");
//		(current.messageList(271) = "shortMessage longTask");
//		(current.messageList(276) = "shortMessage longTask");
//		(current.messageList(281) = "shortMessage mediumTask");
//		(current.messageList(289) = "short message");
//		(current.messageList(294) = "short message");
//		(current.messageList(299) = "short message");
//		(current.messageList(309) = "shortMessage longTask");
//		(current.messageList(314) = "short message");
//		(current.messageList(319) = "short message");
//		(current.messageList(324) = "shortMessage longTask report");
//		(current.messageList(344) = "short message");
//		(current.messageList(349) = "shortMessage longTask");
//		(current.messageList(354) = "short message");
//		(current.messageList(359) = "shortMessage mediumTask");
//		(current.messageList(369) = "short message");
//		(current.messageList(374) = "shortMessage mediumTask");
//		(current.messageList(384) = "shortMessage shortTask report");
//		(current.messageList(389) = "short message");
//		(current.messageList(394) = "short message");
//		(current.messageList(399) = "shortMessage shortTask report");
//		(current.messageList(409) = "short message");
//		(current.messageList(414) = "shortMessage shortTask");
//		(current.messageList(424) = "long message");
//		(current.messageList(429) = "short message");
//		(current.messageList(434) = "shortMessage shortTask");
//		(current.messageList(439) = "shortMessage shortTask");
//		(current.messageList(444) = "shortMessage shortTask");
//						
//	initial_facts:
//		(current.operatorMessages = 0);
//		(current.newOpMessage = false);
//		(current.newMessage = false);
//		(current.messageLength = "null");
//		(Tasks.incompleteTasks = 0);
//		(current.messageList(1) = "long opMessage");	
//		(current.messageList(5) = "short opMessage");
//		(current.messageList(10) = "short message");
//		(current.messageList(15) = "short message");
//		(current.messageList(20) = "short message");
//		(current.messageList(25) = "short message");
//		(current.messageList(30) = "shortMessage shortTask");
//		(current.messageList(40) = "shortMessage mediumTask");
//		(current.messageList(50) = "long message");
//		(current.messageList(58) = "short message");	
//		(current.messageList(65) = "shortMessage shortTask");
//		(current.messageList(75) = "long message");
//		(current.messageList(81) = "short message");
//		(current.messageList(86) = "shortMessage mediumTask");
//		(current.messageList(96) = "short opMessage");
//		(current.messageList(106) = "shortMessage shortTask");
//		(current.messageList(111) = "shortMessage shortTask report");
//		(current.messageList(126) = "long message");
//		(current.messageList(133) = "short message");
//		(current.messageList(141) = "shortMessage mediumTask");
//		(current.messageList(156) = "long opMessage");
//		(current.messageList(161) = "long opMessage");
//		(current.messageList(166) = "short opMessage");
//		(current.messageList(206) = "short message");
//		(current.messageList(211) = "longMessage shortTask");
//		(current.messageList(221) = "short message");
//		(current.messageList(226) = "shortMessage mediumTask");
//		(current.messageList(236) = "short message");
//		(current.messageList(241) = "shortMessage mediumTask");
//		(current.messageList(246) = "short message");
//		(current.messageList(251) = "long message");
//		(current.messageList(261) = "short message");
//		(current.messageList(266) = "shortMessage mediumTask report");
//		(current.messageList(271) = "shortMessage longTask");
//		(current.messageList(276) = "shortMessage longTask");
//		(current.messageList(281) = "shortMessage mediumTask");
//		(current.messageList(289) = "short message");
//		(current.messageList(294) = "short message");
//		(current.messageList(299) = "short message");
//		(current.messageList(309) = "shortMessage longTask");
//		(current.messageList(314) = "short message");
//		(current.messageList(319) = "short message");
//		(current.messageList(324) = "shortMessage longTask report");
//		(current.messageList(344) = "short message");
//		(current.messageList(349) = "shortMessage longTask");
//		(current.messageList(354) = "short message");
//		(current.messageList(359) = "shortMessage mediumTask");
//		(current.messageList(369) = "short message");
//		(current.messageList(374) = "shortMessage mediumTask");
//		(current.messageList(384) = "shortMessage shortTask report");
//		(current.messageList(389) = "short message");
//		(current.messageList(394) = "short message");
//		(current.messageList(399) = "shortMessage shortTask report");
//		(current.messageList(409) = "short message");
//		(current.messageList(414) = "shortMessage shortTask");
//		(current.messageList(424) = "long message");
//		(current.messageList(429) = "short message");
//		(current.messageList(434) = "shortMessage shortTask");
//		(current.messageList(439) = "shortMessage shortTask");
//		(current.messageList(444) = "shortMessage shortTask");
//	
//}