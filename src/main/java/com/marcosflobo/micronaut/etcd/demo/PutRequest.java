package com.marcosflobo.micronaut.etcd.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PutRequest {

  @JsonProperty("key")
  private String key;
  @JsonProperty("value")
  private String value;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "PutRequest{" +
        "key='" + key + '\'' +
        ", value='" + value + '\'' +
        '}';
  }
}
