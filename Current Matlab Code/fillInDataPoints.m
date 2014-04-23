function [ out_data, out_time ] = fillInDataPoints( data, time )
    last_time = time(end);
    i = 1;
    temp = [];
    temp_t = [];
    while time(i) < last_time
        t1 = data(i,1);
        t2 = data(i,2);
        t3 = data(i,3);
        t4 = data(i,4);
        temp(end+1,1) = t1;
        temp(end,2) = t2;
        temp(end,3) = t3;
        temp(end,4) = t4;
        temp_t(end+1) = time(i);
        j = time(i);
        while j+1 < time(i+1)
            temp(end+1,1) = t1;
            temp(end,2) = t2;
            temp(end,3) = t3;
            temp(end,4) = t4;
            temp_t(end+1) = j + 1;
            j = j + 1;
        end
        i = i + 1;
    end
    out_time = temp_t;
    out_data = temp;
end

