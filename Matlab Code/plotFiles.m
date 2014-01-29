function [resource, temporal, decision, tasks, time ] = plotFiles( names, i )
    figure(i)
    for i = 1:6
        names(i,:);
        [ resource, temporal, decision, tasks, time ] = plotWorkloads( names(i,:) );
        subplot(3,2,i)
        hold all
        plot(time,resource(:,1)+temporal(:,1)+decision(:,1),'b')
        plot(time,resource(:,2)+temporal(:,2)+decision(:,2),'r')
        plot(time,resource(:,3)+temporal(:,3)+decision(:,3),'g')
        legend('Parent Search', 'Operator', 'Mission Manager')
%         plot(time,resource(:,1) + resource(:,2) + resource(:,3),'b')
%         plot(time,temporal(:,1) + temporal(:,2) + temporal(:,3),'r')
%         plot(time,decision(:,1) + decision(:,2) + decision(:,3),'g');
%         legend('Resource','Temporal','Decision');
        title(names(i,:));
        hold off
    end
end

