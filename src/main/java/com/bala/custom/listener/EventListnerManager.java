package com.bala.custom.listener;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

public class EventListnerManager {
	public static SelectedEventListener newJMSInstance(boolean transacted, ConnectionFactory connFactory, Queue queue) {
		SelectedEventListener logger = new SelectedEventListener();
		logger.setTransacted(transacted);
		logger.setConnectionFactory(connFactory);
		logger.setQueue(queue);

		return logger;
	}
}
