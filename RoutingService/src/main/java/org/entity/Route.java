package org.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Route implements RouteComponent{
    private BigDecimal timeTaken;
    private List<RouteComponent> routeComponents = new ArrayList<>();

    public Route() {
    }

    public Route(Route auxRoute) {
        for (RouteComponent component : auxRoute.routeComponents) {
            this.routeComponents.add(component);
        }
    }

    public void addRouteComponent(RouteComponent routeComponent) {
        routeComponents.add(routeComponent);
    }
    public void removeRouteComponent(RouteComponent routeComponent) {
        routeComponents.remove(routeComponent);
    }

    public void setTimeTaken(BigDecimal timeTaken) {
        this.timeTaken = timeTaken;
    }

    public BigDecimal getTimeTaken() {
        return timeTaken;
    }

    @Override
    public void displayRouteDetails() {
        for (RouteComponent component : routeComponents) {
            component.displayRouteDetails();
        }
    }
}
