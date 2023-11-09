package dev.kutuptilkisi.invoker.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ByteBufUtil {
    public static void writeString(ByteBuf byteBuf, String text){
        byteBuf.writeInt(text.length());
        byteBuf.writeCharSequence(text, StandardCharsets.UTF_8);
    }

    public static String readString(ByteBuf byteBuf){
        return byteBuf.readCharSequence(byteBuf.readInt(), StandardCharsets.UTF_8).toString();
    }

    public static void writeUUID(ByteBuf byteBuf, UUID uuid){
        byteBuf.writeLong(uuid.getMostSignificantBits());
        byteBuf.writeLong(uuid.getLeastSignificantBits());
    }

    public static UUID readUUID(ByteBuf byteBuf){
        return new UUID(byteBuf.readLong(), byteBuf.readLong());
    }
}
