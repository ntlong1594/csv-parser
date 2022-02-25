package com.hometest.reader;

import com.hometest.reader.csv.CsvReader;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

/**
 * @author: long.nguyen
 * Base on the file extension, the factory will get corresponding SourceReader
 * if we don't support that file type, we will throw UnsupportedOperation
 **/
public class SourceReaderFactory {
    private static final Map<SupportedSource, ISourceReader> SOURCE_READER_MAP =
            Map.of(SupportedSource.CSV, CsvReader.getInstance());

    public static ISourceReader getSourceReader(@NonNull String filePath) {
        String extension = Optional.of(filePath.split("\\.")).filter(paths -> paths.length > 1).map(paths -> paths[1])
                .orElseThrow(() -> new IllegalArgumentException("Invalid file extension"));
        SupportedSource supportedSource = SupportedSource.fromValue(extension);
        return Optional.ofNullable(SOURCE_READER_MAP.get(supportedSource))
                .orElseThrow(() -> new UnsupportedOperationException(String.format("We don't support extension [%s] yet", extension)));
    }

}
