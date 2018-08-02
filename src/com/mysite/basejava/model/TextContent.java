package com.mysite.basejava.model;

import java.util.Objects;

public class TextContent extends Content {

    private static final long serialVersionUID = 1L;

    private String content;

    public TextContent() {
    }

    public TextContent(final String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextContent that = (TextContent) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

    @Override
    public String toString() {
        return content;
    }
}
