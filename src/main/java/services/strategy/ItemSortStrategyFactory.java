package services.strategy;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

@ApplicationScoped
public class ItemSortStrategyFactory {

    @Inject
    Instance<ItemSortStrategy> availableStrategies;

    private Map<String, ItemSortStrategy> strategyRegistry;

    @PostConstruct
    void init() {
        Map<String, ItemSortStrategy> registry = new LinkedHashMap<>();

        for (ItemSortStrategy strategy : availableStrategies) {
            registry.put(strategy.getSortKey(), strategy);
        }

        strategyRegistry = Collections.unmodifiableMap(registry);
    }

    public ItemSortStrategy getStrategy(String sortBy) {
        if (sortBy == null || sortBy.isBlank()) {
            return null;
        }

        return strategyRegistry.get(sortBy.toLowerCase());
    }
}
