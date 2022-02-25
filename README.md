This project is used to read the csv file

# Pre condition before starting

- Using java 11 or higher, please ensure you already installed jdk & jre 11.
- Using maven as package manager tool, please ensure you already installed maven.

# Question Statement

- Build a csv java parser

# Program short description

- Due to the question statement does not mention how does how input file look like and how expected output look like all
  data model are design as a Map instead of map it to fixed java bean.
- Assume that the first line of csv are the header and split by ","
- We can support new type of reader (TxtReader for instance) by implement the interface `ISourceReader` and add it to `SourceReaderFactory`
- The max file size is 1 MB or (1024 * 1024) bytes
- The output extract result look like as below:

Field           | Data type                        | Description
---             |----------------------------------|--------------
error           | Map<Integer, String>             | Map of error which key = row number & value = error message
headerIndexMap  | Map<String,Integer>              | Map of defined header which key = header_name & value = header_column_position
data            | Map<Integer, Map<String,Object>> | Map of detail row data which key = line number & value = Map of header-value pair

Example:

```json
{
  "error": {},
  "headerIndexMap": {
    "PHONE_NUMBER": 0,
    "Name": 1,
    "Age": 2
  },
  "data": {
    "2": {
      "PHONE_NUMBER": "0987000001",
      "Age": "14",
      "Name": "tester One"
    },
    "3": {
      "PHONE_NUMBER": "0987000002",
      "Age": "13",
      "Name": "tester two"
    },
    "4": {
      "PHONE_NUMBER": "0987000003",
      "Age": "12",
      "Name": "tester three"
    },
    "5": {
      "PHONE_NUMBER": "0987000004",
      "Age": "11",
      "Name": "tester four"
    },
    "6": {
      "PHONE_NUMBER": "0987000005",
      "Age": "10",
      "Name": "tester five"
    }
  }
}
```

# Instruction

- Before starting , please run `mvn clean install`
- The project can be start in Main.java in `src/main/java/com/hometest/readcsv/Main.java` and check the console to see
  the output printed


- The code is tested in test package in `src/test/java/come/hometest`
- It's developed on MacOs Montery version 12.2.1 with IntelliJ IDE.
