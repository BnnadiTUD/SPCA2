package services.strategy;

public class SortingStockStrat implements ItemSortStrategy {

	    @Override
	    public String getSortField() {
	        return "title";
	    }
	}
