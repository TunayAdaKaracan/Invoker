package dev.kutuptilkisi.invoker.util.impl;

import dev.kutuptilkisi.invoker.util.TypeIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteTypeIO implements TypeIO {
    @Override
    public boolean fromClass(Object obj) {
        return obj instanceof Byte || obj instanceof Boolean;
    }

    @Override
    public Object read(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readByte();
    }

    @Override
    public void write(DataOutputStream dataOutputStream, Object arg) throws IOException {
        byte toWrite;
        if(arg instanceof Boolean){
            toWrite = (boolean) arg ? (byte) 1 : (byte) 0;
        } else {
            toWrite = (byte) arg;
        }
        dataOutputStream.writeByte(toWrite);
    }
}
