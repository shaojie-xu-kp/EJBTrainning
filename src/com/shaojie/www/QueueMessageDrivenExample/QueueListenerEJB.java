package com.shaojie.www.QueueMessageDrivenExample;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.shaojie.www.TopicMessageDrivenExample.message.MyMessage;

/**
 * Message driven bean listening to a queue.
 */
@MessageDriven(activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"), 
                                    @ActivationConfigProperty(propertyName="destination",propertyValue="queue/test")}, 
                                    name = "QueueListener1")
public class QueueListenerEJB implements MessageListener {
	
	/* Constant(s): */
	/* Class variable(s): */
	private static AtomicInteger mCurrentBeanNumber = new AtomicInteger(0);
	/* Instance variable(s): */
	private int mBeanNumber = mCurrentBeanNumber.incrementAndGet();

	/**
	 * Default constructor.
	 */
	public QueueListenerEJB() {
		System.out.println("*** QueueListenerEJB created: " + mBeanNumber);
	}

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	@Override
	public void onMessage(Message inMessage) {
		System.out.println("*** Bean " + mBeanNumber + " received message: " + inMessage);
		extractMessagePayload(inMessage);
	}

	private void extractMessagePayload(Message inMessage) {
		/* Extract the message payload, if any. */
		if (inMessage instanceof ObjectMessage) {
			try {
				ObjectMessage theObjMsg = (ObjectMessage) inMessage;
				MyMessage theMsgPayload = (MyMessage) theObjMsg.getObject();
				System.out.println("Received queue message with number: " + theMsgPayload.getMessageNumber());
				System.out.println("Queue Message string: " + theMsgPayload.getMessageString());
				System.out.println("Queue Message time: " + theMsgPayload.getMessageTime());
			} catch (JMSException theException) {
				System.out.println("An error occurred retrieving message payload: " + theException);
			}
		}
	}
}