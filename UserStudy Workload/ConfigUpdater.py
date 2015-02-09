import os
import sys
import time

class ConfigUpdater():

	def __init__(self):
		filename = os.getcwd() + '/brahms-translate/examples/UserStudy/config.cfg'

		weights = ['1','1','1','2','1','2','1','2','1','2','1','2','1','4','1','4','1','4','1','4','1','4','1','4','1','2','1','3','1','1','1','3','1','4','1','4','1','6','1','6','1','5','1','5','1','4','1','4','1','5','1','5','1','3','1','3','1','1','3','3','2','2','2','4','5','4','4','5','5','3','1','2','1','1','2','1','1','2','1','1','2','5','1','1','2','5','1','1','2','5','1','1','2','5','1','1','2','5','1','1','2','5','1','1','2','1','1','2','1','1','2','1','0','1','0','0','0','0','1','0','0','0','1','0','3','0','2','0','1','0','4','0','1','0','5','0','3','0','4','0','2','0','5','0','2','0']

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
		    
		    if i < 301 and i > 243 and temp != '\n' and temp[-2:-1] != 'L' and temp[-2:-1] != ')' and temp[-2:-1] != '}' and temp[-2:-1] != 'O':
		    	newtemp = temp
		    	loc = newtemp.find('= ')
		    	temp = newtemp[:loc+2] + weights[j] + '\n'
		    	j += 1

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

		    elif i == 279:
		    	loc = temp.find('\n')
		    	fil += temp[:loc] + ' // Disney is 5\n'


		    elif i == 281:
		        loc = temp.find('{')
		        fil += temp[:loc+1]
		        for i in range(0,len(completeShortTask)):
		        	if i < len(completeShortTask) - 1:
						fil += completeShortTask[i] + ','
		        	else:
		        		fil += completeShortTask[i]
		        fil += '}\n'

		    elif i == 284:
		    	loc = temp.find('\n')
		    	fil += temp[:loc] + ' // Disney is 6\n'

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

		    elif i == 294:
		    	loc = temp.find('\n')
		    	fil += temp[:loc] + ' // Disney is 7\n'

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
