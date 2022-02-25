package com.hometest.reader;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: long.nguyen
 **/
public interface ISourceReader {
    ExtractedResult readDataFrom(@NonNull String path);

    @Builder
    @AllArgsConstructor
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class ExtractedResult {
        /**
         * Contains the error
         */
        @Builder.Default
        Map<Integer, String> error = new LinkedHashMap<>();
        /**
         * List of supported headers
         */
        @Builder.Default
        Map<String, Integer> headerIndexMap = new LinkedHashMap<>();

        /**
         * Key = Row number
         * Value = Map of value
         */
        @Builder.Default
        Map<Integer, Map<String, Object>> data = new LinkedHashMap<>();
    }
}
