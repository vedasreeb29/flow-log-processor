# flow-log-processor

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
The output file path parameter is optional and if not provided, the output is stored in src/resources/output.txt. 