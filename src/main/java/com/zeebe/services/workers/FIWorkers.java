package com.zeebe.services.workers;

import org.springframework.stereotype.Component;

import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;

@Component
public class FIWorkers {

	@JobWorker(type="myServiceTask", autoComplete=true)
	public void aServiceTask(final JobClient client, final ActivatedJob job) {
		System.out.println("Job worker worked!");
		System.out.println(job.getVariables());
		client.newCompleteCommand(job).variables("{\"var1\":\"final\"}").send();
	}
}
