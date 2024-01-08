
package com.zeebe.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.zeebe.client.ZeebeClient;

@RestController
@RequestMapping("/api/jobs")
public class JobServices {

  @Autowired private ZeebeClient zeebeClient;

  @PutMapping("/{key}/complete")
  public void completeJob(
      @PathVariable("key") final long key, @RequestBody final String variables) {

    zeebeClient.newCompleteCommand(key).variables(variables).send().join();
  }

  @PutMapping("/{key}/fail")
  public void failJob(@PathVariable("key") final long key) {

    zeebeClient
        .newFailCommand(key)
        .retries(0)
        .errorMessage("Failed by user.")
        .send()
        .join();
  }

  @PutMapping("/{key}/throw-error")
  public void throwError(
      @PathVariable("key") final long key, @RequestBody final String errorCode) {

    zeebeClient.newThrowErrorCommand(key).errorCode(errorCode).send().join();
  }

}