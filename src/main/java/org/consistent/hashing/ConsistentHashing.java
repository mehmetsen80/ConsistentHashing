package org.consistent.hashing;

import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;


public class ConsistentHashing<T extends Node> {

    private final static int VIRTUAL_NODE_COUNT = 20;
    SortedMap<Long, VirtualNode<T>> ring = new TreeMap<>();

    public ConsistentHashing(Collection<T> collection){
        collection.forEach(this::addNode);
    }

    public void addNode(T node){
        Integer latestIndex = getLatestIndex(node);
        for(int i = 0; i < VIRTUAL_NODE_COUNT; i++){
            VirtualNode<T> virtualNode = new VirtualNode<>(node, latestIndex + i);
            Long hash =  new BigInteger(DigestUtils.md5(virtualNode.getKey())).longValue();
            ring.put(hash, virtualNode);
        }
    }

    private Integer getLatestIndex(T node){
        int numOfReplicas = 0;
        for(VirtualNode<T> virtualNode: ring.values()){
            if(virtualNode.isVirtualNodeOf(node)){
                numOfReplicas++;
            }
        }
        return numOfReplicas;
    }

    public void removeNode(T node){
        Iterator<Long> it = ring.keySet().iterator();
        while(it.hasNext()){
            Long hash = it.next();
            VirtualNode<T> virtualNode = ring.get(hash);
            if(virtualNode.isVirtualNodeOf(node)){
                it.remove();
            }
        }
    }

    private T route(String key){
        if(ring.isEmpty()) return null;
        Long hash = new BigInteger(DigestUtils.md5(key)).longValue();
        SortedMap<Long, VirtualNode<T>> tailMap = ring.tailMap(hash);
        Long nodeHashValue = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        return ring.get(nodeHashValue).getServerNode();
    }

    public  void route(String ... requestIps){
        for(String requestIp: requestIps){
            System.out.println(requestIp + " is route to " + route(requestIp));
        }
    }
}
