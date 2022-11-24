package com.holydev.fastcase.utilities.primitives;

import javax.validation.constraints.NotNull;

public record AuthRequest(@NotNull String username,
                          @NotNull String password) {
    public AuthRequest() {
        this(null, null);
    }
}
