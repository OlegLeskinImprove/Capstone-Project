package leskin.udacity.findoutfirst.model.enums;

/**
 * Created by Oleg Leskin on 23.05.2017.
 */

public enum LanguageOfNewsSources {
    ENGLISH("en"),
    FRENCH("fr"),
    GERMAN("de");

    private String value;

    LanguageOfNewsSources(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
