package com.flowlogprocessor.models;

public class FlowLogRecord {
    int version;
    String accountId;
    String interfaceId;
    String sourceAddress;
    String destinationAddress;
    int sourcePort;
    int destinationPort;
    int protocol;
    int packets;
    int bytes;
    int startTime;
    int endTime;
    String action;
    String logStatus;

    public FlowLogRecord(int version, String accountId, String interfaceId, String sourceAddress,
                         String destinationAddress, int sourcePort, int destinationPort, int protocol,
                         int packets, int bytes, int startTime, int endTime, String action, String logStatus) {
        this.version = version;
        this.accountId = accountId;
        this.interfaceId = interfaceId;
        this.sourceAddress = sourceAddress;
        this.destinationAddress = destinationAddress;
        this.sourcePort = sourcePort;
        this.destinationPort = destinationPort;
        this.protocol = protocol;
        this.packets = packets;
        this.bytes = bytes;
        this.startTime = startTime;
        this.endTime = endTime;
        this.action = action;
        this.logStatus = logStatus;
    }

    public int getDestinationPort() {
        return destinationPort;
    }

    public int getProtocol() {
        return protocol;
    }

    @Override
    public String toString() {
        return "FlowLogRecord{" +
                "version=" + version +
                ", accountId='" + accountId + '\'' +
                ", interfaceId='" + interfaceId + '\'' +
                ", sourceAddress='" + sourceAddress + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", sourcePort=" + sourcePort +
                ", destinationPort=" + destinationPort +
                ", protocol=" + protocol +
                ", packets=" + packets +
                ", bytes=" + bytes +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", action='" + action + '\'' +
                ", logStatus='" + logStatus + '\'' +
                '}';
    }
}
