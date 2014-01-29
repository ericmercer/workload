clear all
close all
names = ['HCDW.csv';'HCRW.csv';'HCTW.csv';'LCDW.csv';'LCRW.csv';'LCTW.csv'];

fig_num = 1;

[resource, temporal, decision, tasks, time ] = plotFiles(names, fig_num);

examinePlots(time, names, fig_num);