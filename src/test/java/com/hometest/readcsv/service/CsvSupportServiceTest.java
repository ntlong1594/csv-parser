package com.hometest.readcsv.service;

import com.hometest.reader.ISourceReader;
import com.hometest.reader.csv.CsvReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CsvSupportServiceTest {

    @Test
    public void givenInvalidFileSize_whenReadFile_thenThrowException() {
        assertThatThrownBy(() -> CsvReader.getInstance().readDataFrom("src/test/resources/input/exceed-limit-size.csv"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Max file size is [" + 1024 * 1024 + "] bytes");
    }

    @ParameterizedTest
    @MethodSource("testCases")
    public void givenInputFile_whenReadFile_thenReturnCorrectResultMap(String inputPath,
                                                                       ISourceReader.ExtractedResult expectedResult) {
        // When
        ISourceReader.ExtractedResult result = CsvReader.getInstance().readDataFrom(inputPath);
        // Then
        assertThat(result.getError().size()).isEqualTo(expectedResult.getError().size());
        for (Integer key : result.getError().keySet()) {
            assertThat(result.getError().get(key)).isEqualTo(expectedResult.getError().get(key));
        }

        assertThat(result.getHeaderIndexMap().size()).isEqualTo(expectedResult.getHeaderIndexMap().size());
        for (String key : result.getHeaderIndexMap().keySet()) {
            assertThat(result.getHeaderIndexMap().get(key)).isEqualTo(expectedResult.getHeaderIndexMap().get(key));
        }

        assertThat(result.getData().size()).isEqualTo(expectedResult.getData().size());
        for (Integer key : result.getData().keySet()) {
            assertThat(result.getData().get(key).size()).isEqualTo(expectedResult.getData().get(key).size());
            for (String innerKey : result.getData().get(key).keySet()) {
                assertThat(result.getData().get(key).get(innerKey)).isEqualTo(expectedResult.getData().get(key).get(innerKey));
            }
        }
    }

    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("src/test/resources/input/input-testcase-1.csv", ISourceReader.ExtractedResult.builder()
                        .error(Collections.emptyMap())
                        .headerIndexMap(Map.of("phone_number", 0, "name", 1, "age", 2))
                        .data(Map.of(
                                3, Map.of("phone_number", "0987000001", "name", "tester One", "age", "14"),
                                4, Map.of("phone_number", "0987000002", "name", "tester two", "age", "13"),
                                5, Map.of("phone_number", "0987000003", "name", "tester three", "age", "12"),
                                6, Map.of("phone_number", "0987000004", "name", "tester four", "age", "11"),
                                7, Map.of("phone_number", "0987000005", "name", "tester five", "age", "10")
                        ))
                        .build()),

                Arguments.of("src/test/resources/input/input-testcase-2.csv", ISourceReader.ExtractedResult.builder()
                        .error(Collections.emptyMap())
                        .headerIndexMap(Map.of("field1", 0, "field2", 1, "field3", 2))
                        .data(Map.of(
                                3, Map.of("field1", "", "field2", "", "field3", "13"),
                                4, Map.of("field1", "", "field2", "tester", "field3", ""),
                                5, Map.of("field1", "0123456", "field2", "", "field3", "")
                        ))
                        .build())
        );
    }
}