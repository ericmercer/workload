import os
import sys
import time

class ConfigUpdater():

	def __init__(self):
		#filename = os.getcwd() + '/brahms-translate/examples/UserStudy/config.cfg'
                filename = '/Users/egm/Documents/bitbucket/brahms-verification/examples/UserStudy/config.cfg'
                
		weights = ['1', # wf_readingShort.decisionWeight = 1
                           '1', # wf_readingShort.temporalWeight = 1
                           '1', # wf_readingShortTask_LoRep.decisionWeight = 1
                           '2', # wf_readingShortTask_LoRep.temporalWeight = 2
                           '1', # wf_readingShortTask_ShRep.decisionWeight = 1
                           '2', # wf_readingShortTask_ShRep.temporalWeight = 2
                           '1', # wf_readingShortTask_Med.decisionWeight = 1
                           '2', # wf_readingShortTask_Med.temporalWeight = 2
                           '1', # wf_readingShortTask_Lo.decisionWeight = 1
                           '2', # wf_readingShortTask_Lo.temporalWeight = 2
                           '1', # wf_readingShortTask_Sh.decisionWeight = 1
                           '2', # wf_readingShortTask_Sh.temporalWeight = 2
                           '1', # wf_readingLongTask_Lo.decisionWeight = 1
                           '4', # wf_readingLongTask_Lo.temporalWeight = 4
                           '1', # wf_readingLongTask_MedRep.decisionWeight = 1
                           '4', # wf_readingLongTask_MedRep.temporalWeight = 4
                           '1', # wf_readingLongTask_Sh.decisionWeight = 1
                           '4', # wf_readingLongTask_Sh.temporalWeight = 4
                           '1', # wf_readingLongTask_Med.decisionWeight = 1
                           '4', # wf_readingLongTask_Med.temporalWeight = 4
                           '1', # wf_readingLongTask_LoRep.decisionWeight = 1
                           '4', # wf_readingLongTask_LoRep.temporalWeight = 4
                           '1', # wf_readingLongTask_ShRep.decisionWeight = 1
                           '4', # wf_readingLongTask_ShRep.temporalWeight = 4
                           '1', # wf_readingShortTask_MedRep.decisionWeight = 1
                           '2', # wf_readingShortTask_MedRep.temporalWeight = 2
                           '1', # wf_readingLong.decisionWeight = 1
                           '3', # wf_readingLong.temporalWeight = 3
                           '1', # wf_checkIfOpMessage.decisionWeight = 1
                           '1', # wf_checkIfOpMessage.temporalWeight = 1
                           '1', # wf_performingShortTask1.decisionWeight = 1
                           '3', # wf_performingShortTask1.temporalWeight = 3
                           '1', # wf_performingMediumTask2.decisionWeight = 1
                           '4', # wf_performingMediumTask2.temporalWeight = 4
                           '1', # wf_performingMediumTask1.decisionWeight = 1
                           '4', # wf_performingMediumTask1.temporalWeight = 4
                           '1', # wf_performingLongTaskReport2.decisionWeight = 1
                           '6', # wf_performingLongTaskReport2.temporalWeight = 6
                           '1', # wf_performingLongTaskReport1.decisionWeight = 1
                           '6', # wf_performingLongTaskReport1.temporalWeight = 6
                           '1', # wf_performingMediumTaskReport2.decisionWeight = 1
                           '5', # wf_performingMediumTaskReport2.temporalWeight = 5
                           '1', # wf_performingMediumTaskReport1.decisionWeight = 1
                           '5', # wf_performingMediumTaskReport1.temporalWeight = 5
                           '1', # wf_performingShortTaskReport2.decisionWeight = 1
                           '4', # wf_performingShortTaskReport2.temporalWeight = 4
                           '1', # wf_performingShortTaskReport1.decisionWeight = 1
                           '4', # wf_performingShortTaskReport1.temporalWeight = 4
                           '1', # wf_performingLongTask2.decisionWeight = 1
                           '5', # wf_performingLongTask2.temporalWeight = 5
                           '1', # wf_performingLongTask1.decisionWeight = 1
                           '5', # wf_performingLongTask1.temporalWeight = 5
                           '1', # wf_performingShortTask2.decisionWeight = 1
                           '3', # wf_performingShortTask2.temporalWeight = 3
                           '1', # wf_handleMessages.decisionWeight = 1
                           '3', # wf_handleMessages.temporalWeight = 3
                           '1', # wf_checkMessages.decisionWeight = 1
                           '1', # wf_checkMessages.temporalWeight = 1
                           '3', # {Operator_wf_readingShortTask_LoRep_xox35=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 3
                           '3', # {Operator_wf_readingShortTask_ShRep_xox42=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 3
                           '2', # {Operator_wf_readingShortTask_Med_xox49=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 2
                           '2', # {Operator_wf_readingShortTask_Lo_xox56=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 2
                           '2', # {Operator_wf_readingShortTask_Sh_xox63=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 2
                           '4', # {Operator_wf_readingLongTask_Lo_xox70=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 4
                           '5', # {Operator_wf_readingLongTask_MedRep_xox77=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 5
                           '4', # {Operator_wf_readingLongTask_Sh_xox84=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 4
                           '4', # {Operator_wf_readingLongTask_Med_xox91=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 4
                           '5', # {Operator_wf_readingLongTask_LoRep_xox98=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 5
                           '5', # {Operator_wf_readingLongTask_ShRep_xox105=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 5
                           '3', # {Operator_wf_readingShortTask_MedRep_xox112=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 3
                           '1', # {Operator_wf_performingShortTask1_xox149=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingShortTask1_xox152=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '1', # {Operator_wf_performingShortTask1_xox155=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingMediumTask2_xox162=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingMediumTask2_xox165=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '1', # {Operator_wf_performingMediumTask2_xox168=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingMediumTask1_xox175=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingMediumTask1_xox178=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '1', # {Operator_wf_performingMediumTask1_xox181=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingLongTaskReport2_xox188=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingLongTaskReport2_xox191=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '5', # {Operator_wf_performingLongTaskReport2_xox194=Scenario_Chat.incompleteTasks EQ var(Scenario_Chat).incompleteTasks MINUS 1}.weight = 5
                           '1', # {Operator_wf_performingLongTaskReport2_xox197=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingLongTaskReport1_xox204=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingLongTaskReport1_xox207=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '5', # {Operator_wf_performingLongTaskReport1_xox210=Scenario_Chat.incompleteTasks EQ var(Scenario_Chat).incompleteTasks MINUS 1}.weight = 5
                           '1', # {Operator_wf_performingLongTaskReport1_xox213=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingMediumTaskReport2_xox220=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingMediumTaskReport2_xox223=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '5', # {Operator_wf_performingMediumTaskReport2_xox226=Scenario_Chat.incompleteTasks EQ var(Scenario_Chat).incompleteTasks MINUS 1}.weight = 5
                           '1', # {Operator_wf_performingMediumTaskReport2_xox229=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingMediumTaskReport1_xox236=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingMediumTaskReport1_xox239=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '5', # {Operator_wf_performingMediumTaskReport1_xox242=Scenario_Chat.incompleteTasks EQ var(Scenario_Chat).incompleteTasks MINUS 1}.weight = 5
                           '1', # {Operator_wf_performingMediumTaskReport1_xox245=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingShortTaskReport2_xox252=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingShortTaskReport2_xox255=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '5', # {Operator_wf_performingShortTaskReport2_xox258=Scenario_Chat.incompleteTasks EQ var(Scenario_Chat).incompleteTasks MINUS 1}.weight = 5
                           '1', # {Operator_wf_performingShortTaskReport2_xox261=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingShortTaskReport1_xox268=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingShortTaskReport1_xox271=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '5', # {Operator_wf_performingShortTaskReport1_xox274=Scenario_Chat.incompleteTasks EQ var(Scenario_Chat).incompleteTasks MINUS 1}.weight = 5
                           '1', # {Operator_wf_performingShortTaskReport1_xox277=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingLongTask2_xox284=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingLongTask2_xox287=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '1', # {Operator_wf_performingLongTask2_xox290=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingLongTask1_xox297=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1 
                           '2', # {Operator_wf_performingLongTask1_xox300=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '1', # {Operator_wf_performingLongTask1_xox303=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '1', # {Operator_wf_performingShortTask2_xox310=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '2', # {Operator_wf_performingShortTask2_xox313=Tasks.incompleteTasks EQ var(Tasks).incompleteTasks MINUS 1}.weight = 2
                           '1', # {Operator_wf_performingShortTask2_xox316=current.incompleteTasks EQ var(current).incompleteTasks}.weight = 1
                           '0', # {Operator_tf_thinkToCheckMessages_xox10=current.internalTimer EQ var(Scenario_Clock).time PLUS var(current).increment}.weight = 1
                           '1', # {Operator_tf_thinkToCheckMessages_xox13=current.checkMessages EQ true}.weight = 1
                           '0', # {Operator_wf_handleMessages_xox11=current.dontInterrupt EQ true}.weight = 0
                           '0', # {Operator_wf_handleMessages_xox14=current.messageIterator EQ var(current).messageIterator PLUS 1}.weight = 0
                           '0', # {Operator_wf_handleMessages_xox17=current.dontInterrupt EQ false}.weight = 0
                           '0', # {Operator_wf_checkMessages_xox24=current.lastCheckedMessages EQ var(Scenario_Clock).time}.weight = 0
                           '1', # {Operator_wf_checkMessages_xox27=current.checkMessages EQ false}.weight = 1
                           '0', # {Operator_wf_checkMessages_xox30=current.seenFirstMessage EQ true}.weight = 0
                           '0', # {Operator_wf_checkMessages_xox33=current.increment EQ 3}.weight = 0
                           '0', # {Operator_wf_checkMessages_xox36=current.increment EQ 5}.weight = 0
                           '1', # checkMessageWindow.perceptionWeight = 1
                           '0', # checkMessageWindow.weight = 0
                           '3', # readingLong.perceptionWeight = 3
                           '0', # readingLong.weight = 0
                           '2', # readingShortTask.perceptionWeight = 2
                           '0', # readingShortTask.weight = 0
                           '1', # readingShort.perceptionWeight = 1
                           '0', # readingShort.weight = 0
                           '4', # readingLongTask.perceptionWeight = 4
                           '0', # readingLongTask.weight = 0
                           '1', # checkIfOpMessage.perceptionWeight = 1
                           '0', # checkIfOpMessage.weight = 0
                           '5', # reportTaskComplete.perceptionWeight = 5
                           '0', # reportTaskComplete.weight = 0
                           '3', # completeShortTask.perceptionWeight = 3
                           '0', # completeShortTask.weight = 0
                           '4', # completeMediumTask.perceptionWeight = 4 // Disney is 6
                           '0', # completeMediumTask.weight = 0
                           '2', # checkOffTask.perceptionWeight = 20
                           '0', # checkOffTask.weight = 0
                           '5', # completeLongTask.perceptionWeight = 5 // Disney is 7
                           '0', # completeLongTask.weight = 0
                           '2', # checkTaskWindow.perceptionWeight = 2
                           '1'] # checkTaskWindow.weight = 1

		checkMessageWindow = [] # (line: 234)
		readingShortTask = []   # (lines: 114,116,118,120,122,136)
		readingLongTask = []    # (lines: 124,126,128,130,132,134)
		reportTaskComplete = [] # (lines: 160,168,176,184,192,200)
		completeShortTask = []  # (lines: 142,194,202,220)
		completeMediumTask = [] # (lines: 148,154,178,186)
		checkOffTask = []       # (lines: 140,146,152,158,166,174,182,190,198,206,212,218)
		completeLongTask = []   # (lines: 162,170,208,214)
		checkTaskWindow = []    # (lines: 138,144,150,156,164,172,180,188,196,204,210,216)

		f = open(filename, 'r')
		fil = ''
		j = 0
		for i in range(1,307):
		    temp = f.readline()

		    if i > 24 and  i < 242 and temp != '\n' and temp[:2] != '//':	
		    	newtemp = temp
		    	loc = newtemp.find('= ')
		    	temp = newtemp[:loc+2] + weights[j] + '\n'
		    	j += 1

                    if j >= len(weights):
                       break;
               
		    if i < 301 and i > 243 and temp != '\n' and temp[-2:-1] != 'L' and temp[-2:-1] != ')' and temp[-2:-1] != '}' and temp[-2:-1] != 'O':
		    	newtemp = temp
		    	loc = newtemp.find('= ')
		    	temp = newtemp[:loc+2] + weights[j] + '\n'
		    	j += 1

                    if j >= len(weights):
                       break;

		    if i > 242 and i < 300 and temp[-2:-1] == 'O':
		    	newtemp = temp
		    	loc = newtemp.find('AUDIO')
		    	temp = newtemp[:loc] + 'VISUAL' + '\n'

		    if i == 234:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        checkMessageWindow.append(temp[loc1:loc2])

		    elif i == 114 or i == 116 or i == 118 or i == 120 or i == 122 or i == 136:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        readingShortTask.append(temp[loc1:loc2])

		    elif i == 124 or i == 126 or i == 128 or i == 130 or i == 132 or i == 134:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        readingLongTask.append(temp[loc1:loc2])

		    elif i == 160 or i == 168 or i == 176 or i == 184 or i == 192 or i == 200:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=Scen')
		        reportTaskComplete.append(temp[loc1:loc2])
		    
		    elif i == 142 or i == 194 or i == 202 or i == 220:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        completeShortTask.append(temp[loc1:loc2])
		    
		    elif i == 148 or i == 154 or i == 178 or i == 186:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        completeMediumTask.append(temp[loc1:loc2])
		    
		    elif i == 140 or i == 146 or i == 152 or i == 158 or i == 166 or i == 174 or i == 182 or i == 190 or i == 198 or i == 206 or i == 212 or i == 218:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=Tasks')
		        checkOffTask.append(temp[loc1:loc2])
		    
		    elif i == 162 or i == 170 or i == 208 or i == 214:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        completeLongTask.append(temp[loc1:loc2])
		    
		    elif i == 138 or i == 144 or i == 150 or i == 156 or i == 164 or i == 172 or i == 180 or i == 188 or i == 196 or i == 204 or i == 210 or i == 216:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        checkTaskWindow.append(temp[loc1:loc2])

		    elif i == 246:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(checkMessageWindow)):
		        	if i < len(checkMessageWindow) - 1:
			            fil += checkMessageWindow[i] + ','
			        else:
			        	fil += checkMessageWindow[i]
		        fil += '}\n'

		    elif i == 256:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(readingShortTask)):
		        	if i < len(readingShortTask) - 1:
		        		fil += readingShortTask[i] + ','
		        	else:
						fil += readingShortTask[i]		        
		        fil += '}\n'

		    elif i == 266:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(readingLongTask)):
		        	if i < len(readingLongTask) - 1:
		        		fil += readingLongTask[i] + ','
		        	else:
		        		fil += readingLongTask[i]
		        fil += '}\n'

		    elif i == 276:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(reportTaskComplete)):
		        	if i < len(reportTaskComplete) - 1:
						fil += reportTaskComplete[i] + ','
		        	else:
		        		fil += reportTaskComplete[i]
		        fil += '}\n'

		    elif i == 281:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(completeShortTask)):
		        	if i < len(completeShortTask) - 1:
						fil += completeShortTask[i] + ','
		        	else:
		        		fil += completeShortTask[i]
		        fil += '}\n'

		    elif i == 286:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(completeMediumTask)):
		        	if i < len(completeMediumTask) - 1:
						fil += completeMediumTask[i] + ','
		        	else:
		        		fil += completeMediumTask[i]
		        fil += '}\n'

		    elif i == 291:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(checkOffTask)):
		        	if i < len(checkOffTask) - 1:
						fil += checkOffTask[i] + ','
		        	else:
		        		fil += checkOffTask[i]
		        fil += '}\n'

		    elif i == 296:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(completeLongTask)):
		        	if i < len(completeLongTask) - 1:
						fil += completeLongTask[i] + ','
		        	else:
		        		fil += completeLongTask[i]
		        fil += '}\n'

		    elif i == 301:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(checkTaskWindow)):
		        	if i < len(checkTaskWindow) - 1:
						fil += checkTaskWindow[i] + ','
		        	else:
		        		fil += checkTaskWindow[i]
		        fil += '}\n'

		    else:
		        fil += temp

		f.close()
		#os.remove(filename)

		f = open(filename, 'w+')
		f.write(fil)
		f.close()

if __name__ == '__main__':
	c = ConfigUpdater()
