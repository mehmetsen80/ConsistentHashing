package org.consistent.hashing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VirtualNode<T extends Node> implements Node{
    T serverNode;
    Integer index;

    @Override
    public String getKey() {
        return serverNode.getKey() + "-" + index;
    }

    public boolean isVirtualNodeOf(T node){
        return serverNode.getKey().equals(node.getKey());
    }
}
