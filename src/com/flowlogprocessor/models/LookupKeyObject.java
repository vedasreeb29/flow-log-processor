package com.flowlogprocessor.models;

import java.util.Objects;

public class LookupKeyObject {
    int destinationPort;
    String protocol;

    public LookupKeyObject(int destinationPort, String protocol) {
        this.destinationPort = destinationPort;
        this.protocol = protocol;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(int destinationPort) {
        this.destinationPort = destinationPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LookupKeyObject that = (LookupKeyObject) obj;
        // We use equalsIgnoreCase to handle cases where protocol names are capitalized
        return destinationPort == that.destinationPort && protocol.equalsIgnoreCase(that.protocol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationPort, protocol);
    }

    @Override
    public String toString() {
        return "LookupKeyObject{" +
                "destinationPort=" + destinationPort +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
