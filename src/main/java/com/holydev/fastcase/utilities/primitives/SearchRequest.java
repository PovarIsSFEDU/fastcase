package com.holydev.fastcase.utilities.primitives;

import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotNull;
import java.util.List;

public record SearchRequest(@NotNull String search_value
) {
}
