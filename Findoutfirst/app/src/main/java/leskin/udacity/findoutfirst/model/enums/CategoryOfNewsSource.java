package leskin.udacity.findoutfirst.model.enums;

/**
 * Created by Oleg Leskin on 23.05.2017.
 */

public enum CategoryOfNewsSource {
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GAMING("gaming"),
    GENERAL("general"),
    MUSIC("music"),
    POLITICS("politics"),
    SCIENCE_AND_NATURE("science-and-nature"),
    SPORT("sport"),
    TECHNOLOGY("technology");

    private String value;

    CategoryOfNewsSource(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
