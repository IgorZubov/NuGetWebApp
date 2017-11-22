package com.zubov.i.tests.utils;

import java.io.IOException;
import java.io.InputStream;

public class UnDisposableStream extends InputStream {

    private final String _path;
    private InputStream stream;

    public UnDisposableStream(String path){
        _path = path;
        openResources();
    }

    private void openResources() {
        ClassLoader classLoader = getClass().getClassLoader();
        this.stream = classLoader.getResourceAsStream(_path);
    }

    public void realClose() throws IOException {
        this.stream.close();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return this.stream.read(b, off, len);
    }

    @Override
    public int read() throws IOException {
        return this.stream.read();
    }

    @Override
    public void close() throws IOException {
        //do nothing except reopen
        this.stream.close();
        openResources();
    }
}
