package services.observer;

public interface InventorySubject {
    void notifyObservers(StockEvent event);
}
