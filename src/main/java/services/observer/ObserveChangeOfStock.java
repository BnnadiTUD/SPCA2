package services.observer;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObserveChangeOfStock implements StockObserver {

    private static final Logger LOGGER = Logger.getLogger(ObserveChangeOfStock.class.getName());
    private static final int MAX_EVENTS = 50;
    private final List<StockEvent> eventHistory = new ArrayList<>();

    @Override
    public synchronized void update(StockEvent event) {
        eventHistory.add(0, event);
        if (eventHistory.size() > MAX_EVENTS) {
            eventHistory.remove(eventHistory.size() - 1);
        }

        LOGGER.info("STOCK EVENT -> type=" + event.getEventType()
                + ", itemId=" + event.getItemId()
                + ", title=" + event.getItemTitle()
                + ", oldStock=" + event.getOldStock()
                + ", newStock=" + event.getNewStock());
    }

    public synchronized List<StockEvent> getRecentEvents() {
        return List.copyOf(eventHistory);
    }
}
