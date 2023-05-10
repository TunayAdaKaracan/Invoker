package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PacketSender extends Thread {
    @Override
    public void run() {
        NetHandler handler = Invoker.getInstance().getNetHandler();
        while(handler.isRunning()){
            for(Map.Entry<Client, List<Packet>> packetsToSend : handler.getPacketQueue().entrySet()){
                Client client = packetsToSend.getKey();
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(client.getSocket().getOutputStream());
                    for(Packet packet : packetsToSend.getValue()){
                        packet.write(dataOutputStream);
                    }
                    dataOutputStream.flush();
                    dataOutputStream.close();
                } catch (IOException e) {
                    client.close();
                }
                handler.getPacketQueue().remove(client);
            }
        }
    }
}
