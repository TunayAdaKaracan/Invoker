package dev.kutuptilkisi.invoker.util.typeio.impl;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;
import io.netty.buffer.ByteBuf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VoidTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return false; // TODO
    }

    @Override
    public Object read(ByteBuf dataInputStream) {
        return null;
    }

    @Override
    public void write(ByteBuf dataOutputStream, Object arg) {

    }
}
