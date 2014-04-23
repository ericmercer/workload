
file = fopen('HCDW.csv');
Data = textscan(file,'%d%s%d%s%d%s%d%s%s','Delimiter',',','HeaderLines',1);
fclose(file);
A = Data(1);
B = Data(8);
C = Data(9);
time = A{1};
tasksS = B{1};
tasksE = C{1};
taskList = {'tasks'};
len = size(tasksS);
for index = 1:len
    taskList(end+1,1) = {''};
    taskList(end,1) = tasksS(index,1);
end
len = size(tasksE);
for index = 1:len
    taskList(index,1) = strcat(taskList(index,1),tasksE(index,1));
end
taskList
x = 50;
            entry = 1;
            for i=1:size(time)
                if(time(i) < x)
                    entry = entry + 1;
                end
            end
            x = entry;
            
%tasks = getEncapsulatingTasks(tasksS,tasksE,x);
taskList = getEncapsulatingTasks2(taskList,x)