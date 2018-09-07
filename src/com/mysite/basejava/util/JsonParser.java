package com.mysite.basejava.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysite.basejava.model.Content;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {

    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Content.class, new JsonContentAdapter())
            .create();

    public static <T> T read(final Reader reader, final Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> T read(final String string, final Class<T> clazz) {
        return GSON.fromJson(string, clazz);
    }

    public static <T> void write(final T object, final Writer writer) {
        GSON.toJson(object, writer);
    }

    public static <T> String write(final T object, final Class<T> clazz) {
        return GSON.toJson(object, clazz);
    }

}
