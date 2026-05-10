package org.acme.people.service;

public enum Language {
    UNKNOWN,
    EN,
    IT,
    ES;

    public static Language fromString(String name) {
        if (name == null) {
            return UNKNOWN;
        }

        try {
            return Language.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
