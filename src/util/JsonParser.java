package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysite.basejava.model.Content;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {

    private static Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Content.class, new JsonContentAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

}
