package services.observer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObserveLowStock implements StockObserver {

    private static final Logger LOGGER = Logger.getLogger(ObserveLowStock.class.getName());
    private static final int LOW_STOCK_THRESHOLD = 3;
    private final Map<Long, StockEvent> lowStockAlerts = new ConcurrentHashMap<>();

    @Override
    public void update(StockEvent event) {
        if (event.getNewStock() <= LOW_STOCK_THRESHOLD) {
            lowStockAlerts.put(event.getItemId(), event);
            LOGGER.warning("LOW STOCK ALERT -> Item: " + event.getItemTitle()
                    + " (id=" + event.getItemId() + ") now has stock " + event.getNewStock());
            return;
        }

        lowStockAlerts.remove(event.getItemId());
    }

    public List<StockEvent> getActiveAlerts() {
        return lowStockAlerts.values().stream()
                .sorted(Comparator.comparing(StockEvent::getItemTitle, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
