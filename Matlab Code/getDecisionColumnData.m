function out_data = getDecisionColumnData( in_data )
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
    for i = 2: a(1)
        out_data(end+1,1) = 0;
        out_data(end,2) = 0;
        out_data(end,3) = 0;
        out_data(end,4) = 0;
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
                            out_data(i,k) = out_data(i,k) + val;
                        end
                    end
                end
            end
        end
    end
end


