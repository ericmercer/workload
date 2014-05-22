#written by Michael Sharp.  Last revision 5/15/2014
import csv
import copy
import os
import sys
import getopt

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
    for x in actors:
        print(x)
    #finds current directory
    loc = os.getcwd()
    loc=os.path.join(loc,"src","csvFiles")
    first = []
    old_root = ""
    #itterates through all the files in the directory
    for root, dirs, files in os.walk(loc):
        total = {}
        for file in files:
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
            with open(os.path.join(root,file),'rb') as csvfile:
                reader = csv.reader(csvfile, delimiter = ',')
                expand = {}
                first = reader.next()
                temp_place = 0;
                temp_for_csv = []
                for line in reader:
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
            with open(os.path.join(old_root,"Total.csv"),'wb') as outcsv:
                out = csv.writer(outcsv) 
                out.writerow(first)
                for x in sorted(total):
                    temp = [x]
                    temp.extend(total[x].getAllData())
                    out.writerow(temp)
if __name__ =="__main__":
    main(sys.argv[1:])