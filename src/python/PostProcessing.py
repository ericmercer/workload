#written by Michael Sharp.  Last revision 6/3/2014
#Post processes the .csv files and expands the data, also creates the swimlanes.
import csv
import copy
import os
import sys
import getopt
from Swimlane import swimlane
from ForCSV import for_csv
#main program
def main(argv):
    #data for swimlane file 
    actor_lane_id = {}
    swim = swimlane()
    
    #for swimline data
    items = []
    lane_info = []
    max_time = 0
    previousChannel = ""
    startTime = ""
    active_output = ""
    OP_TEMP_WINDOW = 9
    actors = []
    #if op temp parameter is not passed, defaults to 9
    try:
      opts, args = getopt.getopt(argv,"w:a:")
    except getopt.GetoptError:
      sys.exit(2)
    for opt, arg in opts:
        if opt == '-w':
            OP_TEMP_WINDOW = int(arg)
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
            #if its not a csv file it skips it
            if ".csv" not in file:
                continue
            #if total is in the name it skips it, this is to prevent doubling up on the totals file if its not deleted.
            if "Total" in file:
                os.remove(os.path.join(root,file))
                continue

            lane_info.append("{ id: '"+str(file[:-4])+" IDLE', lane: "+str(actor_lane_id.get(file[:-4]))+", start: yr(0), class: 'item', end: yr(")
            with open(os.path.join(root,file),'rb') as csvfile:
                reader = csv.reader(csvfile, delimiter = ',')
                expand = {}
                first = reader.next()
                temp_place = 0;
                temp_for_csv = []
                for line in reader:
                    if line[5] != "":
                        lane_info[len(lane_info)-1]+=(str(line[0])+")},")
                        first_loc = line[5].index(' ')
                        second_loc = line[5].index(' ',first_loc+1)
                        lane_info.append("\n\n{ id: '"+str(file[:-4])+" "+line[5][first_loc:second_loc] +"', lane: "+str(actor_lane_id.get(file[:-4]))+", start: yr("+line[0]+"), class: 'item', end: yr(")
                    
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
                for x in sorted(expand):
                     #fixes active outputs
                    if expand[x].getData("Active Output") != "":    
                        active_output = expand[x].getData("Active Output")
                    if expand[x].getData("Transition Durations (Actor State [TransitionDuration])*") == "":
                        expand[x].setData("Active Output",active_output)
                    else: active_output = ""
                    
                    #active output data
                    if expand[x].getData("Active Output") !="" and previousChannel == "":
                        previousChannel = expand[x].getData("Active Output")
                        startTime = x
                    elif expand[x].getData("Active Output") == "" and previousChannel!="":
                        tempLane = 0
                        if previousChannel[2:3] == "A":
                            tempLane =1
                        elif previousChannel[2:3] == "V":
                            tempLane = 2
                        else:
                            tempLane = 3
                        items.append("{ id: '"+previousChannel[4:-2] +"', lane: "+str(int(actor_lane_id.get(file[:-4])) + tempLane)+", start: yr("+str(startTime)+"), class: 'item', end: yr("+str(x)+")}")
                        previousChannel = ""
                    elif expand[x].getData("Active Output") != previousChannel:
                        tempLane = 0
                        if previousChannel[2:3] == "A":
                            tempLane =1
                        elif previousChannel[2:3] == "V":
                            tempLane = 2
                        else:
                            tempLane = 3
                        items.append("{ id: '"+previousChannel[4:-2] +"', lane: "+str(int(actor_lane_id.get(file[:-4])) + tempLane)+", start: yr("+str(startTime)+"), class: 'item', end: yr("+str(x)+")}")
                        previousChannel = expand[x].getData("Active Output")
                        startTime = x
                        print(startTime)

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
                    if x > max_time:
                        max_time = x
                   
                        
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
            #finishes last channel out
            if previousChannel!="":
                tempLane = 0
                if expand[x].getData("Active Output")[2:3] == "A":
                    tempLane =1
                elif expand[x].getData("Active Output")[2:3] == "V":
                    tempLane = 2
                else:
                    tempLane = 3
            #items.append("{ id: '"+previousChannel[4:-2] +"', lane: "+str(int(actor_lane_id.get(file[:-4])) + tempLane)+", start: yr("+str(startTime)+"), class: 'item', end: yr(")
        #prints out the totals file.  
        firstt = True 
        if len(total) >0:
            print(os.path.join(old_root,"Total.csv"))
           #fixes the endings of the swimlane info
            for x in lane_info:
                if x[len(x)-1] != ',':
                    x+=str(max_time+1)+")},"
               
                swim.addInfo(x)
            
            for x in items:

                if x[len(x)-1] != '}':
                    x+=str(max_time)+")}"
                if firstt:
                    swim.addInfo(x)
                    firstt = False
                else:
                    swim.addChannelInfo(x)
            del lane_info[:]
            del items[:]    
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