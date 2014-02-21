function [data] = getDecisionData( A,B )
    A = getDecisionColumnData(A);
    B = getDecisionColumnData(B);
    length = size(A);
    length = length(1);
    data = [];
    for index = 1:length
        data(end+1,1) = A(index,1)+B(index,1);
        data(end,2) = A(index,2)+B(index,2);
        data(end,3) = A(index,3)+B(index,3);
    end
end

