package services.strategy;

public class SortingManufacturerStrat implements ItemSortStrategy {

	    @Override
	    public String getSortField() {
	        return "manufacturer";
	    }
	}
