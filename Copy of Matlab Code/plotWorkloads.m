function [ resource, temporal, decision, tasks, time, timeT, timeR, timeD ] = plotWorkloads( name )

    file = fopen(name);
    Header = fgetl(file);
    fclose(file);

    Header = regexp(Header,'([^,]*)','tokens');
    Header = cat(2, Header{:});

    file = fopen(name);
    Data = textscan(file,'%d%s%d%s%d%s%d%s%s%f','Delimiter',',','HeaderLines',1);
    fclose(file);

    temp_column = Data(1);
    B = temp_column{1};       %time
    temp_column = Data(3);
    C = temp_column{1};       %resource(Active Inputs) workload
    temp_column = Data(5);
    E = temp_column{1};       %decision(transition duration) workload
    temp_column = Data(7);
    D = temp_column{1};       %decision(enabled transitions) workload
    temp_column = Data(2);
    F = temp_column{1};       %resource(active input) data
    temp_column = Data(4);
    G = temp_column{1};       %decision(transition duration) data
    temp_column = Data(6);
    H = temp_column{1};       %decision(# enabled transitions) data
    temp_column = Data(8);
    tasks = temp_column{1};
    temp_column = Data(10);
    tempo = temp_column{1};     %op tempo


    [resource, timeR] = getResourceData(B, F);
    [temporal, timeT] = getTemporalData(B, tempo);
    [decision, timeD] = getDecisionData(B, G,H);
    
    last_time = max(timeR);
    new_timeR = [];
    new_timeR(end+1) = 0;
    newResource = [];
    newResource(end+1,1) = resource(1,1);
    newResource(end,2) = resource(1,2);
    newResource(end,3) = resource(1,3);
    newResource(end,4) = resource(1,4);
    indexR = 1;
    for t = 0:10:last_time
        while timeR(indexR) < t
            new_timeR(end+1) = timeR(indexR);
            newResource(end+1,1) = resource(indexR,1);
            newResource(end,2) = resource(indexR,2);
            newResource(end,3) = resource(indexR,3);
            newResource(end,4) = resource(indexR,4);
            indexR = indexR + 1;
        end
        if timeR(indexR) ~= t
            new_timeR(end+1) = t;
            newResource(end+1,1) = newResource(end,1);
            newResource(end,2) = newResource(end-1,2);
            newResource(end,3) = newResource(end-1,3);
            newResource(end,4) = newResource(end-1,4);
        end
    end
    resource = newResource;
    timeR = new_timeR;
%     %individual resources
%     resourceTotal = resource(:,1)+resource(:,2)+resource(:,3);     %combine the three actors' workload
%     temporalTotal = temporal(:,1)+temporal(:,2)+temporal(:,3);     %combine the three actors' workload
%     decisionTotal = decision(:,1)+decision(:,2)+decision(:,3);     %combine the three actors' workload

    time = B;
end

