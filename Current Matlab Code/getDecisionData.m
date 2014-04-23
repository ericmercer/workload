function [data, timeD] = getDecisionData(time, enabled,dur )
    [dur, timeA] = getDecisionDurationColumnData(dur,time);
    enabled = getDecisionColumnData(enabled);
    [enabled, timeB] = fillInDataPoints2(enabled,time);
    max_val = max(max(max(dur)),max(max(enabled)));
    dur = dur/max(max(dur));%norm(dur);
    enabled = enabled/max(max(enabled));%norm(enabled);

    data = [];
    timeD = [];
    for t = 0:max(time)
        i = find(timeA==t);
        s1 = size(i);
        j = find(timeB==t);
        s2 = size(j);
        if s2(1) ~= 0 %|| s1(1) ~= 0
            timeD(end+1) = t;
            data(end+1,1) = 0;
            data(end,2) = 0;
            data(end,3) = 0;
            data(end,4) = 0;
            if s1(1) ~= 0 && s1(2) ~= 0 && s2(1) ~= 0 && s2(2) ~= 0
                data(end,1) = dur(i,1) + enabled(j,1);
                data(end,2) = dur(i,2) + enabled(j,2);
                data(end,3) = dur(i,3) + enabled(j,3);
                data(end,4) = dur(i,4) + enabled(j,4);
            elseif s2(1) ~= 0 && s2(2) ~= 0
                data(end,1) = enabled(j,1);
                data(end,2) = enabled(j,2);
                data(end,3) = enabled(j,3);
                data(end,4) = enabled(j,4);
            elseif s1(1) ~= 0 && s1(2) ~= 0
                data(end,1) = dur(i,1);
                data(end,2) = dur(i,2);
                data(end,3) = dur(i,3);
                data(end,4) = dur(i,4);
            end
        end
        
    end

end

