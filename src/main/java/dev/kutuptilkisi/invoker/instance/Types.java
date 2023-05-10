package dev.kutuptilkisi.invoker.instance;

import dev.kutuptilkisi.invoker.util.TypeIO;
import dev.kutuptilkisi.invoker.util.impl.*;

import java.io.DataInputStream;
import java.io.IOException;

public enum Types {
    STRING(new StringTypeIO(), 'S'),
    INT(new IntTypeIO(), 'I'),
    DOUBLE(new DoubleTypeIO(), 'D'),
    FLOAT(new FloatTypeIO(), 'F'),
    LONG(new LongTypeIO(), 'L');

    private final TypeIO typeIO;
    private final char representation;
    Types(TypeIO typeIO, char repr){
        this.typeIO = typeIO;
        this.representation = repr;
    }

    public Object readFrom(DataInputStream dis) throws IOException {
        return typeIO.read(dis);
    }

    public char getRepresentation() {
        return representation;
    }

    public TypeIO getTypeIO() {
        return typeIO;
    }

    public static Types fromCharacter(char c){
        for(Types type : Types.values()){
            if(type.representation == c){
                return type;
            }
        }
        return null;
    }

    public static Types fromObject(Object object){
        for(Types type : Types.values()){
            if(type.typeIO.fromClass(object)){
                return type;
            }
        }
        return null;
    }
}
