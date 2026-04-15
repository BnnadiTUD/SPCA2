package services.observer;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StockInventManager implements InventorySubject {

    @Inject
    ObserveLowStock lowStockObserver;

    @Inject
    ObserveChangeOfStock stockAuditObserver;

    private List<StockObserver> observers;

    @PostConstruct
    void init() {
        observers = new ArrayList<>();
        observers.add(lowStockObserver);
        observers.add(stockAuditObserver);
    }

    @Override
    public void notifyObservers(StockEvent event) {
        for (StockObserver observer : observers) {
            observer.update(event);
        }
    }

    public List<StockEvent> getRecentEvents() {
        return stockAuditObserver.getRecentEvents();
    }

    public List<StockEvent> getLowStockAlerts() {
        return lowStockObserver.getActiveAlerts();
    }
}
