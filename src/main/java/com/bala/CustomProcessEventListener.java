package com.bala;


import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.jbpm.workflow.instance.impl.NodeInstanceImpl;
import org.kie.api.definition.process.Node;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeEvent;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomProcessEventListener implements ProcessEventListener {

	private static final Logger logger = LoggerFactory.getLogger(CustomProcessEventListener.class);
	private ConnectionFactory connectionFactory;
	private Queue queue;
	private boolean transacted = true;

	public CustomProcessEventListener(ConnectionFactory conn, Queue queue, boolean isTrans) {
		setConnectionFactory(conn);
		setQueue(queue);
		setTransacted(isTrans);
	}

	public void afterNodeLeft(ProcessNodeLeftEvent event) {
		sendMessage(extractLimitedNodeInfo(event));
	}

	public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
		// Do nothing

	}

	public void afterProcessCompleted(ProcessCompletedEvent event) {
		// Do nothing

	}

	public void afterProcessStarted(ProcessStartedEvent event) {
		// Do nothing

	}

	public void afterVariableChanged(ProcessVariableChangedEvent event) {
		// Do nothing

	}

	public void beforeNodeLeft(ProcessNodeLeftEvent event) {
		// Do nothing

	}

	public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
		// Do nothing

	}

	public void beforeProcessCompleted(ProcessCompletedEvent event) {
		// Do nothing

	}

	public void beforeProcessStarted(ProcessStartedEvent event) {
		// Do nothing

	}

	public void beforeVariableChanged(ProcessVariableChangedEvent event) {
		// Do nothing

	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public boolean isTransacted() {
		return transacted;
	}

	public void setTransacted(boolean transacted) {
		this.transacted = transacted;
	}

	protected void sendMessage(String messageContent) {

		if (connectionFactory == null && queue == null) {
			throw new IllegalStateException("ConnectionFactory and Queue cannot be null");
		}

		Connection queueConnection = null;
		Session queueSession = null;
		MessageProducer producer = null;

		try {
			queueConnection = connectionFactory.createConnection();
			queueSession = queueConnection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);

			TextMessage message = queueSession.createTextMessage(messageContent);
			message.setIntProperty("EventType", 7);
			producer = queueSession.createProducer(queue);
			producer.send(message);
		} 
		catch (Exception e) {
			throw new RuntimeException("Error when sending JMS message with working memory event", e);
		} 
		finally {
			if (producer != null) {
				try {
					producer.close();
				} catch (JMSException e) {
					logger.warn("Error when closing producer", e);
				}
			}

			if (queueSession != null) {
				try {
					queueSession.close();
				} catch (JMSException e) {
					logger.warn("Error when closing queue session", e);
				}
			}

			if (queueConnection != null) {
				try {
					queueConnection.close();
				} catch (JMSException e) {
					logger.warn("Error when closing queue connection", e);
				}
			}
		}
	}

	protected String extractLimitedNodeInfo(ProcessNodeEvent event) {
		String nodeType = null;
		String nodeName = "";

		
		// Get the node name, node type and node exit timestamp
		Node node = event.getNodeInstance().getNode();
		Date nodeInstanceExitDate = event.getEventDate();
		NodeInstanceImpl nodeInstance = (NodeInstanceImpl) event.getNodeInstance();

		if (null != node) {
			nodeName = node.getName();
			nodeType = node.getClass().getSimpleName();
		} else {
			nodeType = (String) nodeInstance.getMetaData("NodeType");
		}

		// Get the process instance variables
		WorkflowProcessInstance workflowProcessInstance = (WorkflowProcessInstance) event.getProcessInstance();
		VariableScopeInstance variableScope = (VariableScopeInstance) workflowProcessInstance
				.getContextInstance(VariableScope.VARIABLE_SCOPE);
		Collection<Object> variables = variableScope.getVariables().values();
		
		return "Node: " + nodeName + ", " + nodeType + "; " + "ExitDate: " + nodeInstanceExitDate + ", Variables: " + variables;
	}

}
