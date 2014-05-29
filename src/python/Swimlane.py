class swimlane: 
    def __init__(self): 
        self.actor_lane_id = {}
        self.lanes = "var lanes = [\n"
        self.items = "var items = [\n"
        self.for_lanes = 0
        self.part1 = '''<html>\n\n<head>\n\n<title>Swimlane using d3.js</title>\n\n<script type="text/javascript" src="http://mbostock.github.com/d3/d3.v2.js"></script>
        \n<style>\n\n.chart {\n\n  shape-rendering: crispEdges;\n\n}\n\n\n\n.mini text {\n\n  font: 9px sans-serif; \n\n}
        \n\n\n.main text {\n\n  font: 12px sans-serif;  \n\n}\n\n\n\n.month text {\n\n  text-anchor: start;\n\n}\n\n\n\n.todayLine {\n\n  stroke: blue;\n\n  stroke-width: 1.5;\n\n}
        \n\n\n.axis line, .axis path {\n\n  stroke: black;\n\n}\n\n\n\n.miniItem {\n\n  stroke-width: 6;  \n\n}\n\n\n\n.item {\n\n  stroke: gray;\n\n  fill: #ddd;\n\n}\n\n\n\n.brush .extent {
        \n  stroke: gray;\n\n  fill: blue;\n\n  fill-opacity: .165;\n\n}\n\n</style>\n\n</head>\n\n<body>\n\n\n\n<script type="text/javascript">\n\n\n\n// helper function to create dates prior to 1000
        \nvar yr = function(year) {\n\n  var date = new Date(2000,1,1);\n\n  date.setFullYear(year);\n\n  return date;\n\n}\n\n\n\n// lanes is an array of lane objects that have the following properties
        \n//    id:    the unique id for this swimlane\n\n//    label: the text label for this swimlane\n\n//    \n\n// these determine how many horizontal lanes there will be in the chart
        \n// and what their names will be'''
        self.part2 = '''\n\n\n// items is an array of item objects that have the following properties\n\n//    id:    the unique id for this item\n\n//    lane:  the id of the lane that this item belongs in
        \n//    start: the starting value for this item\n\n//    end:   the end value for this item\n\n//    class: the css class that should be applied to this item
        \n//    \n\n// these define the actual items that are displayed on the chart'''
        self.part3 = """\n\n\n\n// define the chart extents\n\nvar margin = {top: 20, right: 15, bottom: 15, left: 100}\n\n  , width = 1880 - margin.left - margin.right\n\n  , height = 1080 - margin.top - margin.bottom
        \n  , miniHeight = lanes.length * 12 + 50\n\n  , mainHeight = height - miniHeight - 50;\n\n\n\nvar x = d3.time.scale()\n\n  .domain([d3.min(items, function(d) { return d.start - 100000; }),
        \n       d3.max(items, function(d) { return d.end; })])\n\n  .range([0, width]);\n\nvar x1 = d3.time.scale().range([0, width]);\n\n\n\nvar ext = d3.extent(lanes, function(d) { return d.id; });
        \nvar y1 = d3.scale.linear().domain([ext[0], ext[1] + 1]).range([0, mainHeight]);\n\nvar y2 = d3.scale.linear().domain([ext[0], ext[1] + 1]).range([0, miniHeight]);
        \n\n\nvar chart = d3.select('body')\n\n  .append('svg:svg')\n\n  .attr('width', width + margin.right + margin.left)\n\n  .attr('height', height + margin.top + margin.bottom)
        \n  .attr('class', 'chart');\n\n\n\nchart.append('defs').append('clipPath')\n\n  .attr('id', 'clip')\n\n  .append('rect')\n\n    .attr('width', width)\n\n    .attr('height', mainHeight);
        \n\n\nvar main = chart.append('g')\n\n  .attr('transform', 'translate(' + margin.left + ',' + margin.top + ')')\n\n  .attr('width', width)\n\n  .attr('height', mainHeight)\n\n  .attr('class', 'main');
        \n\n\nvar mini = chart.append('g')\n\n  .attr('transform', 'translate(' + margin.left + ',' + (mainHeight + 60) + ')')\n\n  .attr('width', width)\n\n  .attr('height', miniHeight)
        \n  .attr('class', 'mini');\n\n\n\n// draw the lanes for the main chart\n\nmain.append('g').selectAll('.laneLines')\n\n  .data(lanes)\n\n  .enter().append('line')\n\n  .attr('x1', 0)
        \n  .attr('y1', function(d) { return d3.round(y1(d.id)) + 0.5; })\n\n  .attr('x2', width)\n\n  .attr('y2', function(d) { return d3.round(y1(d.id)) + 0.5; })
        \n  .attr('stroke', function(d) { return d.label === '' ? 'white' : 'lightgray' });\n\n\n\nmain.append('g').selectAll('.laneText')\n\n  .data(lanes)\n\n  .enter().append('text')
        \n  .text(function(d) { return d.label; })\n\n  .attr('x', -10)\n\n  .attr('y', function(d) { return y1(d.id + .5); })\n\n  .attr('dy', '0.5ex')\n\n  .attr('text-anchor', 'end')
        \n  .attr('class', 'laneText');\n\n\n\n// draw the lanes for the mini chart\n\nmini.append('g').selectAll('.laneLines')\n\n  .data(lanes)\n\n  .enter().append('line')
        \n  .attr('x1', 0)\n\n  .attr('y1', function(d) { return d3.round(y2(d.id)) + 0.5; })\n\n  .attr('x2', width)\n\n  .attr('y2', function(d) { return d3.round(y2(d.id)) + 0.5; })
        \n  .attr('stroke', function(d) { return d.label === '' ? 'white' : 'lightgray' });\n\n\n\nmini.append('g').selectAll('.laneText')\n\n  .data(lanes)\n\n  .enter().append('text')
        \n  .text(function(d) { return d.label; })\n\n  .attr('x', -10)\n\n  .attr('y', function(d) { return y2(d.id + .5); })\n\n  .attr('dy', '0.5ex')\n\n  .attr('text-anchor', 'end')
        \n  .attr('class', 'laneText');\n\n\n\n// draw the x axis\n\nvar xDateAxis = d3.svg.axis()\n\n  .scale(x)\n\n  .orient('bottom');\n\n\n\nvar x1DateAxis = d3.svg.axis()
        \n  .scale(x1)\n\n  .orient('bottom');\n\n\n\nmain.append('g')\n\n  .attr('transform', 'translate(0,' + mainHeight + ')')\n\n  .attr('class', 'main axis date')\n\n  .call(x1DateAxis);
        \n\n\nmini.append('g')\n\n  .attr('transform', 'translate(0,' + miniHeight + ')')\n\n  .attr('class', 'axis date')\n\n  .call(xDateAxis);\n\n\n\n// draw the items
        \nvar itemRects = main.append('g')\n\n  .attr('clip-path', 'url(#clip)');\n\n\n\nmini.append('g').selectAll('miniItems')\n\n  .data(getPaths(items))\n\n  .enter().append('path')
        \n  .attr('class', function(d) { return 'miniItem ' + d.class; })\n\n  .attr('d', function(d) { return d.path; });\n\n\n\n// invisible hit area to move around the selection window
        \nmini.append('rect')\n\n  .attr('pointer-events', 'painted')\n\n  .attr('width', width)\n\n  .attr('height', miniHeight)\n\n  .attr('visibility', 'hidden')\n\n  .on('mouseup', moveBrush);
        \n\n\n// draw the selection area\n\nvar brush = d3.svg.brush()\n\n  .x(x)\n\n  .extent([yr(0), yr(50)])\n\n  .on("brush", display);\n\n\n\nmini.append('g')\n\n  .attr('class', 'x brush')
        \n  .call(brush)\n\n  .selectAll('rect')\n\n    .attr('y', 1)\n\n    .attr('height', miniHeight - 1);\n\n\n\nmini.selectAll('rect.background').remove();\n\ndisplay();
        \n\n\nfunction display () {\n\n\n\n  var rects, labels\n\n    , minExtent = brush.extent()[0]\n\n    , maxExtent = brush.extent()[1]
        \n    , visItems = items.filter(function (d) { return d.start < maxExtent && d.end > minExtent});\n\n\n\n  mini.select('.brush').call(brush.extent([minExtent, maxExtent]));   
        \n\n\n  x1.domain([minExtent, maxExtent]);\n\n\n\n  // update the axis\n\n  main.select('.main.axis.date').call(x1DateAxis);\n\n\n\n  // upate the item rects
        \n  rects = itemRects.selectAll('rect')\n\n    .data(visItems, function (d) { return d.id; })\n\n    .attr('x', function(d) { return x1(d.start); })
        \n    .attr('width', function(d) { return x1(d.end) - x1(d.start); });\n\n\n\n  rects.enter().append('rect')\n\n    .attr('x', function(d) { return x1(d.start); })
        \n    .attr('y', function(d) { return y1(d.lane) + .1 * y1(1) + 0.5; })\n\n    .attr('width', function(d) { return x1(d.end) - x1(d.start); })
        \n    .attr('height', function(d) { return .8 * y1(1); })\n\n    .attr('class', function(d) { return 'mainItem ' + d.class; });\n\n\n\n  rects.exit().remove();
        \n\n\n  // update the item labels\n\n  labels = itemRects.selectAll('text')\n\n    .data(visItems, function (d) { return d.id; })
        \n    .attr('x', function(d) { return x1(Math.max(d.start, minExtent)) + 2; });\n\n\n\n  labels.enter().append('text')\n\n    .text(function (d) { return '\\n\\n\\n\\n ' + d.id; })
        \n    .attr('x', function(d) { return x1(Math.max(d.start, minExtent)) + 2; })\n\n    .attr('y', function(d) { return y1(d.lane) + .4 * y1(1) + 0.5; })
        \n    .attr('text-anchor', 'start')\n\n    .attr('class', 'itemLabel');\n\n\n\n  labels.exit().remove();\n\n}
        \n\n\nfunction moveBrush () {\n\n  var origin = d3.mouse(this)\n\n    , point = x.invert(origin[0])\n\n    , halfExtent = (brush.extent()[1].getTime() - brush.extent()[0].getTime()) / 2
        \n    , start = new Date(point.getTime() - halfExtent)\n\n    , end = new Date(point.getTime() + halfExtent);\n\n\n\n  brush.extent([start,end]);\n\n  display();\n\n}
        \n\n\n// generates a single path for each item class in the mini display\n\n// ugly - but draws mini 2x faster than append lines or line generator
        \n// is there a better way to do a bunch of lines as a single path with d3?\n\nfunction getPaths(items) {\n\n  var paths = {}, d, offset = .5 * y2(1) + 0.5, result = [];
        \n  for (var i = 0; i < items.length; i++) {\n    d = items[i];\n\n    if (!paths[d.class]) paths[d.class] = ''; \n\n    paths[d.class] += ['M',x(d.start),(y2(d.lane) + offset),'H',x(d.end)].join(' ');
        \n  }\n\n\n\n  for (var className in paths) {\n\n    result.push({class: className, path: paths[className]});\n\n  }\n\n\n\n  return result;\n\n}
        \n\n\n</script>\n\n</body>\n\n</html>"""    
    def addLane(self, lane_to_add):
        self.lanes+=lane_to_add
        self.for_lanes +=1
        return self
    def addNumLanes(self, num_to_add):
        self.for_lanes+=num_to_add
        return self
    def getNumLane(self):
        return self.for_lanes
    def addInfo(self, info_to_add):
        self.items+=info_to_add
        return self  
    def printFile(self):
        to_return = self.part1 +"\n"+self.lanes+"\n"+self.part2+"\n"+self.items+"\n"+self.part3
        return to_return
    def resetItems(self):
        self.items = "var items = [\n"
        return self