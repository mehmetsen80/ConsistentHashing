package org.consistent.hashing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServerNode implements Node{
    private  String dataCenter;
    private  String ipAddress;
    private  Integer portNumber;

    public ServerNode(String dataCenter){
        this.dataCenter = dataCenter;
    }

    @Override
    public String getKey() {
        return StringUtils.isNotEmpty(ipAddress) && !Objects.isNull(portNumber)   ?
                dataCenter + "-" + ipAddress + ":" + portNumber :
                dataCenter;
    }

    @Override
    public String toString(){
        return getKey();
    }
}
