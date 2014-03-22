function [data, timeD] = getDecisionData(time, A,B )
    A = getDecisionDurationColumnData(A,time);
    B = getDecisionColumnData(B);
    A = A/norm(A);
    B = B/norm(B);
    
    length = size(A);
    length = length(1);
    data = [];
    for index = 1:length
%         if data(end,1)+data(end,2)+data(end,3)+data(end,4) == 0
%             data(end+1,1) = 0;
%         end
        data(end+1,1) = A(index,1)+B(index,1);
        data(end,2) = A(index,2)+B(index,2);
        data(end,3) = A(index,3)+B(index,3);
        data(end,4) = A(index,4)+B(index,4);
%         if data(end,1)+data(end,2)+data(end,3)+data(end,4) > 0
%             timeD(end+1) = time(index);
    end
    timeD = time;
end

