package dev.kutuptilkisi.invoker.util;

import dev.kutuptilkisi.invoker.Invoker;

public class Logger {
    private static final String PREFIX = "[DEBUGGER]";
    private static final String INFO_PREFIX = PREFIX+"[INFO]";
    private static final String WARNING_PREFIX = PREFIX+"[WARNING]";

    public static void info(String text){
        if(Invoker.getInstance().getConfig().getBoolean("debug")){
            System.out.println(INFO_PREFIX+" "+text);
        }
    }
    public static void warning(String text){
        if(Invoker.getInstance().getConfig().getBoolean("debug")) {
            System.out.println(WARNING_PREFIX + " " + text);
        }
    }
}
