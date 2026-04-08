package services.observer;

public class StockEvent {

    private final Long itemId;
    private final String itemTitle;
    private final int oldStock;
    private final int newStock;
    private final StockEventType eventType;

    public StockEvent(Long itemId, String itemTitle, int oldStock, int newStock, StockEventType eventType) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.oldStock = oldStock;
        this.newStock = newStock;
        this.eventType = eventType;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public int getOldStock() {
        return oldStock;
    }

    public int getNewStock() {
        return newStock;
    }

    public StockEventType getEventType() {
        return eventType;
    }
}