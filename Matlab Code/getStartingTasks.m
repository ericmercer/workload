function [ start_nodes ] = getStartingTasks( tasksS, tasksE, x )
    to_start = x-1;
    before_end = x-2;
    ending = size(tasksS);
    ending = ending(1)-1;
    nodesS = {'PS','MM','OP'};
    nodesE = {'PS','MM','OP'};
    
    
    for index = 1:to_start
        taskS = char(tasksS(x-index,:));
        if(length(taskS) > 1)
            taskS = strrep(taskS,'__','][');
            [start,endIndex] = regexp(taskS,'\[\w\w_START_\w+]')
            num = size(start);
            num = num(2);
            if num > 0
                for entry = 1:num
                    actor = taskS(start(entry)+1:start(entry)+2);
                    actor_task = taskS(start(entry)+10:endIndex(entry)-4);
                    nodesS(end+1,1) = {''};
                    nodesS(end,2) = {''};
                    nodesS(end,3) = {''};
                    size(actor);
                    if actor == 'PS'
                        nodesS(end,1) = {actor_task};
                    elseif actor == 'MM'
                        nodesS(end,2) = {actor_task};
                    elseif actor == 'OP'
                        nodesS(end,3) = {actor_task};
                    end
                end
            end
        end
    end
    for index = 1:before_end
        taskE = char(tasksE(x-index,:));
        if(length(taskE > 1))
            taskE = strrep(taskE,'__','][');
            [start,endIndex] = regexp(taskE,'\[\w\w_STOP_\w+\]');
            num = size(start);
            num = num(2);
            if num > 0
                for entry = 1:num
                    actor = taskE(start(entry)+1:start(entry)+2);
                    actor_task = taskE(start(entry)+9:endIndex(entry)-4);
                    nodesE(end+1,1) = {''};
                    nodesE(end,2) = {''};
                    nodesE(end,3) = {''};
                    size(actor);
                    if actor == 'PS'
                        nodesE(end,1) = {actor_task};
                    elseif actor == 'MM'
                        nodesE(end,2) = {actor_task};
                    elseif actor == 'OP'
                        nodesE(end,3) = {actor_task};
                    end
                end
            end
        end
    end
    
    num = size(nodesE);
    for row = 2:num(1)
        for col = 1:num(2)
            taskE = char(nodesE(row,col));
            if length(taskE) > 1
                entries = size(nodesS);
                entries = entries(1);
                for entry = 1:entries
                    start_task = char(nodesS(entry,col));
                    if length(start_task) == length(taskE)
                        if start_task == taskE
                            nodesS(entry,col) = {''};
                            taskE = '  ';
                        end
                    end
                end
            end
        end
    end
    start_nodes = nodesS;
end

