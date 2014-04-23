function [ tasks ] = getEncapsulatingTasks2( taskList, x )
    ending_point = size(taskList);
    ending_point = x;
    tasks = {'PS','MM','OP','VO'};
    taskStarts = {'PS','MM','OP','VO'};
    for index = 1:ending_point
        taskCell = char(taskList(index,:));
        if(length(taskCell) > 1)
            taskCell = strrep(taskCell,'__','][');
            [start,endIndex] = regexp(taskCell,'\[\w\w_(START|STOP)_\w+]');
            num = size(start);
            num = num(2);
            if num > 0
                for entry = 1:num
                    task = taskCell(start(entry):endIndex(entry));
                    actor = taskCell(start(entry)+1:start(entry)+2);
                    if strfind(task,'START')==5
                        actor_task = taskCell(start(entry)+10:endIndex(entry)-4);
                    else
                        actor_task = taskCell(start(entry)+9:endIndex(entry)-4);
                        if index == ending_point
                            continue
                        end
                    end
                    tasks(end+1,1) = {''};
                    tasks(end,2) = {''};
                    tasks(end,3) = {''};
                    taskStarts(end+1,1) = {''};
                    taskStarts(end,2) = {''};
                    taskStarts(end,3) = {''};
                    taskStarts(end,4) = {''};
                    if actor == 'PS'
                        tasks(end,1) = {task};
                        if strfind(task,'START') == 5
                            taskStarts(end,1) = {actor_task};
                        elseif strfind(task,'STOP')==5
                            taskStarts = removeTaskFromQueue(taskStarts,1,actor_task);
                        end
                    elseif actor == 'MM'
                        tasks(end,2) = {task};
                        if strfind(task,'START') == 5
                            taskStarts(end,2) = {actor_task};
                        elseif strfind(task,'STOP')==5
                            taskStarts = removeTaskFromQueue(taskStarts,2,actor_task);
                        end
                    elseif actor == 'OP'
                        tasks(end,3) = {task};
                        if strfind(task,'START') == 5
                            taskStarts(end,3) = {actor_task};
                        elseif strfind(task,'STOP')==5
                            taskStarts = removeTaskFromQueue(taskStarts,3,actor_task);
                        end
                    elseif actor == 'VO'
                        tasks(end,4) = {task};
                        if strfind(task,'START') == 5
                            taskStarts(end,4) = {actor_task};
                        elseif strfind(task,'STOP')==5
                            taskStarts = removeTaskFromQueue(taskStarts,4,actor_task);
                        end
                    end
                end
            end
        end
    end
    tasks = taskStarts;
end

