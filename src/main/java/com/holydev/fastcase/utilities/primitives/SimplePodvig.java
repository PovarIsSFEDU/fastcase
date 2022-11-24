package com.holydev.fastcase.utilities.primitives;

import javax.validation.constraints.NotNull;

public record SimplePodvig(@NotNull String name,
                           @NotNull String description,
                           @NotNull String media_contents
                           ) {
}
