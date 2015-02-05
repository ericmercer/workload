function a = munge(f,fevents,win)
M = csvread(f);
w = ones(1,win)/win;

% Time is in 1/10 of a second
x = (1:length(M))/10;
E = csvread(fevents);

figure
hax = axes;
title('Instantaneous Workload')
grid off;
hold on;

%y = filter(w, 1, M(:,2));
%plot(x,y,'-')

y = filter(w, 1, M(:,3));
plot(x,y,'-')

%y = filter(w, 1, M(:,4));
%plot(x,y,'-')

y = filter(w, 1, M(:,5));
plot(x,y,'-')

%y = filter(w, 1, M(:,6));
%plot(x,y,'-')

for i = E
    line([i i], get(hax,'YLim'), ...
        'Color', [.5,.5,.5], ...
        'LineWidth', .5, ...
        'LineStyle', '--');
    %plot(i, y(i*10), 'ro');
end

%legend('Physical', 'Perception', 'Visual', 'Decision', 'Activity')
legend('Perceptual', 'Decision')
hold off;