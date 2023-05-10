package dev.kutuptilkisi.invoker.util.impl;

import dev.kutuptilkisi.invoker.util.TypeIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return obj instanceof String;
    }

    @Override
    public Object read(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readUTF();
    }

    @Override
    public void write(DataOutputStream dataOutputStream, Object arg) throws IOException {
        dataOutputStream.writeUTF((String) arg);
    }
}
