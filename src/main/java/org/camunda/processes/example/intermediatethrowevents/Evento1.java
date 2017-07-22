package org.camunda.processes.example.intermediatethrowevents;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


public class Evento1 implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception {

		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();
    RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();

		Map<String, Object> processVariables = new HashMap<String,Object>();

    
    processVariables.put("ejecutivo", execution.getVariable("ejecutivo"));
    
    processVariables.put("titulo", execution.getVariable("titulo"));
    
    processVariables.put("descripcion", execution.getVariable("descripcion"));
    
    processVariables.put("INSTANCE_ID_Example1", execution.getProcessInstanceId());

    Map<String, Object> localVariables = execution.getVariables();
		for(Map.Entry<String,Object> entry : localVariables.entrySet()){
			if(entry.getKey().startsWith("INSTANCE_ID_")){
				processVariables.put(entry.getKey(), entry.getValue());
			}
		}

		runtimeService.createMessageCorrelation("Message_097dq0e")
			.setVariables(processVariables).correlate();
	}

}


