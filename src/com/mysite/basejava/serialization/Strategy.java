package com.mysite.basejava.serialization;

import com.mysite.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Strategy {

    void toWrite(final Resume resume, final OutputStream outputStream) throws IOException;

    Resume toRead(final InputStream inputStream) throws IOException;

}
