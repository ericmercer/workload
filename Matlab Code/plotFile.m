function [ ParentSearch, Operator, MissionManager, tasks, time ] = plotFile( name )

    file = fopen(name);
    Header = fgetl(file);
    fclose(file);

    Header = regexp(Header,'([^,]*)','tokens');
    Header = cat(2, Header{:});

    file = fopen(name);
    Data = textscan(file,'%d%s%d%s%d%s%d%s%s','Delimiter',',','HeaderLines',1);
    fclose(file);

    A = Data(1);
    B = A{1};
    A = Data(3);
    C = A{1};
    A = Data(5);
    E = A{1};
    A = Data(7);
    D = A{1};
    A = Data(2);
    F = A{1};
    A = Data(4);
    G = A{1};
    A = Data(6);
    H = A{1};
    A = Data(8);
    tasks = A{1};
    resource = getResourceData(F);
    temporal = getTemporalData(G);
    decision = getDecisionData(H);
    ParentSearch = [];
    Operator = [];
    MissionManager = [];
    dimensions = size(resource);
    for i = 1:dimensions(1)
        ParentSearch(i,1) = resource(i,1)+temporal(i,1)+decision(i,1);
        Operator(i,1) = resource(i,2)+temporal(i,2)+decision(i,2);
        MissionManager(i,1) = resource(i,3)+temporal(i,3)+decision(i,3);
    end
    total = ParentSearch+Operator+MissionManager;
    ParentSearch = ParentSearch./max(ParentSearch);
    Operator = Operator./max(Operator);
    MissionManager = MissionManager./max(MissionManager);
    time = B;
end

