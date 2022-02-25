package com.hometest.readcsv;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hometest.reader.ISourceReader;
import com.hometest.reader.SourceReaderFactory;


public class Main {

    private static final String INPUT_PATH = "src/main/resources/input/input.csv";

    public static void main(String[] args) throws Exception {
        ISourceReader.ExtractedResult extractedResult = SourceReaderFactory.getSourceReader(INPUT_PATH).readDataFrom(INPUT_PATH);
        System.out.println(String.format("Read %s records, %s succeeded and %s failed", extractedResult.getData().size() + extractedResult.getError().size(), extractedResult.getData().size(), extractedResult.getError().size()));
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(extractedResult));
    }

}
