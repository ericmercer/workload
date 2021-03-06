import matplotlib.pyplot as plt
import numpy as np
import os
import sys

def main(argv):
    loc = os.getcwd()
    loc=os.path.join(loc,"src","csvFiles")
    for arg in argv:
        v,w,x,y,z = np.genfromtxt(loc+arg,delimiter=',',skip_header=1,unpack = True,usecols = (0,2,4,6,9))
        #readFile = open('C:\\Users\\researcher\\workspace\\Test\\src\\csvFiles\\HCRW\\MissionManager.csv')
        #csv.reader(readFile)
        plt.figure()
        location2 = arg.find('.')
        plt.suptitle(arg[6:location2]+" "+arg[1:5]+" Workload")
        plt.subplot(4,1,1)
        plt.title("Perception")
        plt.plot(v,w)
        plt.grid(b=True, which='major', color='k', linestyle='--')
        plt.subplot(4,1,2)
        plt.title("Cognition")
        plt.plot(v,x+y)
        plt.grid(b=True, which='major', color='k', linestyle='--')
        plt.subplot(4,1,3)
        plt.title("Temporal")
        plt.plot(v,z)
        plt.grid(b=True, which='major', color='k', linestyle='--')
        plt.subplot(4,1,4)
        plt.title("Combined")
        plt.plot(v,w+x+y+z)
        plt.grid(b=True, which='major', color='k', linestyle='--')
        plt.tight_layout()
        plt.subplots_adjust(top=0.9)
	        
    plt.show()
if __name__ =="__main__":
    main(sys.argv[1:])




