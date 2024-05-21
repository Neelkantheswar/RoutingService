package org.notification;

import org.entity.Order;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver(Order order);
}
