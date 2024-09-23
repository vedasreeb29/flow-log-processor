package com.flowlogprocessor.services;

import com.flowlogprocessor.exceptions.FlowLogException;
import com.flowlogprocessor.models.FlowLogRecord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads the Flow logs from input file, maps them to FlowLogRecord objects
 */
public class FlowLogReader {
    private final String flowLogFile;

    /**
     * Constructs a FlowLogReader with the specified flow log file.
     * @param flowLogFile the path to the flow log file
     */
    public FlowLogReader(String flowLogFile) {
        this.flowLogFile = flowLogFile;
    }

    /**
     * Reads flow logs from the input file and returns a list of FlowLogRecord objects
     * @return a list of flow log records
     * @throws FlowLogException if an error occurs while reading the file or if the file is not found
     */
    public List<FlowLogRecord> getFlowRecords() throws FlowLogException {
        try(BufferedReader reader = new BufferedReader(new FileReader(this.flowLogFile))) {
            List<FlowLogRecord> flowLogRecords = new ArrayList<>();

            String flowLogLine;
            while((flowLogLine = reader.readLine()) != null) {
                FlowLogRecord flowLogRecord = convertFlowLogLineToObject(flowLogLine);
                flowLogRecords.add(flowLogRecord);
            }

            return flowLogRecords;
        } catch(FileNotFoundException e) {
            throw new FlowLogException("Input file not found, please provide correct full path for the input file.", e);
        } catch (IOException e) {
            throw new FlowLogException("Error while reading the flow logs.", e);
        }
    }

    /**
     * Converts a line from flow log input file into a FlowLogRecord object.
     * @param flowLogLine the line from the flow log input file
     * @return a FlowLogRecord object populated with the parsed data
     */
    private FlowLogRecord convertFlowLogLineToObject(String flowLogLine) {
        String[] flowLogFields = flowLogLine.split(" ");

        int version = Integer.parseInt(flowLogFields[0]);
        String accountId = flowLogFields[1];
        String interfaceId = flowLogFields[2];
        String sourceAddress = flowLogFields[3];
        String destinationAddress = flowLogFields[4];
        int sourcePort = Integer.parseInt(flowLogFields[5]);
        int destinationPort = Integer.parseInt(flowLogFields[6]);
        int protocol = Integer.parseInt(flowLogFields[7]);
        int packets = Integer.parseInt(flowLogFields[8]);
        int bytes = Integer.parseInt(flowLogFields[9]);
        int startTime = Integer.parseInt(flowLogFields[10]);
        int endTime = Integer.parseInt(flowLogFields[11]);
        String action = flowLogFields[12];
        String logStatus = flowLogFields[13];

        return new FlowLogRecord(version, accountId, interfaceId, sourceAddress, destinationAddress, sourcePort,
                destinationPort, protocol, packets, bytes, startTime, endTime, action, logStatus);
    }
}
