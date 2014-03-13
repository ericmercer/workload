function [ resource, temporal, decision, tasks, time ] = plotWorkloads( name )

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


    resource = getResourceData(F);
    temporal = getTemporalData(tempo);
    decision = getDecisionData(G,H);
    
%     %individual resources
%     resourceTotal = resource(:,1)+resource(:,2)+resource(:,3);     %combine the three actors' workload
%     temporalTotal = temporal(:,1)+temporal(:,2)+temporal(:,3);     %combine the three actors' workload
%     decisionTotal = decision(:,1)+decision(:,2)+decision(:,3);     %combine the three actors' workload

    time = B;
end

