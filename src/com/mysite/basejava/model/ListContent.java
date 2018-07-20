package com.mysite.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListContent implements Content {

    private final List<String> content;

    public ListContent(final List<String> content) {
        Objects.requireNonNull(content, "Content must not be null");
        this.content = content;
    }

    public List<String> getContent() {
        return content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListContent that = (ListContent) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
