package dev.kutuptilkisi.invoker.instance;

import dev.kutuptilkisi.invoker.util.typeio.TypeIO;
import dev.kutuptilkisi.invoker.util.typeio.impl.*;

public enum Types {
    STRING(new StringTypeIO(), 'S'),
    INT(new IntTypeIO(), 'I'),
    DOUBLE(new DoubleTypeIO(), 'D'),
    FLOAT(new FloatTypeIO(), 'F'),
    LONG(new LongTypeIO(), 'L'),
    BYTE(new ByteTypeIO(), 'B'),
    VOID(new VoidTypeIO(), 'V');

    private final TypeIO typeIO;
    private final char representation;
    Types(TypeIO typeIO, char repr){
        this.typeIO = typeIO;
        this.representation = repr;
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
