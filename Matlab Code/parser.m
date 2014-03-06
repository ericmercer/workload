clear all
close all
names = ['HCDW.csv';'HCRW.csv';'HCTW.csv';'LCDW.csv';'LCRW.csv';'LCTW.csv'];

fig_num = 1;

[maxTime ] = plotFiles(names, fig_num);

examinePlots(maxTime, names, fig_num);