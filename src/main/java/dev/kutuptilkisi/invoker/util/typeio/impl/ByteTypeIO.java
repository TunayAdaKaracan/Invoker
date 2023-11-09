package dev.kutuptilkisi.invoker.util.typeio.impl;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;
import io.netty.buffer.ByteBuf;

public class ByteTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return obj instanceof Byte || obj instanceof Boolean;
    }

    @Override
    public Object read(ByteBuf dataInputStream) {
        return dataInputStream.readByte();
    }

    @Override
    public void write(ByteBuf dataOutputStream, Object arg) {
        byte toWrite;
        if(arg instanceof Boolean){
            toWrite = (boolean) arg ? (byte) 1 : (byte) 0;
        } else {
            toWrite = (byte) arg;
        }
        dataOutputStream.writeByte(toWrite);
    }
}
