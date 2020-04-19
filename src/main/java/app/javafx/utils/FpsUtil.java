package app.javafx.utils;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

public class FpsUtil {

    public static AtomicInteger count = new AtomicInteger(0);

    public static Long timeRange = 1*500l;

    public static void add(){
        count.incrementAndGet();
    }

    public static String getFps(){
        Float cCount = Float.valueOf(count.getAndSet(0))*1000;
        return new DecimalFormat("00.00").format(cCount/timeRange);
    }

}
