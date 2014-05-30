#written by Michael Sharp.  Last revision 5/29/2014
#Post processes the .csv files and expands the data, also creates the swimlanes.
import csv
import copy
import os
import sys
import getopt
from Swimlane import swimlane

#complex data structure for holding the row data
class for_csv:
    #needs the data and the column names to create it
    def __init__(self,info,column_names):
        self.info = info
        self.column_names = column_names
    def __str__(self):
        string =""
        for x in self.info:
            string+=str(x)
            string+=','
        string = string[:-1]
        return string
    #for adding two csv lines together
    def add(self, other_csv):
        temp = other_csv.getAllData()
        for x in self.column_names:
            count = self.column_names.index(x)
            try:
                self.info[count]=int(int(self.info[count]) +int(temp[count]))
            except:
                self.info[count]+=str(temp[count])
        return self
    #returns the list that holds that data   
    def getAllData(self):
        return self.info
    #lets you set the data, pass in the name of the column you want to change and what you want it to be   
    def setData(self,column_name,change_to_what):
        count = self.column_names.index(column_name)
        try:
            self.info[count] = int(change_to_what)
        except:
            self.info[count] =str(change_to_what)
        return
    #lets you change the data, pass in the name of the column and how much you want it to change by    
    def modData(self,column_name,how_much):
        count = self.column_names.index(column_name)
        try:
            self.info[count] += int(how_much)
        except:
            self.info[count] +=str(how_much)
        return
    #returns just one peice of the data based on the column name you pass in    
    def getData(self,column_name):
        return self.info[self.column_names.index(column_name)]
        
#main program
def main(argv):
    #data for swimlane file 
    actor_lane_id = {}
    swim = swimlane()
    
    #for swimline data
    first_time = True
    previousChannel = ""
    startTime = ""
    
    OP_TEMP_WINDOW = 9
    actors = []
    #if op temp parameter is not passed, defaults to 9
    try:
      opts, args = getopt.getopt(argv,"w:a:")
    except getopt.GetoptError:
      sys.exit(2)
    for opt, arg in opts:
        print (opt + "  " + arg)
        if opt == '-w':
            OP_TEMP_WINDOW = int(arg)
            print("op = "+ str(OP_TEMP_WINDOW))
        elif opt in ("-a"):
            actors.append( str(arg+".csv"))
            #for swimlanes, adds the actor names as lanes
            if int(swim.getNumLane()) == 0:
                actor_lane_id[arg] = str(swim.getNumLane())
                swim.addLane("  {id: "+str(swim.getNumLane())+", label: '"+arg+"'}")
            else:
                actor_lane_id[arg] = str(swim.getNumLane())
                swim.addLane(",\n\n  {id: "+str(swim.getNumLane())+", label: '"+arg+"'}")
            #adds the different com channels to the lanes
            swim.addLane(",\n\n  {id: "+str(swim.getNumLane())+", label: 'Audio'}" + ",\n\n  {id: "+str(swim.getNumLane()+1)+", label: 'Visual'}" + ",\n\n  {id: "+str(swim.getNumLane() + 2)+", label: 'Data'}")
            swim.addNumLanes(2)
    swim.addLane("\n\n];")
    #finds current directory
    loc = os.getcwd()
    loc=os.path.join(loc,"src","csvFiles")
    first = []
    old_root = ""
    #itterates through all the files in the directory
    for root, dirs, files in os.walk(loc):
        total = {}
        for file in files:
            #deletes files not passed in as parameters
            if file not in actors and ".csv" in file:
                os.remove(os.path.join(root,file))
                continue
            old_root = root
            #prints the file it is working on
            print (os.path.join(root,file))   
            #if its not a csv file it skips it
            if ".csv" not in file:
                continue
            #if total is in the name it skips it, this is to prevent doubling up on the totals file if its not deleted.
            if "Total" in file:
                os.remove(os.path.join(root,file))
                continue
                #opens the csv file to work on it
            if first_time == False:
                swim.addInfo(",\n\n")
            first_time = False
            swim.addInfo("{ id: '"+str(file[:-4])+" IDLE', lane: "+str(actor_lane_id.get(file[:-4]))+", start: yr(0), class: 'item', end: yr(")
            with open(os.path.join(root,file),'rb') as csvfile:
                reader = csv.reader(csvfile, delimiter = ',')
                expand = {}
                first = reader.next()
                temp_place = 0;
                temp_for_csv = []
                for line in reader:
                    if line[5] != "":
                        swim.addInfo(str(line[0])+")},")
                        first_loc = line[5].index(' ')
                        second_loc = line[5].index(' ',first_loc+1)
                        swim.addInfo("\n\n{ id: '"+str(file[:-4])+" "+line[5][first_loc:second_loc] +"', lane: "+str(actor_lane_id.get(file[:-4]))+", start: yr("+line[0]+"), class: 'item', end: yr(")
                    if line[10] !="" and previousChannel == "":
                        previousChannel = line[10]
                        startTime = line[0]
                    elif line[10] == "" and previousChannel!="":
                        tempLane = 0
                        if line[10][0:0] == "A":
                            tempLane =1
                        elif line[10][0:0] == "V":
                            tempLane = 2
                        else:
                            tempLane = 3
                        swim.addChannelInfo("{ id: '"+previousChannel[4:-2] +"', lane: "+str(int(actor_lane_id.get(file[:-4])) + tempLane)+", start: yr("+startTime+"), class: 'item', end: yr("+line[0]+")}")
                        previousChannel = ""
                    elif line[10] != previousChannel:
                        tempLane = 0
                        if line[10][0:0] == "A":
                            tempLane =1
                        elif line[10][0:0] == "V":
                            tempLane = 2
                        else:
                            tempLane = 3
                        swim.addChannelInfo("{ id: '"+previousChannel[4:-2] +"', lane: "+str(int(actor_lane_id.get(file[:-4])) + tempLane)+", start: yr("+startTime+"), class: 'item', end: yr("+line[0]+")}")
                        previousChannel = line[10]
                        startTime = line[0]
                    expand[int(line[0])] = for_csv(line[1:],first[1:])
                    if (int(line[0]) - temp_place) >1:
                        #this loop expands the data, and sets to 0 what should be set to zero
                        for x in range(temp_place+1,int(line[0])):
                            expand[x] = copy.deepcopy(for_csv(temp_for_csv,first[1:]))
                            expand[x].setData('Op Tempo',0)
                            expand[x].setData("Total Transitions",0)
                            expand[x].setData("Transition Durations (Actor State [TransitionDuration])*","")
                            expand[x].setData("Enabled Transitions (Actor State [NumberOfEnabledTransitions])*","")
                            expand[x].setData("Total Enabled Transitions",0)
                            
                    temp_for_csv = line[1:]
                    temp_place = int(line[0])       
                op_list = []
                swim.addInfo(str(int(line[0])+1)+')}')
                if previousChannel!="":
                    tempLane = 0
                    if line[10][0:0] == "A":
                        tempLane =1
                    elif line[10][0:0] == "V":
                        tempLane = 2
                    else:
                        tempLane = 3
                    swim.addChannelInfo("{ id: '"+previousChannel[4:-2] +"', lane: "+str(int(actor_lane_id.get(file[:-4])) + tempLane)+", start: yr("+startTime+"), class: 'item', end: yr("+str(int(line[0])+1)+")}")
                for x in sorted(expand):
                    #this fixes the total transitions so they dont spike, but so they put one ball in the bucket for each time unit
                    if expand[x].getData("Total Transitions") > 0:
                        for y in range (x-int(expand[x].getData("Total Transitions")),x):
                            expand[y].modData("Total Transitions",1)
                        expand[x].setData("Total Transitions",0)
                        #fixes op tempo so it is correct
                    if int(expand[x].getData("Op Tempo")) > 0:
                        op_list.append(x)
                    for y in op_list:
                        if (x-y) > OP_TEMP_WINDOW:
                            op_list.remove(y)
                    expand[x].setData("Op Tempo",len(op_list))
                #prints out the file as a new csv in the same location as the original.  It overwrites the original
                with open(os.path.join(root,file),'wb') as outcsv:
                    out = csv.writer(outcsv) 
                    out.writerow(first)
                    for x in sorted(expand):
                        temp = [x]
                        temp.extend(expand[x].getAllData())
                        out.writerow(temp)   
                        if x in total:
                            total[x]=total[x].add(expand[x])
                        else:
                            total[x] = copy.deepcopy(expand[x])    
        #prints out the totals file.   
        if len(total) >0:  
            swim.finishInfo()
            swimWriter =  open(os.path.join(old_root,"Swimlane.html"),'w')
            swimWriter.write(swim.printFile())
            swimWriter.close()
            swim.resetItems()
            with open(os.path.join(old_root,"Total.csv"),'wb') as outcsv:
                out = csv.writer(outcsv) 
                out.writerow(first)
                for x in sorted(total):
                    temp = [x]
                    temp.extend(total[x].getAllData())
                    out.writerow(temp)
if __name__ =="__main__":
    main(sys.argv[1:])