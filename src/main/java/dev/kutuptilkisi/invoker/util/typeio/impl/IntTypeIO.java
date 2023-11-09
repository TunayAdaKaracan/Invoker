package dev.kutuptilkisi.invoker.util.typeio.impl;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;
import io.netty.buffer.ByteBuf;

public class IntTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return obj instanceof Integer;
    }

    @Override
    public Object read(ByteBuf dataInputStream) {
        return dataInputStream.readInt();
    }

    @Override
    public void write(ByteBuf dataOutputStream, Object arg) {
        dataOutputStream.writeInt((Integer) arg);
    }
}
