package dev.kutuptilkisi.invoker.router.impl;

import dev.kutuptilkisi.invoker.instance.Types;
import dev.kutuptilkisi.invoker.router.Routable;
import dev.kutuptilkisi.invoker.router.Route;
import dev.kutuptilkisi.invoker.util.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RouteData {

    private final Route route;
    private final Routable routable;
    private final Method method;

    public RouteData(Route route, Routable objec, Method method){
        this.route = route;
        this.routable = objec;
        this.method = method;
    }

    public String getName(){
        return this.route.route();
    }

    public String getFullName(){
        return this.routable.getIdentifier()+"_"+this.route.route();
    }

    public Pair<Types, Object> executeRoute(Object[] args) throws InvocationTargetException, IllegalAccessException {
        this.method.setAccessible(true);
        Object ret = this.method.invoke(this.routable, args);
        return new Pair<>(Types.fromObject(ret), ret);
    }
}
