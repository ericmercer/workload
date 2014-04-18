function [ out_data, timeT ] = getTemporalData(time, in_data )
    OP = 'Operator';
    VO = 'VideoOperator';
    PS = 'ParentSearch';
    MM = 'MissionManager';
    a = size(in_data);
    out_data = [];
    data = getDecisionColumnData(in_data);
    timeT = [];
    for i = 0:time(end)
        timeT(end+1) = i;
        array = getSlidingData(time, data, i);
        out_data(end+1,1) = array(1);
        out_data(end,2) = array(2);
        out_data(end,3) = array(3);
        out_data(end,4) = array(4);
    end
end

function out = getSlidingData(time,data, t)
    a = size(time);
    index = 1;
    if t > 10
        while(time(index) < t-9)
            index = index + 1;
        end
    end
    out = [0,0,0,0];
    while(index < a(1) && time(index) <= t)
        if t > 247 && t < 260
            data(index,:);
        end
        if data(index,1) > 0 
            out(1) = out(1) + 1;
        end
        if data(index,2) > 0 
            out(2) = out(2) + 1;
        end
        if data(index,3) > 0 
            out(3) = out(3) + 1;
        end
        if data(index,4) > 0 
            out(4) = out(4) + 1;
        end
        index = index + 1;
    end
end
