package dev.kutuptilkisi.invoker.util.typeio.impl;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;
import io.netty.buffer.ByteBuf;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StringTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return obj instanceof String;
    }

    @Override
    public Object read(ByteBuf dataInputStream){
        return dataInputStream.readCharSequence(dataInputStream.readInt(), StandardCharsets.UTF_8).toString();
    }

    @Override
    public void write(ByteBuf dataOutputStream, Object arg){
        dataOutputStream.writeCharSequence((String) arg, StandardCharsets.UTF_8);
    }
}
