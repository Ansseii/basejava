package com.mysite.basejava.model;

public enum ContactType {

    MOBILE("Тел.: "),
    SKYPE("Skype: "),
    MAIL("Почта: "),
    LINKED_IN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOWERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}



