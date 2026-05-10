package org.acme.people.service;

import jakarta.enterprise.util.AnnotationLiteral;

@SuppressWarnings("all")
public class LocaleLiteral extends AnnotationLiteral<Locale> implements Locale {

    private final Language language;

    public LocaleLiteral(Language language) {
        this.language = language;
    }

    @Override
    public Language value() {
        return language;
    }

    public static LocaleLiteral of(Language language) {
        return new LocaleLiteral(language);
    }
}
