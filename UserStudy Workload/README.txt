1. Open brahms-translate/build.txt
2. change source filepath to .../UserStudy\ Workload/.../UserStudy.bmd
3. change destination to (file path of brahms-translate)/examples/
4. Run brahms-translate/parse/(default package)/GetFiles.java
5. Will create folder brahms-translate/examples/UserStudy
6. Swap /brahms-translate/examples/UserStudy/config.cfg w/ the config.cfg in this folder
7. Run brahms-translate/examples/UserStudy/UserStudyMain.java
8. Once, fully executed, it will create /brahms-translate/examples/UserStudy/OperatorWorkload.txt
9. Update file path of the OperatorWorkload.txt in WorkloadGrapher.py
10. Run WorkloadGrapher.py

Running WorkloadGrapher.py will create a csv file of the operatorWorkload and the use that to create a folder where it saves and opens plots of the different workload
