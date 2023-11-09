package dev.kutuptilkisi.invoker.util.typeio.impl;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;
import io.netty.buffer.ByteBuf;

public class LongTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return obj instanceof Long;
    }

    @Override
    public Object read(ByteBuf dataInputStream) {
        return dataInputStream.readLong();
    }

    @Override
    public void write(ByteBuf dataOutputStream, Object arg) {
        dataOutputStream.writeLong((Long) arg);
    }
}
