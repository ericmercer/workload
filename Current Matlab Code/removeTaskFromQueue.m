function [ tasks ] = removeTaskFromQueue( taskStarts, actor, actor_task )
    len = size(taskStarts);
    len = len(1);
    for index = 0:len-2
       check = taskStarts(len-index,actor);
       check = check{1};
       actor_task = actor_task;
       if size(check) == size(actor_task)
           if check == actor_task
               
               taskStarts(len-index,actor) = {''};
           end
       end
    end
    
    tasks = taskStarts;
end

