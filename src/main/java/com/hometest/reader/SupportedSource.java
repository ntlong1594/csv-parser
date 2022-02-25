package com.hometest.reader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

/**
 * @author: long.nguyen
 **/
@AllArgsConstructor
public enum SupportedSource {
    CSV("csv"), UNKNOWN("unknown");

    @Getter
    String value;

    public static SupportedSource fromValue(@NonNull String extension) {
        return Arrays.stream(SupportedSource.values())
                .filter(enumValue -> enumValue.getValue().equals(extension))
                .findFirst().orElse(UNKNOWN);
    }
}
