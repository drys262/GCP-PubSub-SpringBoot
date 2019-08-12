package com.dk.gcpservices.gcpsender.controllers;

import com.dk.gcpservices.gcpsender.models.SampleMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class DemoController {

  @Autowired
  private PubSubTemplate pubSubTemplate;
  private static final String TOPIC_NAME = "exampleTopic";

  @PostMapping("/postMessage")
  public RedirectView postMessage(@RequestBody SampleMessage sampleMessage)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
  String jsonString = objectMapper.writeValueAsString(sampleMessage);
    pubSubTemplate.publish(TOPIC_NAME, jsonString);
    return buildStatusView("Messages published asynchronously; status unknown.");
}

  private RedirectView buildStatusView(String statusMessage) {
    RedirectView view = new RedirectView("/");
    view.addStaticAttribute("statusMessage", statusMessage);
    return view;
  }
}
