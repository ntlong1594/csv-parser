package com.hometest.reader;

import com.hometest.reader.csv.CsvReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author: long.nguyen
 **/
public class SourceReaderFactoryTest {

    @ParameterizedTest
    @MethodSource("exceptionTestCases")
    public void givenInvalidPath_whenGetSourceReader_thenThrowException(String path,
                                                                        Class<?> exceptionClazz,
                                                                        String expectedMsg) {
        assertThatThrownBy(() -> SourceReaderFactory.getSourceReader(path))
                .isInstanceOf(exceptionClazz).hasMessage(expectedMsg);
    }

    private static Stream<Arguments> exceptionTestCases() {
        return Stream.of(
                Arguments.of(null, NullPointerException.class, "filePath is marked non-null but is null"),
                Arguments.of("invalidPath", IllegalArgumentException.class, "Invalid file extension"),
                Arguments.of("invalidPath.docs", UnsupportedOperationException.class, "We don't support extension [docs] yet")
        );
    }

    @Test
    public void givenValidFilePath_whenGetSourceReader_thenReturnCorrectReader() {
        assertThat(SourceReaderFactory.getSourceReader("abc.csv").getClass()).isEqualTo(CsvReader.class);
    }
}