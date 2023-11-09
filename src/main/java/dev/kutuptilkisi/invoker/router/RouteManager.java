package dev.kutuptilkisi.invoker.router;

import dev.kutuptilkisi.invoker.router.impl.RouteData;
import dev.kutuptilkisi.invoker.util.Logger;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RouteManager {
    private final List<RouteData> routes;
    public RouteManager(){
        routes = new ArrayList<>();
    }

    public void addRoute(Routable routable){
        for(Method method : Arrays.stream(routable.getClass().getDeclaredMethods()).filter(this::hasRouteAnnotation).collect(Collectors.toList())){
            RouteData routeData = getRouteData(routable, method);
            if(routeData != null) this.addRoute(routeData);
        }
    }

    private void addRoute(RouteData routeData){
        Logger.info("New route registered with name of: "+routeData.getFullName());
        this.routes.add(routeData);
    }

    public RouteData getRouteOrNull(String name){
        for(RouteData routeData : routes){
            if(routeData.getFullName().equals(name)){
                return routeData;
            }
        }
        return null;
    }

    private boolean hasRouteAnnotation(Method method){
        return method.isAnnotationPresent(Route.class);
    }

    private RouteData getRouteData(Routable routable, Method method){
        if(!hasRouteAnnotation(method)) return null;
        return new RouteData(method.getAnnotation(Route.class), routable, method);
    }
}
