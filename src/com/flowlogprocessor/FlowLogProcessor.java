package com.flowlogprocessor;

import com.flowlogprocessor.exceptions.FlowLogException;
import com.flowlogprocessor.models.FlowLogRecord;
import com.flowlogprocessor.models.LookupKeyObject;
import com.flowlogprocessor.services.FlowLogMapper;
import com.flowlogprocessor.services.FlowLogReader;
import com.flowlogprocessor.services.LookupTableReader;

import java.util.List;
import java.util.Map;

/**
 * The main class which calls other services to load input flow logs, lookup table and writes the counts of tag matches and port/protocol combinations to the output file
 */
public class FlowLogProcessor {

    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("Usage: java -cp src FlowLogProcessor <input_file_path> <lookup_table_path> <optional_output_file_path>");
            return;
        }

        String inputFilePath = args[0];
        String lookupTablePath = args[1];
        String outputFilePath;
        if(args.length > 2)
            outputFilePath = args[2];
        else
            outputFilePath = "src/resources/output.txt";

        try {
            FlowLogReader flowLogReader = new FlowLogReader(inputFilePath);
            List<FlowLogRecord> flowRecords = flowLogReader.getFlowRecords();

            LookupTableReader lookupTableReader = new LookupTableReader(lookupTablePath);
            Map<LookupKeyObject, String> lookupTable = lookupTableReader.generateLookupTable();

            FlowLogMapper flowLogMapper = new FlowLogMapper();
            flowLogMapper.mapFlowLogs(flowRecords, lookupTable, outputFilePath);
        } catch(FlowLogException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
