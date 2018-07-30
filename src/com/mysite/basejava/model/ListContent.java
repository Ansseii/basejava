package com.mysite.basejava.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ListContent implements Content, Serializable {

    private static final long serialVersionUID = 1L;

    private final List<String> content;

    public ListContent(final String... element) {
        content = new LinkedList<>(Arrays.asList(element));
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
        StringBuilder builder = new StringBuilder();
        for (String string : content) {
            builder.append("\n")
                    .append("--")
                    .append(string);
        }
        return builder.toString();
    }
}
