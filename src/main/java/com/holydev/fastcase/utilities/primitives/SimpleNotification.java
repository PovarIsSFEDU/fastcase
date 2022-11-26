package com.holydev.fastcase.utilities.primitives;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SimpleNotification {
    String type;
    String content;
}
