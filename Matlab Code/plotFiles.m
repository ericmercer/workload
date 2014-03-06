function [maxTime ] = plotFiles( names, i )
    figure(i)
    maxR = 0;
    maxD = 0;
    maxT = 0;
    maxTime = 0;
    for i = 1:6
        [ resource, temporal, decision, tasks, time ] = plotWorkloads( names(i,:) );
        resource = resource(:,1) + resource(:,2) + resource(:,3);
        temporal = temporal(:,1) + temporal(:,2) + temporal(:,3);
        decision = decision(:,1) + decision(:,2) + decision(:,3);
        maxR = max(maxR, max(resource(:)));
        maxD = max(maxD, max(decision(:)));
        maxT = max(maxT, max(temporal(:)));
        maxTime = max(maxTime,time(end));
    end
    maxTotal = max(maxR,max(maxD,maxT));
    for i = 1:6
        [ resource, temporal, decision, tasks, time ] = plotWorkloads( names(i,:) );
        subplot(3,2,i)
        hold all
        resource = resource(:,1) + resource(:,2) + resource(:,3);
        temporal = temporal(:,1) + temporal(:,2) + temporal(:,3);
        decision = decision(:,1) + decision(:,2) + decision(:,3);
        
        resource = resource./maxR;
        temporal = temporal./maxT;
        decision = decision./maxD;
        
        plot(time,resource,'bx--')
        plot(time,decision,'gx--');
        axis([0 maxTime 0 1]);
        legend('Resource','Decision');
        name = names(i,:);
        name = strrep(name,'H','Highest ');
        name = strrep(name,'L','Lowest ');
        name = strrep(name,'C','Cumulative ');
        
        name = strrep(name,'R','Resource ');
        name = strrep(name,'T','Temporal ');
        name = strrep(name,'D','Decision ');
        name = strrep(name,'W','Workload');
        title(name);
        hold off
    end
end

