function [out_data, timeR] = getResourceData(time, in_data)
    OP = 'Operator';
    VO = 'VideoOperator';
    PS = 'ParentSearch';
    MM = 'MissionManager';
    a = size(in_data);
    out_data = [];
    out_data(end+1,1) = 0;
    out_data(end,2) = 0;
    out_data(end,3) = 0;
    out_data(end,4) = 0;
    timeR = [];
    for i = 2: a(1)
        timeR(end+1) = time(i);
        out_data(end+1,1) = 0;
        out_data(end,2) = 0;
        out_data(end,3) = 0;
        out_data(end,4) = 0;
        if length(char(in_data(i,:))) > 1
            string = char(in_data(i,:));
            [start,endIndex] = regexp(string,'\(\w+\s\w+\s\[\w+\]\)');          %'( \w+\s+\d )')
            num_entries = size(start);
            if num_entries > 0
                for j = 1:num_entries(2)
                    subset = string(start(j):endIndex(j));
                    endOfName = strfind(subset,' ');
                    actor = subset(2:endOfName(1)-1);
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
                        array_dim = size(out_data);
                        if array_dim(1) == i
                            out_data(i,k) = out_data(i,k) + 1;
                        end
                    end
                end
            end
        end
    end
    
    last_time = timeR(end);
    i = 1;
    temp = [];
    temp_t = [];
    while timeR(i) < last_time
        t1 = out_data(i,1);
        t2 = out_data(i,2);
        t3 = out_data(i,3);
        t4 = out_data(i,4);
        temp(end+1,1) = t1;
        temp(end,2) = t2;
        temp(end,3) = t3;
        temp(end,4) = t4;
        temp_t(end+1) = timeR(i);
        j = timeR(i);
        while j < timeR(i+1)
            temp(end+1,1) = t1;
            temp(end,2) = t2;
            temp(end,3) = t3;
            temp(end,4) = t4;
            temp_t(end+1) = j + 1;
            j = j + 1;
        end
        i = i + 1;
            
    end
    timeR = temp_t;
    out_data = temp;
end
