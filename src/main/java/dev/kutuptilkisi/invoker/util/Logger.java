package dev.kutuptilkisi.invoker.util;

import dev.kutuptilkisi.invoker.Invoker;

public class Logger {
    private static final String PREFIX = "[DEBUGGER]";
    private static final String WARNING_PREFIX = PREFIX+"[WARNING]";

    public static void info(String text){
        if(Invoker.invokerAPI.getBoolean("debug")){
            Invoker.invokerAPI.logInfo(PREFIX + " " + text);
        }
    }
    public static void warning(String text){
        if(Invoker.invokerAPI.getBoolean("debug")) {
            Invoker.invokerAPI.logWarn(WARNING_PREFIX + " " + text);
        }
    }
}
