import os
import sys
import time
import csv
import matplotlib.pyplot as plt
import numpy as np
import matplotlib.gridspec as gridspec
from matplotlib.ticker import MultipleLocator, FormatStrFormatter

class WorkloadGrapher():

	def __init__(self):
		filename = os.getcwd() + '/brahms-translate/examples/UserStudy/OperatorWorkload.txt'

		inputText = csv.reader(open(filename, 'r'), delimiter = ',')
		outputText = csv.writer(open(os.getcwd() + '/workload.csv', 'w+'))

		inputText.next()
		outputText.writerows(inputText)

class Grapher():

    def __init__(self):
        col = np.genfromtxt(os.getcwd() + '/workload.csv',delimiter=',',skip_header=1,usecols = (0,1,2,3,4,5),unpack = True)

        colNames = 'Time', 'Physical Workload', 'Temporal Memory Workload', 'Perception Workload', 'Visual Workload', 'Decision Workload', 'Combined Workload'

        directory = os.getcwd() + '/workloadPlots'

        if not os.path.exists(directory):
            os.makedirs(directory)

        for c in range(1,7):

            plotFile = directory + '/' + colNames[c]

            majorLocator   = MultipleLocator(100)
            majorFormatter = FormatStrFormatter('%d')
            minorLocator   = MultipleLocator(10)

            fig = plt.figure(c,figsize=(35,7))

            ax = plt.subplot(111)
            plt.subplots_adjust(bottom=0.04,left=0.02,right=0.99,top=0.96)
            
            ax.xaxis.set_major_locator(majorLocator)
            ax.xaxis.set_major_formatter(majorFormatter)
            ax.xaxis.set_minor_locator(minorLocator)

            plt.title(colNames[c])
            plt.grid(b=True, which='major', color='k', linestyle='--')

            if c < 6:    
                plt.plot(col[0],col[c],'b')
            else:
                plt.plot(col[0],col[1]+col[2]+col[3]+col[4]+col[5],'b')

            plt.savefig(plotFile)
            
        plt.show()


if __name__ == '__main__':
	d = WorkloadGrapher()
time.sleep(5)
g = Grapher()





