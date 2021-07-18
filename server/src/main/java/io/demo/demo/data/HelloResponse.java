package io.demo.demo.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author roieb
 * @package io.demo.demo
 * @date 9/4/2020
 */
public record HelloResponse ( @JsonProperty("msg") String msg){
}
