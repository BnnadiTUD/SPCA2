package services.observer;

import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObserveLowStock implements StockObserver {

    private static final Logger LOGGER = Logger.getLogger(ObserveLowStock.class.getName());
    private static final int LOW_STOCK_THRESHOLD = 3;

    @Override
    public void update(StockEvent event) {
        if (event.getNewStock() <= LOW_STOCK_THRESHOLD) {
            LOGGER.warning("LOW STOCK ALERT -> Item: " + event.getItemTitle()
                    + " (id=" + event.getItemId() + ") now has stock " + event.getNewStock());
        }
    }
}