package dev.kutuptilkisi.invoker.util.typeio.impl;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VoidTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return false; // TODO
    }

    @Override
    public Object read(DataInputStream dataInputStream) throws IOException {
        return null;
    }

    @Override
    public void write(DataOutputStream dataOutputStream, Object arg) throws IOException {

    }
}
