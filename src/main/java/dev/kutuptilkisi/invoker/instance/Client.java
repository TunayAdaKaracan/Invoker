package dev.kutuptilkisi.invoker.instance;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.instance.intents.ClientIntents;
import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class Client {
    private final Socket socket;
    private final UUID uuid;
    private volatile boolean isConnected;

    private boolean isAuthorized;
    private final ClientIntents clientIntents;

    public Client(Socket socket){
        this.socket = socket;
        this.isConnected = true;
        this.isAuthorized = false;

        this.clientIntents = ClientIntents.defaultPackets();
        this.uuid = UUID.randomUUID();
    }

    public Socket getSocket() {
        return socket;
    }

    public UUID getClientUUID() {
        return this.uuid;
    }

    public ClientIntents getClientIntents(){
        return clientIntents;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(){
        this.isAuthorized = true;
    }

    public void send(Packet packet){
        packet.send(this);
    }

    public void close(){
        this.isConnected = false;
        try {
            Invoker.invokerAPI.getNetHandler().removeClient(this);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
