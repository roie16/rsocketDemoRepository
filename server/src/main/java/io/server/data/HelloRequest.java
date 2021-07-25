package io.server.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author roieb
 * @package io.demo.demo
 * @date 9/4/2020
 */

public record HelloRequest(@JsonProperty("name") @NotBlank String name,
                           @JsonProperty("age") @Min(18) @Max(130) int age) {
}
