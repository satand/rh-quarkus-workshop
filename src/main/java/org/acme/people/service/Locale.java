package org.acme.people.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.inject.Qualifier;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.PARAMETER})
public @interface Locale { 

    Language value();

    enum Language {
        EN,
        IT,
        ES;
    }

    @SuppressWarnings("all")
    class Literal extends AnnotationLiteral<Locale> implements Locale {

        private final Language language;

        public Literal(Language language) {
            this.language = language;
        }

        @Override
        public Language value() {
            return language;
        }

        public static Literal of(Language language) {
            return new Literal(language);
        }
    }
}

