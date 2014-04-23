function [ end_nodes ] = getEndingTasks( tasksS, tasksE, x )

    ending = size(tasksS);
    ending = ending(1)-1;
    nodesS = {'PS','MM','OP'};
    nodesE = {'PS','MM','OP'};
    
    
    for index = x:ending
        taskS = char(tasksS(index,:));
        if(length(taskS) > 1)
            taskS = strrep(taskS,'__','][');
            [start,endIndex] = regexp(taskS,'\[\w\w_START_\w+\]');
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
        
        taskE = char(tasksE(index,:));
        if(length(taskE) > 1)
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
    num = size(nodesS);
    for row = 2:num(1)
        for col = 1:num(2)
            taskS = char(nodesS(row,col));
            if length(taskS) > 1
                entries = size(nodesE);
                entries = entries(1);
                for entry = 1:entries
                    end_task = char(nodesE(entry,col));
                    if length(end_task) == length(taskS)
                        nodesE(entry,col) = {''};
                    end
                end
            end
        end
    end
    end_nodes = nodesE;
end

