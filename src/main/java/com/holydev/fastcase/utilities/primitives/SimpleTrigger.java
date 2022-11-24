package com.holydev.fastcase.utilities.primitives;

import javax.validation.constraints.NotNull;

public record SimpleTrigger(@NotNull String trigger_type,
                            String needed_action,
                            Long parent_task_id,
                            @NotNull Long author_id,
                            String timer) {
}
