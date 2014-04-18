function [out_data, out_time] = getDecisionDurationColumnData( in_data, time )
    OP = 'Operator';
    VO = 'VideoOperator';
    PS = 'ParentSearch';
    MM = 'MissionManager';
    a = size(in_data);
    out_time = [];
    out_data = [];
    for t = 0:max(time)
        out_time(end+1,1) = t;
        out_data(end+1,:) = [0,0,0,0];
    end
    
    for i = 2: a(1)
        if length(char(in_data(i,:))) > 1
            string = char(in_data(i,:));
            [start, endIndex] = regexp(string, '\(\w+\s\w+\s\[\d+\]\)');
            num_entries = size(start);
            if num_entries > 0
                for j = 1:num_entries(2)
                    subset = string(start(j):endIndex(j));
                    endOfName = strfind(subset,' ');
                    actor = subset(2:endOfName(1)-1);
                    [s_index, e_index] = regexp(subset,'\[\d+\]');
                    val = str2num(subset(s_index+1:e_index-1));
                    k = -1;
                    if size(PS) == size(actor)
                        if PS == actor
                            k = 1;
                        end
                   elseif size(OP) == size(actor)
                        if OP == actor
                            k = 2;
                        end
                    elseif size(MM) == size(actor)
                        if MM == actor
                            k = 3;
                        end
                    elseif size(VO) == size(actor)
                        if VO == actor
                            k = 4;
                        end
                    end
                    if k ~= -1
                        cur_time = time(i);
                        time_check = cur_time-val;
                        t = find(out_time==cur_time);
                        while t > 0 && out_time(t) > time_check
                            out_data(t,k) = out_data(t,k) + 1;
                            t = t - 1;
                        end
                    end
                end
            end
        end
    end
    
end


