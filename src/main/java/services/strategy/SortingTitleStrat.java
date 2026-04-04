package services.strategy;


public class SortingTitleStrat implements ItemSortStrategy {

	    @Override
	    public String getSortField() {
	        return "stock";
	    }
	}
