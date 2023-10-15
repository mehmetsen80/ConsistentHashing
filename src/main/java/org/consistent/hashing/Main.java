package org.consistent.hashing;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //initialize 4 service node
        ServerNode node1 = new ServerNode("New York","127.0.0.1",8080);
        ServerNode node2 = new ServerNode("Houston","127.0.0.2",8081);
        ServerNode node3 = new ServerNode("Oregon","127.0.0.3",8082);
        ServerNode node4 = new ServerNode("Chicago","127.0.0.4",8084);

        ConsistentHashing<ServerNode> consistentHashing = new ConsistentHashing<>(Arrays.asList(node1, node2, node3, node4));
        System.out.println("Let's add request ip addresses");
        String[] ips = {"192.5.1.1", "172.8.1.2", "111.1.9.13", "122.0.5.6", "199.25.4.7", "133.6.5.2", "194.7.8.9"};
        consistentHashing.route(ips);

        System.out.println("Let's add another server node Denver");
        ServerNode node5 = new ServerNode("Denver","127.0.0.1",8085);
        consistentHashing.addNode(node5);
        consistentHashing.route(ips);

        System.out.println("Let's add more request ips");
        ArrayUtils.addAll(ips, "102.0.1.2", "189.7.1.0", "192.5.1.10");
        consistentHashing.route(ips);

        System.out.println("Let's remove one of the nodes");
        consistentHashing.removeNode(node4);
        consistentHashing.route(ips);
    }



}