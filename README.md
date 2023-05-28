# Invoker
Invoker is a remote communication library for bukkit and softwares. Invoker uses sockets to communicate with plugin on server side. Invoker is highly developer friendly and is easy to use as well as easy to extend and add new features such as new packets to plugin.

## How to use? (Docs)

##### Routables:
Routables are the essential and most important part of the Invoker. You can add "route"s to classes that inherits routable interface. A route is a method that can be called by a client connected to socket server.

```java
    public class PlayerRoute implements Routable {
        @Override
        public String getIdentifier() {
            return "kutup"; // This identifiers are mixed with route names. I.E: kutup_echo
        }
    
        @Route(route = "isActive")
        public boolean isActive(Client client, String username){
            return Bukkit.getPlayer(username) != null;
        }
    
        @Route(route = "echo")
        public void echo(Client client, String message){
            Bukkit.getLogger().info("ECHO: "+message);
        }
    }
```
You can register a route to invoker by simply doing:
```java
Invoker.getInstance().getRouteManager().addRoute(new PlayerRoute());
```

Example usage from python-invoker:
```python
from network import *
import time
net = Network("localhost", 8080, "kutup is awesome") // HOST, PORT, AUTH KEY
net.start()

while True:
    cmd = input("> ")
    if cmd == "active":
        net.request("kutup_isActive", lambda v: print(v), "Kutup_Tilkisi")
    elif cmd == "echo":
        net.request("kutup_echo", lambda v: print(v), "Hi!")
    elif cmd == "close":
        break

net.client.close()
```

Invoker returns a void if there are no return types. (None in this example). Both Invoker and wrappers are **multithreaded** which means they **do not wait** for something to happen. 

##### Packets
Packets are heart of the Invoker. Everything is made through packets. But sometimes you would want to send a packet to client. But we don't include a server to application route management. So how do you do it?
With your own packets of course!

**Incoming Packets**:
Lets say you want to make a BanPlayerPacket and for some reason you don't want to use a route for that. This is how you can create a packet:
```java
public class BanPlayerPacket implements Packet { // Every 

    private String userName;

    @Override
    public int packetID() {
        return 0x10; // Must be an unique packet ID
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        userName = dis.readUTF();
    }
}
```
depending on packets bound socket, you have to override either read or write packet. Packets can also be both way packets but... we don't prefer it.

Ok lets get to other step. How would you listen for this packet?
We have an internal structure called packet factory. Which takes a packet id and returns a packet instance.
You need to register your packet to that class.
This is how it is done:
```java
// Incoming packets must be registered to packet factory
PacketFactory.registerPacket(new BanPlayerPacket().packetID(), BanPlayerPacket::new); 
```
This is actually bad practice to create an empty packet to just get the id. But who cares? It is easier to show.

Wait... but why add a packet if i can't trigger an event when it is sent?
Well... Here we go again. We have another structure called EventRegistry. Which fires events based on incoming packet.
This is what it looks like in our code:
```java
public class BanPacketListener {

    private final TestPlugin testPlugin;
    public BanPacketListener(TestPlugin testPlugin){
        this.testPlugin = testPlugin;
    }

    public void onBanPacket(Client client, BanPlayerPacket banPlayerPacket){
        // Events are called async. You need to use #runTask in order to use any operation that must be done in sync.
        Bukkit.getScheduler().runTask(testPlugin, () -> {
            Player player = Bukkit.getPlayer(banPlayerPacket.getUserName());
            if(player != null){
                Bukkit.getBanList(BanList.Type.NAME).addBan(banPlayerPacket.getUserName(), null, null, null);
                player.kickPlayer("You are banned from server");
            }
        });
    }
}
```
Yeah yeah, i know. I can hear you guys saying "Why there is no annotation or an inherited interface?". Well, i just copied this EventRegistry from one of my old projects (Fix is soon).
Now the last step is to register this Custom Packet Listener.
```java
EventRegistry.registerEvent(new BanPacketListener(this));
``` 

**Outgoing Packets**
Outgoing packets are actually a lot easier as you don't have to put them into listeners or something else.
Let' say you want to send a chat message player sent to a connected application. 
As usual, we will first create our own Packet.

```java
public class ChatPacket implements Packet {

    private String userName;
    private String message;

    @Override
    public int packetID() {
        return 0x09; // Must be an unique packet ID
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos); // This is a must
        dos.writeUTF(userName);
        dos.writeUTF(message);
    }
}
```
**IMPORTANT**: You have to put `Packet.super.write(dos);`. This basically writes the packet id to DataOutputStream hence it is a must.

Then ofcourse we want to send the packet, aren't we?
In this example you will also see all ways to send a packet to a client.
```java
public class PlayerChatListener implements Listener {

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent playerChatEvent){
        ChatPacket chatPacket = new ChatPacket();
        chatPacket.setUserName(playerChatEvent.getPlayer().getDisplayName());
        chatPacket.setMessage(playerChatEvent.getMessage());

        // There are some ways to send a packet to client(s)

        // Packet#broadcast
        chatPacket.broadcast();

        // Nethandler#broadcast
        Invoker.getInstance().getNetHandler().broadcastPacket(chatPacket);

        // Send to a client
        Client client = Invoker.getInstance().getNetHandler().getClient(0); // Get first connected client
        if(client != null){
            // Client#send
            client.send(chatPacket);

            // Packet#send
            chatPacket.send(client);

            // NetHandler#sendPacket
            Invoker.getInstance().getNetHandler().sendPacket(client, chatPacket);
        }
    }
}
```
Aaaand we just sent it. Cool right?

This is why invoker is actually a powerfull tool to use.

### Todo:

 - [ ] Fix Protocol
 - [ ] Add Websocket Support
 - [ ] Create more official wrappers
 - [ ] Fix EventRegistry


## Official Wrappers (More soon)
|Name|Language|Link|
|--|--|--|
|python-invoker|Python|https://github.com/TunayAdaKaracan/python-invoker|

