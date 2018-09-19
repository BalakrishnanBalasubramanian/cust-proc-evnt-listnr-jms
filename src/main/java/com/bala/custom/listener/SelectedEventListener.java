package com.bala.custom.listener;

import org.jbpm.process.audit.jms.AsyncAuditLogProducer;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;

public class SelectedEventListener implements org.kie.api.event.process.ProcessEventListener {

	@Override
	public void beforeProcessStarted(ProcessStartedEvent event) {
		// Do nothing
		System.out.println("Inside beforeProcessStarted event");
	}

	@Override
	public void afterProcessStarted(ProcessStartedEvent event) {
		// Do nothing
		System.out.println("Inside afterProcessStarted event");
	}

	@Override
	public void beforeProcessCompleted(ProcessCompletedEvent event) {
		// Do nothing
		System.out.println("Inside beforeProcessCompleted event");
	}

	@Override
	public void afterProcessCompleted(ProcessCompletedEvent event) {
		// Do nothing
		System.out.println("Inside afterProcessCompleted event");
	}

	@Override
	public void beforeVariableChanged(ProcessVariableChangedEvent event) {
		// Do nothing
		System.out.println("Inside beforeVariableChanged event");
	}

	@Override
	public void afterVariableChanged(ProcessVariableChangedEvent event) {
		// Do nothing
		System.out.println("Inside afterVariableChanged event");
	}
	
	public void beforeNodeLeft(ProcessNodeLeftEvent event) {
	}

	public void afterNodeLeft(ProcessNodeLeftEvent event) {
	}

	public void afterNodeTriggered(ProcessVariableChangedEvent event) {
	}

	public void beforeNodeTriggered(ProcessVariableChangedEvent event) {
	}

}