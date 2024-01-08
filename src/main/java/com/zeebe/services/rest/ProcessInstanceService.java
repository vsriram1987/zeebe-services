
package com.zeebe.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;

@RestController
@RequestMapping("/api/instances")
public class ProcessInstanceService {

	@Autowired
	private ZeebeClient zeebeClient;

	@DeleteMapping("/{processInstanceKey}")
	public void cancelProcessInstance(@PathVariable("processInstanceKey") final long key) throws Exception {
		zeebeClient.newCancelInstanceCommand(key).send().join();
	}

	@PostMapping("/{processDefinitionKey}")
	public long createProcessInstance(@PathVariable("processDefinitionKey") final long processDefinitionKey,
			@RequestBody final String payload) {

		final ProcessInstanceEvent processInstanceEvent = zeebeClient.newCreateInstanceCommand()
				.processDefinitionKey(processDefinitionKey).variables(payload).send().join();
		return processInstanceEvent.getProcessInstanceKey();
	}

	@PutMapping("/{key}/set-variables")
	public void setVariables(@PathVariable("key") final long key, @RequestBody final String payload) throws Exception {
		zeebeClient.newSetVariablesCommand(key).variables(payload).send().join();
	}

	@PutMapping("/{key}/set-variables-local")
	public void setVariablesLocal(@PathVariable("key") final long key, @RequestBody final String payload)
			throws Exception {
		zeebeClient.newSetVariablesCommand(key).variables(payload).local(true).send().join();
	}

}
