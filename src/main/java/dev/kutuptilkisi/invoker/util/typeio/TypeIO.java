package dev.kutuptilkisi.invoker.util.typeio;

import io.netty.buffer.ByteBuf;

public interface TypeIO {
    boolean fromClass(Object obj);
    Object read(ByteBuf byteBuf);

    void write(ByteBuf byteBuf, Object arg);
}
