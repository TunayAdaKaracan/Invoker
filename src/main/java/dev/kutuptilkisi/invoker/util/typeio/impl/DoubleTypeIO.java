package dev.kutuptilkisi.invoker.util.typeio.impl;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;
import io.netty.buffer.ByteBuf;

public class DoubleTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return obj instanceof Double;
    }

    @Override
    public Object read(ByteBuf dataInputStream) {
        return dataInputStream.readDouble();
    }

    @Override
    public void write(ByteBuf dataOutputStream, Object arg) {
        dataOutputStream.writeDouble((Double) arg);
    }
}
