package leskin.udacity.findoutfirst.model.enums;

/**
 * Created by Oleg Leskin on 23.05.2017.
 */

public enum SortingOfArticles {
    TOP("top"),
    LATEST("latest"),
    POPULAR("popular");

    private String value;

    SortingOfArticles(String value) {
        this.value = value;
    }

    public static SortingOfArticles getByValue(String value) {
        if (value.equals(TOP.toString()))
            return TOP;
        else if (value.equals(LATEST.toString()))
            return LATEST;
        else return POPULAR;
    }

    @Override
    public String toString() {
        return value;
    }
}
