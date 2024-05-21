package org.notification;

import org.entity.Order;

public interface Observer {
    void update(Order order);
}
