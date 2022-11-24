package com.holydev.fastcase.utilities.primitives;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public record RegistrationRequest(@NotNull String username,
                                  @NotNull String password,
                                  @NotNull String fio,
                                  @NotNull @Email String email,
                                  @NotNull String phone
) {
}
