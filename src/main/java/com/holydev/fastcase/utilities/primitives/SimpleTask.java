package com.holydev.fastcase.utilities.primitives;

import javax.validation.constraints.NotNull;
import java.util.List;

public record SimpleTask(@NotNull String name,
                         @NotNull String description,
                         @NotNull String media_contents,
                         @NotNull List<Long> assignee_ids,
                         @NotNull List<Long> subscribed_ids,
                         @NotNull List<SimpleTrigger> triggers
) {
}
