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

    @Override
    public String toString() {
        return value;
    }
}
