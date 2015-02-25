function a = munge(f,fevents, fphases,span,method)
M = csvread(f);

% Time is in 1/10 of a second
%x = (1:length(M))/10;
% Time is 1 second
x = (1:length(M));

E = csvread(fevents);
P = csvread(fphases);

temporal = M(:,3);
perception = M(:,4);
decision = M(:,6);

figure
hax = axes;
axis([0 350 -1 5])
title('Instantaneous Workload')
grid off;
hold on;

y = smooth(temporal, span, method);
%y = temporal;
plot(x,y,'-')

y = smooth(perception, span, method);
%y = perception;
plot(x,y,'-')

y = smooth(decision, span, method);
%y = decision;
plot(x,y,'-')

for i = E
    line([i i], get(hax,'YLim'), ...
        'Color', [.8,.8,.8], ...
        'LineWidth', .3, ...
        'LineStyle', '--');
end

for i = P
    line([i i], get(hax,'YLim'), ...
        'Color', [.8,0,0], ...
        'LineWidth', 1, ...
        'LineStyle', '--');
% Time is 1/10th of a second
%    prev = min(i*10, length(M));
% Time is 1 second
    prev = min(i, length(M));
end
%prev

legend('Temporal', 'Perceptual', 'Decision')
hold off;

p1 = (sum(temporal(1:prev(1))) + ...
      sum(perception(1:prev(1))) + ... 
      sum(decision(1:prev(1))))/prev(1);
p2 = (sum(temporal(prev(1):prev(2))) + ...
      sum(perception(prev(1):prev(2))) + ...
      sum(decision(prev(1):prev(2))))/(prev(2) - prev(1) + 1);
p3 = (sum(temporal(prev(2):prev(3))) + ...
      sum(perception(prev(2):prev(3))) + ...
      sum(decision(prev(2):prev(3))))/(prev(3) - prev(2) + 1);

sprintf('%s = %d\n', 'Phase 1', p1, 'Phase2', p2, 'Phase 3', p3)