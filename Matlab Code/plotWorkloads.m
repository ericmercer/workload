function [ resource, temporal, decision, tasks, time ] = plotWorkloads( name )

    file = fopen(name);
    Header = fgetl(file);
    fclose(file);

    Header = regexp(Header,'([^,]*)','tokens');
    Header = cat(2, Header{:});

    file = fopen(name);
    Data = textscan(file,'%d%s%d%s%d%s%d%s%s','Delimiter',',','HeaderLines',1);
    fclose(file);

    A = Data(1);
    B = A{1};       %time
    A = Data(3);
    C = A{1};       %resource workload
    A = Data(5);
    E = A{1};       %temporal workload
    A = Data(7);
    D = A{1};       %decision workload
    A = Data(2);
    F = A{1};       %resource data
    A = Data(4);
    G = A{1};       %temporal data
    A = Data(6);
    H = A{1};       %decision data
    A = Data(8);
    tasks = A{1};
    resource = getResourceData(F);
    temporal = getTemporalData(G);
    decision = getTemporalData(H);
    
    %individual resources
%     resourceTotal = resource(:,1)+resource(:,2)+resource(:,3);     %combine the three actors' workload
%     temporalTotal = temporal(:,1)+temporal(:,2)+temporal(:,3);     %combine the three actors' workload
%     decisionTotal = decision(:,1)+decision(:,2)+decision(:,3);     %combine the three actors' workload
%     resource = resource./max(resourceTotal);
%     temporal = temporal./max(temporalTotal);
%     decision = decision./max(decisionTotal);

    for i = 1:3
        %individual actors
        r = max(resource(:,i));
        t = max(temporal(:,i));
        d = max(decision(:,i));
        resource(:,i) = resource(:,i)./r;
        temporal(:,i) = temporal(:,i)./t;
        decision(:,i) = decision(:,i)./d;
    end
    time = B;
end

