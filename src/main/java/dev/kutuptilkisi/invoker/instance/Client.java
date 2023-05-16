package dev.kutuptilkisi.invoker.instance;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.Counter;

import java.io.IOException;
import java.net.Socket;
public class Client {
    //TODO: Change ClientID system
    private static int CLIENTID = 0;

    private final Socket socket;
    private final int clientID;
    private volatile boolean isConnected;

    private boolean isAuthorized;

    private final Counter routeRequestCounter;

    public Client(Socket socket){
        this.socket = socket;
        this.clientID = CLIENTID;
        CLIENTID++;
        this.isConnected = true;
        this.isAuthorized = false;

        this.routeRequestCounter = new Counter();
    }

    public Socket getSocket() {
        return socket;
    }

    public int getClientID() {
        return clientID;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public Counter getRouteRequestCounter() {
        return routeRequestCounter;
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
            Invoker.getInstance().getNetHandler().removeClient(this);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
