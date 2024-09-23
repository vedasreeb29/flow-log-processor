package com.flowlogprocessor.services;

import com.flowlogprocessor.exceptions.FlowLogException;
import com.flowlogprocessor.models.LookupKeyObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LookupTableReader {
    private final String lookupTableFile;

    public LookupTableReader(String lookupTableFile) {
        this.lookupTableFile = lookupTableFile;
    }

    public Map<LookupKeyObject, String> generateLookupTable() throws FlowLogException{
        try(BufferedReader reader = new BufferedReader(new FileReader(this.lookupTableFile))) {
            Map<LookupKeyObject, String> lookupTable = new HashMap<>();

            // Skip the header line
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null) {
                String[] lookupFields = line.split(",");
                int destinationPort = Integer.parseInt(lookupFields[0]);
                String protocol = lookupFields[1];
                String tag = lookupFields[2];

                LookupKeyObject lookupKey = new LookupKeyObject(destinationPort, protocol);
                lookupTable.put(lookupKey, tag);
            }

            return lookupTable;
        } catch(FileNotFoundException e) {
            throw new FlowLogException("Lookup file not found, please provide correct full path for the lookup file.", e);
        } catch (IOException e) {
            throw new FlowLogException("Error while reading the lookup file.", e);
        }
    }
}
