package com.dk.gcpservices.gcpsender.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class SampleMessage {

  private String message;
  private String description;

  public SampleMessage(String message, String description) {
    this.message = message;
    this.description = description;
  }
}
