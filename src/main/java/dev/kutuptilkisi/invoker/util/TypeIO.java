package dev.kutuptilkisi.invoker.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface TypeIO {
    boolean fromClass(Object obj);
    Object read(DataInputStream dataInputStream) throws IOException;

    void write(DataOutputStream dataOutputStream, Object arg) throws IOException;
}
