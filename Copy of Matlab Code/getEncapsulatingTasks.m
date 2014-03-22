function [ tasks ] = getEncapsulatingTasks( tasksS, tasksE, x )
    ending_point = size(tasksS);
    ending_point = ending_point(1);
    size(tasksE)
    tasks = {'PS','MM','OP'};
    for index = 1:ending_point
        taskS = char(tasksS(index,:));
        if(length(taskS) > 1)
            taskS = strrep(taskS,'__','][');
            [start,endIndex] = regexp(taskS,'\[\w\w_(START|STOP)_\w+]');
            num = size(start);
            num = num(2);
            if num > 0
                for entry = 1:num
                    task = taskS(start(entry):endIndex(entry));
                    actor = taskS(start(entry)+1:start(entry)+2);
                    actor_task = taskS(start(entry)+10:endIndex(entry)-4);
                    tasks(end+1,1) = {''};
                    tasks(end,2) = {''};
                    tasks(end,3) = {''};
%                     size(actor);
                    if actor == 'PS'
                        tasks(end,1) = {task};
                    elseif actor == 'MM'
                        tasks(end,2) = {task};
                    elseif actor == 'OP'
                        tasks(end,3) = {task};
                    end
                end
            end
        end
    end
end

