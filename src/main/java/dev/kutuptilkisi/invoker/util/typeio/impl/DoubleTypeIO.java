package dev.kutuptilkisi.invoker.util.typeio.impl;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DoubleTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return obj instanceof Double;
    }

    @Override
    public Object read(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readDouble();
    }

    @Override
    public void write(DataOutputStream dataOutputStream, Object arg) throws IOException {
        dataOutputStream.writeDouble((Double) arg);
    }
}
