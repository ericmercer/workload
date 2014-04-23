function [ output_args ] = examinePlots( maxTime, names, fig_num )
    condition = 1;
    while (condition >= 1)
        [xm ym] = ginput(fig_num);
        x_limit = [0, maxTime];
        y_limit = [0, 1];
        if xm >= x_limit(1) && xm <= x_limit(2) && ym >= y_limit(1) && ym <= y_limit(2)
            h = gca;
            if (h < 200)
                names(1,:)
                file = fopen(names(1,:));
            elseif (h < 240)
                names(2,:)
                file = fopen(names(2,:));
            elseif (h < 300)
                names(3,:)
                file = fopen(names(3,:));
            elseif (h < 360)
                names(4,:)
                file = fopen(names(4,:));
            elseif (h < 410)
                names(5,:)
                file = fopen(names(5,:));
            elseif (h < 470)
                names(6,:)
                file = fopen(names(6,:));
            end
            Data = textscan(file,'%d%s%d%s%d%s%d%s%s','Delimiter',',','HeaderLines',1);
            fclose(file);
            A = Data(1);
            B = Data(8);
            C = Data(9);
            time = A{1};
            time = time(1:end-1);
            tasksS = B{1};
            tasksE = C{1};
            
            lh = findall(h,'type','line');
            xp = get(lh,'xdata');
            yp = get(lh,'ydata');

            if (xm > (floor(xm)+.5))
                x = floor(xm);
            else
                x = ceil(xm);
            end
            
            entry = 1;
            for i=1:size(time)
                if(time(i) < x)
                    entry = entry + 1;
                end
            end

            if time(end) > x
                t = x;
                x = entry;

                to_end = size(time);
                to_end = to_end(1)-x;
                start_nodes = getStartingTasks(tasksS,tasksE,x);
                end_nodes = getEndingTasks(tasksS,tasksE,x);
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
                
                nodes = getEncapsulatingTasks2(taskList,x);
                st = size(nodes);
                st = st(1);

                ps = {'ParentSearch Tasks:'};
                for i = 2:st
                    start = char(nodes(i,1));
                        if length(start) > 1
                                ps(end+1,1) = {start};
                        end
                end
                mm = {'MissionManager Tasks:'};
                for i = 2:st
                    start = char(nodes(i,2));
                        if length(start) > 1
                                mm(end+1,1) = {start};
                        end
                end
                op = {'Operator Tasks:'};
                for i = 2:st
                    start = char(nodes(i,3));
                        if length(start) > 1
                                op(end+1,1) = {start};
                            end
                end
                vo = {'VideoOperator Tasks:'};
                for i = 2:st
                    start = char(nodes(i,4));
                        if length(start) > 1
                                vo(end+1,1) = {start};
                            end
                end
                
                xl = get(gca, 'XLim');
                yl = get(gca, 'YLim');
                yesno = any(strcmp(who,'l1'));
                if yesno == 1
                    delete(l1);
                    delete(l2);
                end
                tasks = sprintf(' %s\n\n',ps{:},mm{:},op{:},vo{:});
                l1 = line([xm xm],yl,'Color','k');
                l2 = line(xl,[ym ym],'Color','k');
                msg = msgbox(tasks,'Tasks','modal');
            end
        end
    end


end

