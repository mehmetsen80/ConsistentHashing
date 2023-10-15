package org.consistent.hashing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalNode implements Node{
    private  String dataCenter;
    private  String ipAddress;
    private  int portNumber;

    @Override
    public String getKey() {
        return dataCenter + "-" + ipAddress + ":" + portNumber;
    }
}
