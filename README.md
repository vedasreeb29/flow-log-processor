# flow-log-processor
## Project High-level Structure
```
flow-log-processor
├── README.md                               # Project documentation                                  
├── src
│   ├── com
│   │   └── flowlogprocessor                # Main application package
│   │       ├── FlowLogProcessor.java       # Entry point of the program
│   │       ├── exceptions                  
│   │       │   └── FlowLogException.java   # Generic Exception class
│   │       ├── models                      
│   │       │   ├── FlowLogRecord.java      # Data model for flow log records
│   │       │   └── LookupKeyObject.java    # Data model for Lookup keys
│   │       └── services                    
│   │           ├── FlowLogMapper.java      # Processes flow logs and computes the counts for tags and port/protocol combinations
│   │           ├── FlowLogReader.java      # Reads the Flow logs from input file, maps them to FlowLogRecord objects and returns a list of flow logs
│   │           ├── LookupTableReader.java  # Reads the Lookup table from file and creates a map of Lookup objects and tags
│   │           └── ProtocolMapper.java     # Maps a Protocol in decimal to the protocol's string keyword for processing
│   └── resources
│       ├── flow_logs.txt                   # Sample Flow log input txt file
│       ├── lookup_table.csv                # Sample Lookup table input csv file
│       └── output.txt                      # Sample Output txt file
```
## Steps to run
First, install the Java jdk.

Clone the repository and navigate to the flow-log-processor folder

Compile all the source files using the javac command:
```
javac -d out **/*.java
```
Run the FlowProcessor using the java command:
```
java -cp out com.flowlogprocessor.FlowLogProcessor <input_file_path> <lookup_table_path> <optional_output_file_path>
Eg: java -cp out com.flowlogprocessor.FlowLogProcessor src/resources/flow_logs.txt src/resources/lookup_table.csv src/resources/output.txt
```
The output file path parameter is optional and if not provided, the output is stored in src/resources/output.txt by default.

## Assumptions
1. Since the requirement is a bit unclear from the problem statement, it is assumed that we need to compute the count of all the port/protocol combinations from the flow logs instead of only the ones which match the Lookup table, there would be a one line code change if it's the latter.
2. Currently, the program only supports a version 2 flow log record, but it can be easily extended to support custom log format.

## Run time and Space Analysis
- Our program has worst case time complexity of **O(N)**, where **N** is the number of flow logs
- Our program has worst case space complexity of **O(N)**, where **N** is the number of flow logs

It is given that the flow log file size can be up to 10 MB. Considering we have 100 characters per flow record on an average and each character takes approximately 1byte, we could have a maximum of 10^5 records.

## Testing
Tested the code with an input file of ~12MB(resources/flow_logs_large.txt) and the program took less than 0.5seconds(resources/output_large.txt).
Run the FlowProcessor using the below java command to test this:
```
java -cp out com.flowlogprocessor.FlowLogProcessor src/resources/flow_logs_large.txt src/resources/lookup_table.csv src/resources/output_large.txt
```