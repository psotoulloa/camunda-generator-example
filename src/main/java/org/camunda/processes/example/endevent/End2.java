package org.camunda.processes.example.endevent;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class End2 implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {

		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();
    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();

		Map<String, Object> processVariables = new HashMap<String,Object>();

    
    processVariables.put("archivo", execution.getVariable("archivo"));
    
    processVariables.put("fecha", execution.getVariable("fecha"));
    
    processVariables.put("dias", execution.getVariable("dias"));
    
    processVariables.put("INSTANCE_ID_Example2", execution.getProcessInstanceId());

    Map<String, Object> localVariables = execution.getVariables();
		for(Map.Entry<String,Object> entry : localVariables.entrySet()){
			if(entry.getKey().startsWith("INSTANCE_ID_")){
				processVariables.put(entry.getKey(), entry.getValue());
			}
		}

		runtimeService.createMessageCorrelation("Message_2l5u8m4")
			.processInstanceId(execution.getVariable("INSTANCE_ID_Example1").toString())
			.setVariables(processVariables).correlate();
	}

}


