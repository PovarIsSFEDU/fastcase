package com.holydev.fastcase.utilities.primitives;

import javax.validation.constraints.NotNull;

public record SimpleTrigger(@NotNull String trigger_type,
                            Long target_task_id,
                            Long author_id,
                            String timer) {
}
