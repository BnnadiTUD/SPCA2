package services.observer;

import java.util.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ObserveChangeOfStock implements StockObserver {

    private static final Logger LOGGER = Logger.getLogger(ObserveChangeOfStock.class.getName());

    @Override
    public void update(StockEvent event) {
        LOGGER.info("STOCK EVENT -> type=" + event.getEventType()
                + ", itemId=" + event.getItemId()
                + ", title=" + event.getItemTitle()
                + ", oldStock=" + event.getOldStock()
                + ", newStock=" + event.getNewStock());
    }
}
