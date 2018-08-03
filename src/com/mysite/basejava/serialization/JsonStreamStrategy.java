package com.mysite.basejava.serialization;

import com.mysite.basejava.model.Resume;
import util.JsonParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonStreamStrategy implements Strategy {

    @Override
    public void toWrite(final Resume resume, final OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            JsonParser.write(resume, writer);
        }
    }

    @Override
    public Resume toRead(final InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return JsonParser.read(reader, Resume.class);
        }
    }
}
