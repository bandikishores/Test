package com.interview;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Star {
    class ServerNode {
        private List<ClientNode> connections = new ArrayList<ClientNode>();
        private String           ipAddr      = null;

        public ServerNode(String ipAddr) {
            if (!connections.contains(ipAddr)) {
                this.ipAddr = ipAddr.substring(0, ipAddr.length() - 1) + IPAddrCounter;
            } else {
                this.ipAddr = ipAddr.substring(0, ipAddr.length() - 1) + IPAddrCounter + 1;
            }
            IPAddrCounter++;
        }

        public void getConnections() {
            for (ClientNode client : connections) {
                System.out.print("  " + client.getIpAddr());
                System.out.println(" "
                        + client.getIpAddr().substring(client.getIpAddr().length() - 1, client.getIpAddr().length()));
            }
        }

        public int getNumConnections() {
            return connections.size();
        }

        public void addClient(String ipAddr) {
            String clientAddr;
            if (!connections.contains(ipAddr)) {
                clientAddr = ipAddr.substring(0, ipAddr.length() - 1) + IPAddrCounter;
            } else {
                clientAddr = ipAddr.substring(0, ipAddr.length() - 1) + IPAddrCounter + 1;
            }
            ClientNode newClient = new ClientNode(clientAddr);
            this.connections.add(newClient);
            IPAddrCounter++;
        }

        public String getIpAddr() {
            return this.ipAddr;
        }
    }

    class ClientNode {
        public ServerNode server = null;
        private String    ipAddr = null;

        public ClientNode(String ipAddr) {
            this.ipAddr = ipAddr;
        }

        public ClientNode(String ipAddr, ServerNode server) {
            if (!srv.connections.contains(ipAddr)) {
                this.ipAddr = ipAddr.substring(0, ipAddr.length() - 1) + IPAddrCounter;
            } else {
                this.ipAddr = ipAddr.substring(0, ipAddr.length() - 1) + IPAddrCounter + 1;
            }
            this.server = server;
            IPAddrCounter++;
        }

        public String getIpAddr() {
            return this.ipAddr;
        }
    }

    private ServerNode srv;
    public static int  IPAddrCounter = 1;

    public Star(String networkAddr) {
        srv = new ServerNode(networkAddr);
    }

    public void insertNode(String networkAddr) {
        srv.addClient(networkAddr);
    }

    public void deleteNode(String clientAddr) {
        boolean isFound = false;
        for (Iterator<ClientNode> iterator = srv.connections.iterator(); iterator.hasNext();) {
            ClientNode client = iterator.next();
            if (Objects.equals(client.getIpAddr(), clientAddr)) {
                isFound = true;
                iterator.remove();
                break;
            }
        }
        
        if (!isFound) {
            System.out.println("Provided address is not found!");
            System.exit(0);
        }
    }

    public void display() {
        System.out.println("Server " + srv.getIpAddr() + " has the following connections:");
        System.out.println("There are " + srv.getNumConnections() + " clients connected.");
        System.out.println("Connected clients:");
        srv.getConnections();
    }

    public static void main(String[] args) {

        // Create network
        Star starNetwork = new Star("10.10.10.0");

        starNetwork.display();
        System.out.println();

        starNetwork.insertNode("10.10.10.0");
        starNetwork.insertNode("10.10.10.0");
        starNetwork.insertNode("10.10.10.0");

        starNetwork.display();
        System.out.println();

        starNetwork.deleteNode("10.10.10.3");

        starNetwork.display();
        System.out.println();
    }
}
