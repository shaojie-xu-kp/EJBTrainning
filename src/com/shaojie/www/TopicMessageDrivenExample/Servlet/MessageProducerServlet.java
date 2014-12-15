package com.shaojie.www.TopicMessageDrivenExample.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.TopicMessageDrivenExample.message.MyMessage;

/**
 * Servlet producing JMS messages when accessed.
 */
@WebServlet(name = "TopicMessageProducerServlet", urlPatterns = "/sendTopicMsg.do")
public class MessageProducerServlet extends HttpServlet {
	
	/* Constant(s): */
	private static final long serialVersionUID = 1647640647915937983L;
	
		
	/** DI for Connection factory for queue. */
	@Resource(mappedName = "java:/ConnectionFactory")
	private TopicConnectionFactory tcf;
	
	/** DI for Topic destination. */
	@Resource(mappedName = "java:/topic/test")
	private Topic topic;
	
	/** MyMessage number counter. */
	private AtomicLong mMessageNumber = new AtomicLong(0);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MessageProducerServlet() {
		super();
		System.out.println("*** MessageProducerServlet created");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest inRequest, HttpServletResponse inResponse) throws ServletException, IOException {
		sendJmsMessage();
		PrintWriter theResponseWriter = inResponse.getWriter();
		theResponseWriter.println("A message was sent at " + new Date());
	}

	private void sendJmsMessage() {
	    TopicConnection conn = null;
	    TopicSession session = null;
	    MessageProducer theJMSMessageProducer = null;

		try {
	        conn = tcf.createTopicConnection();
	        session = conn.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			/* Create a JMS message producer for the topic destination. */
			theJMSMessageProducer = session.createProducer(topic);
			/* Create the object to be sent in the message created above. */
			MyMessage theObjectToSend = new MyMessage();
			theObjectToSend.setMessageNumber(mMessageNumber.incrementAndGet());
			theObjectToSend.setMessageString("Hello Message Driven Beans");
			theObjectToSend.setMessageTime(new Date());
			/* Create message used to send a Java object. */
			ObjectMessage theJmsObjectMessage = session.createObjectMessage();
			theJmsObjectMessage.setObject(theObjectToSend);
			/* Send the message. */
			theJMSMessageProducer.send(theJmsObjectMessage);
		} catch (JMSException theException) {
			theException.printStackTrace();
		} finally {
			if (theJMSMessageProducer != null) {
				try {
					theJMSMessageProducer.close();
				} catch (JMSException theException) {
				}
			}
			if (session != null) {
				try {
					session.close();
				} catch (JMSException theException) {
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (JMSException theException) {
				}
			}
		}
	}
}