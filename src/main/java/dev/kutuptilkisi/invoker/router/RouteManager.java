package dev.kutuptilkisi.invoker.router;

import dev.kutuptilkisi.invoker.router.impl.RouteData;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteManager {
    private final List<RouteData> routes;
    public RouteManager(){
        routes = new ArrayList<>();
    }

    public void addRoute(Routable routable){
        for(Method method : Arrays.stream(routable.getClass().getDeclaredMethods()).filter(this::hasRouteAnnotation).toList()){
            this.addRoute(getRouteData(routable, method));
        }
    }

    private void addRoute(RouteData routeData){
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
