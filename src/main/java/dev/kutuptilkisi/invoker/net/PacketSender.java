package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PacketSender extends Thread {
    @Override
    public void run() {
        NetHandler handler = Invoker.getInstance().getNetHandler();
        while(handler.isRunning()){
            if(handler.getPacketQueue().size() != 0){
                for(Map.Entry<Client, List<Packet>> packetsToSend : handler.getPacketQueue().entrySet()){
                    Client client = packetsToSend.getKey();
                    try {
                        DataOutputStream dataOutputStream = new DataOutputStream(client.getSocket().getOutputStream());
                        for(Packet packet : packetsToSend.getValue()){
                            if(client.getClientIntents().isEnabled(packet.packetID())){
                                Logger.info("Sending packet "+ packet.packetID() +" to client "+client.getClientID());
                                packet.write(dataOutputStream);
                            }else {
                                Logger.info("Skipped packet of "+packet.packetID()+" as it is not in client's intents.");
                            }
                        }
                        dataOutputStream.flush();
                    } catch (IOException e) {
                        Logger.warning("Error on PacketSender: "+client.getClientID());
                        client.close();
                    }
                }
                handler.getPacketQueue().clear();
            }
        }
    }
}
