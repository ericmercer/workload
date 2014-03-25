function [data, timeD] = getDecisionData(time, A,B )
    [A, timeA] = getDecisionDurationColumnData(A,time);
    A = A/norm(A);
    B = getDecisionColumnData(B);
    [B, timeB] = fillInDataPoints(B,time);
    st = size(timeB)
    for i = 2:st(2)-1
        B(i,1)+B(i,2)+B(i,3)+B(i,4)
        if B(i,1)+B(i,2)+B(i,3)+B(i,4) == 0
            i
            B(i,1) = B(i-1,1);
            B(i,2) = B(i-1,2);
            B(i,3) = B(i-1,3);
            B(i,4) = B(i-1,4);
        end
    end
    B = B/norm(B);

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
                data(end,1) = A(i,1) + B(j,1);
                data(end,2) = A(i,2) + B(j,2);
                data(end,3) = A(i,3) + B(j,3);
                data(end,4) = A(i,4) + B(j,4);
            elseif s2(1) ~= 0 && s2(2) ~= 0
                data(end,1) = B(j,1);
                data(end,2) = B(j,2);
                data(end,3) = B(j,3);
                data(end,4) = B(j,4);
            elseif s1(1) ~= 0 && s1(2) ~= 0
                data(end,1) = A(i,1);
                data(end,2) = A(i,2);
                data(end,3) = A(i,3);
                data(end,4) = A(i,4);
            end
        end
        
    end

end

