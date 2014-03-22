function [ out_data, timeT ] = getTemporalData(time, tempo )

    OP = 'Operator';
    VO = 'VideoOperator';
    PS = 'ParentSearch';
    MM = 'MissionManager';
    out_data = [];
    out_data(end+1,1) = 0;
    out_data(end,2) = 0;
    out_data(end,3) = 0;
    out_data(end,4) = 0;
    
    a = size(tempo);
    out_data = [];
    out_data(end+1,1) = 0;
    out_data(end,2) = 0;
    out_data(end,3) = 0;
    out_data(end,4) = 0;
    for i = 2: a(1)
        if ~isnan(tempo(i))
            out_data(i,1) = tempo(i)/4;
            out_data(i,2) = out_data(i,1);
            out_data(i,3) = out_data(i,2);
            out_data(i,4) = out_data(i,3);
        elseif i > 1
            out_data(i,1) = out_data(i-1,1);
            out_data(i,2) = out_data(i,1);
            out_data(i,3) = out_data(i,2);
            out_data(i,4) = out_data(i,3);
        end
    end
    timeT = time;
end

