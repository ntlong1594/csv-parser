package com.hometest.reader.csv;

import com.hometest.reader.ISourceReader;
import lombok.NonNull;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author: long.nguyen
 **/
public class CsvReader implements ISourceReader {

    private static CsvReader INSTANCE;

    private static final Long MAX_FILE_SIZE_BYTE = 1024L * 1024L; // 1 MB

    public static synchronized CsvReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CsvReader();
        }
        return INSTANCE;
    }

    @Override
    @SneakyThrows
    public ExtractedResult readDataFrom(@NonNull String csvFilePath) {
        File file = new File(csvFilePath);
        if (file.length() > MAX_FILE_SIZE_BYTE) {
            throw new IllegalArgumentException(String.format("Max file size is [%s] bytes", MAX_FILE_SIZE_BYTE));
        }
        ExtractedResult extractedResult = ExtractedResult.builder().build();
        try (FileReader reader = new FileReader(file); BufferedReader br = new BufferedReader(reader)) {
            String line;
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                List<String> columns = Arrays.asList(line.split(","));
                if (extractedResult.getHeaderIndexMap().isEmpty()) {
                    // build header map
                    IntStream.range(0, columns.size()).forEach(index -> extractedResult.getHeaderIndexMap().put(columns.get(index), index));
                } else {
                    Map<String, Object> result = new HashMap<>();
                    for (String header : extractedResult.getHeaderIndexMap().keySet()) {
                        try {
                            Object dataValue = extractedResult.getHeaderIndexMap().get(header) >= columns.size() ? "" : columns.get(extractedResult.getHeaderIndexMap().get(header));
                            result.put(header, dataValue);
                        } catch (Exception exception) {
                            extractedResult.getError().put(lineNumber, String.format("Exception [%s] at data column [%s] row [%s]", exception.getMessage(), header, lineNumber));
                        }
                    }

                    if (!isEmptyLine(result)) {
                        extractedResult.getData().put(lineNumber, result);
                    }
                }
                lineNumber++;
            }
        }
        return extractedResult;
    }

    private boolean isEmptyLine(Map<String, Object> valueMap) {
        return valueMap.entrySet().stream().allMatch(element -> element.getValue() == null || element.getValue().toString().length() == 0);
    }
}
