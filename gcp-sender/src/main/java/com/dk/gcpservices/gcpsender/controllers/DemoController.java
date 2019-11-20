package com.dk.gcpservices.gcpsender.controllers;

import com.dk.gcpservices.gcpsender.models.SampleMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  @Autowired
  private PubSubTemplate pubSubTemplate;
  private static final String TOPIC_NAME = "upload-files-topic";

  @PostMapping("/postMessage")
  public ResponseEntity postMessage(@RequestBody SampleMessage sampleMessage)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonString = objectMapper.writeValueAsString(sampleMessage);
    pubSubTemplate.publish(TOPIC_NAME, jsonString);
    return new ResponseEntity(HttpStatus.OK);
  }

}
