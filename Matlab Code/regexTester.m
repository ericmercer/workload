
OP = 'Operator';
PS = 'ParentSearch';
MM = 'MissionManager';
% string = '(MissionManager TX_PS [AUDIO_PS_MM_COMM])(MissionManager TX_PS [AUDIO_PS_MM_COMM])(MissionManager TX_PS [AUDIO_PS_MM_COMM])(MissionManager TX_PS [AUDIO_PS_MM_COMM])(MissionManager TX_PS [AUDIO_PS_MM_COMM])'
% 
% [start,endIndex] = regexp(string,'\(\w+\s\w+\s\[\w+\]\)')          %'( \w+\s+\d )')
% 
% num_entries = size(start);
% if num_entries > 0
%     subset = string(start(1):endIndex(1))
%     endOfName = strfind(subset,' ')
%     actor = subset(2:endOfName(1)-1)
% end


% string = '(UAVBattery LOW [null])(UAVFlightPlan YES_PATH [null])(Operator OBSERVE_UAV [null])'
% [start, endIndex] = regexp(string, '\(\w+\s\w+\s\[\w+\]\)')
% num_entries = size(start);
% if num_entries > 0
%     for j = 1:num_entries(2)
%         subset = string(start(j):endIndex(j));
%         endOfName = strfind(subset,' ');
%         actor = subset(2:endOfName(1)-1);
%         k = -1;
%         if size(PS) == size(actor)
%             if PS == actor
%                 k = 1;
%             end
%        elseif size(OP) == size(actor)
%             if OP == actor
%                 k = 2;
%             end
%         elseif size(MM) == size(actor)
%             if MM == actor
%                 k = 3;
%             end
%         end
%         if k ~= -1
%             array_dim = size(out_data);
%             if array_dim(1) == i
%                 out_data(i,k) = out_data(i,k) + 1;
%             end
%         end
%     end
% end

% string = '(ParentSearch IDLE [1])'
% [start, endIndex] = regexp(string, '\(\w+\s\w+\s\[\d+\]\)')
% num_entries = size(start);
% if num_entries > 0
%     for j = 1:num_entries(2)
%         subset = string(start(j):endIndex(j));
%         endOfName = strfind(subset,' ');
%         actor = subset(2:endOfName(1)-1);
%         [s_index, e_index] = regexp(subset,'\[\d+\]')
%         val = str2num(subset(s_index+1:e_index-1))
%         if size(PS) == size(actor)
%             if PS == actor
%                 k = 1;
%             end
%        elseif size(OP) == size(actor)
%             if OP == actor
%                 k = 2;
%             end
%         elseif size(MM) == size(actor)
%             if MM == actor
%                 k = 3;
%             end
%         end
%         if k ~= -1
%             array_dim = size(out_data);
%             if array_dim(1) == i
%                 out_data(i,k) = out_data(i,k) + val;
%             end
%         end
%     end
% end

% string = '([PS_START_LISTEN_TO_MM_PS])([PS_START_LISTEN_TO_MM_PS])([PS_START_LISTEN_TO_MM_PS])'
% [start, endIndex] = regexp(string,'\(\[\w+\]\)')
% 
% tasks = sprintf('%s\n','apple','mm','op')
% msgbox(tasks)





file = fopen('HCDW.csv');

Data = textscan(file,'%d%s%d%s%d%s%d%s%s','Delimiter',',','HeaderLines',1);
fclose(file);
A = Data(8);
B = Data(9);
taskS = A{1};
taskE = B{1};

x = 20;

entry = 1;
for i=1:size(time)
    if(time(i) < x)
        entry = entry + 1;
    end
end
x = entry;

to_end = size(time);
to_end = to_end(1)-x;
start_nodes = getStartingTasks(taskS,taskE,x)
end_nodes = getEndingTasks(taskS,taskE,x)