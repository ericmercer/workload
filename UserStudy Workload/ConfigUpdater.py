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

		checkMessageWindow = [] # (line: 237)
		readingShortTask = []   # (lines: 117,119,121,123,125,139)
		readingLongTask = []    # (lines: 127,129,131,133,135,137)
		reportTaskComplete = [] # (lines: 163,171,179,187,195,203)
		completeShortTask = []  # (lines: 145,197,205,223)
		completeMediumTask = [] # (lines: 151,157,181,189)
		checkOffTask = []       # (lines: 143,149,155,161,169,177,185,193,201,209,215,221)
		completeLongTask = []   # (lines: 165,173,211,217)
		checkTaskWindow = []    # (lines: 141,147,153,159,167,175,183,191,199,207,213,219)

		f = open(filename, 'r')
		fil = ''
		j = 0
		for i in range(1,309):
		    temp = f.readline()

		    if i > 27 and  i < 245 and temp != '\n' and temp[:2] != '//':	# this replaces the original weight values
		    	newtemp = temp
		    	loc = newtemp.find('= ')
		    	temp = newtemp[:loc+2] + weights[j] + '\n'
		    	j += 1

                    if j >= len(weights):
                       break;
               
		    if i < 304 and i > 246 and temp != '\n' and temp[-2:-1] != 'L' and temp[-2:-1] != ')' and temp[-2:-1] != '}' and temp[-2:-1] != 'O':
		    	newtemp = temp
		    	loc = newtemp.find('= ')
		    	temp = newtemp[:loc+2] + weights[j] + '\n'
		    	j += 1

                    if j >= len(weights):
                       break;

		    if i > 245 and i < 303 and temp[-2:-1] == 'O':
		    	newtemp = temp
		    	loc = newtemp.find('AUDIO')
		    	temp = newtemp[:loc] + 'VISUAL' + '\n'

		    if i == 237:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        checkMessageWindow.append(temp[loc1:loc2])

		    elif i == 117 or i == 119 or i == 121 or i == 123 or i == 125 or i == 139:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        readingShortTask.append(temp[loc1:loc2])

		    elif i == 127 or i == 129 or i == 131 or i == 133 or i == 135 or i == 137:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        readingLongTask.append(temp[loc1:loc2])

		    elif i == 163 or i == 171 or i == 179 or i == 187 or i == 195 or i == 203:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=Scen')
		        reportTaskComplete.append(temp[loc1:loc2])
		    
		    elif i == 145 or i == 197 or i == 205 or i == 223:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        completeShortTask.append(temp[loc1:loc2])
		    
		    elif i == 151 or i == 157 or i == 181 or i == 189:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        completeMediumTask.append(temp[loc1:loc2])
		    
		    elif i == 143 or i == 149 or i == 155 or i == 161 or i == 169 or i == 177 or i == 185 or i == 193 or i == 201 or i == 209 or i == 215 or i == 221:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=Tasks')
		        checkOffTask.append(temp[loc1:loc2])
		    
		    elif i == 165 or i == 173 or i == 211 or i == 217:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        completeLongTask.append(temp[loc1:loc2])
		    
		    elif i == 141 or i == 147 or i == 153 or i == 159 or i == 167 or i == 175 or i == 183 or i == 191 or i == 199 or i == 207 or i == 213 or i == 219:
		        fil += temp
		        loc1 = temp.find('xox')
		        loc2 = temp.find('=cur')
		        checkTaskWindow.append(temp[loc1:loc2])

		    elif i == 249:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(checkMessageWindow)):
		        	if i < len(checkMessageWindow) - 1:
			            fil += checkMessageWindow[i] + ','
			        else:
			        	fil += checkMessageWindow[i]
		        fil += '}\n'

		    elif i == 259:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(readingShortTask)):
		        	if i < len(readingShortTask) - 1:
		        		fil += readingShortTask[i] + ','
		        	else:
						fil += readingShortTask[i]		        
		        fil += '}\n'

		    elif i == 269:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(readingLongTask)):
		        	if i < len(readingLongTask) - 1:
		        		fil += readingLongTask[i] + ','
		        	else:
		        		fil += readingLongTask[i]
		        fil += '}\n'

		    elif i == 279:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(reportTaskComplete)):
		        	if i < len(reportTaskComplete) - 1:
						fil += reportTaskComplete[i] + ','
		        	else:
		        		fil += reportTaskComplete[i]
		        fil += '}\n'

		    elif i == 284:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(completeShortTask)):
		        	if i < len(completeShortTask) - 1:
						fil += completeShortTask[i] + ','
		        	else:
		        		fil += completeShortTask[i]
		        fil += '}\n'

		    elif i == 289:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(completeMediumTask)):
		        	if i < len(completeMediumTask) - 1:
						fil += completeMediumTask[i] + ','
		        	else:
		        		fil += completeMediumTask[i]
		        fil += '}\n'

		    elif i == 294:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(checkOffTask)):
		        	if i < len(checkOffTask) - 1:
						fil += checkOffTask[i] + ','
		        	else:
		        		fil += checkOffTask[i]
		        fil += '}\n'

		    elif i == 299:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(completeLongTask)):
		        	if i < len(completeLongTask) - 1:
						fil += completeLongTask[i] + ','
		        	else:
		        		fil += completeLongTask[i]
		        fil += '}\n'

		    elif i == 304:
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
