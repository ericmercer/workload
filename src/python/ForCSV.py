#written by Michael Sharp.  Last revision 6/3/2014

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
        