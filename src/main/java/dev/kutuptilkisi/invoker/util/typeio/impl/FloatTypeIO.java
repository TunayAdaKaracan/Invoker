package dev.kutuptilkisi.invoker.util.typeio.impl;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;
import io.netty.buffer.ByteBuf;

public class FloatTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return obj instanceof Float;
    }

    @Override
    public Object read(ByteBuf dataInputStream) {
        return dataInputStream.readFloat();
    }

    @Override
    public void write(ByteBuf dataOutputStream, Object arg) {
        dataOutputStream.writeFloat((Float) arg);
    }
}
