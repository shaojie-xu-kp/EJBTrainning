package com.shaojie.www.QueueMessageDrivenExample.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shaojie.www.TopicMessageDrivenExample.message.MyMessage;

/**
 * Servlet producing JMS messages when accessed.
 */
@WebServlet(name = "QueueMessageProducerServlet", urlPatterns = "/sendQueueMsg.do")
public class MessageProducerServlet extends HttpServlet {
	/* Constant(s): */
	private static final long serialVersionUID = -4364474814559146703L;
	/* Instance variable(s): */

	/** MyMessage number counter. */
	private AtomicLong mMessageNumber = new AtomicLong(0);

	/**
	 * @see HttpServlet#HttpServlet() 140
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
	protected void doGet(HttpServletRequest inRequest,
			HttpServletResponse inResponse) throws ServletException,
			IOException {

		PrintWriter theResponseWriter = inResponse.getWriter();

		try {
			sendJmsMessage();
			theResponseWriter.println("A message was sent at " + new Date());
		} catch (JMSException theException) {
			theResponseWriter.println("An error occurred sending message: "
					+ theException);
		}
	}

	private void sendJmsMessage() throws JMSException {

		QueueConnection theJMSConnection = null;
		QueueSession theJMSSession = null;
		Queue queue = null;
		MessageProducer theJMSMessageProducer = null;

		try {
			InitialContext iniCtx = new InitialContext();
			QueueConnectionFactory qcf = (QueueConnectionFactory) iniCtx.lookup("ConnectionFactory");
			theJMSConnection = qcf.createQueueConnection();
			queue = (Queue) iniCtx.lookup("queue/test");
			theJMSSession = theJMSConnection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
			/* Create a JMS message producer for the queue destination. */
			theJMSMessageProducer = theJMSSession.createProducer(queue);
			/* Create the object to be sent in the message created above. */
			MyMessage theObjectToSend = new MyMessage();
			theObjectToSend.setMessageNumber(mMessageNumber.incrementAndGet());
			theObjectToSend.setMessageString("Hello Message Driven Beans");
			theObjectToSend.setMessageTime(new Date());
			/* Create message used to send a Java object. */
			ObjectMessage theJmsObjectMessage = theJMSSession.createObjectMessage();
			theJmsObjectMessage.setObject(theObjectToSend);
			/* Send the message. */
			theJMSMessageProducer.send(theJmsObjectMessage);
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			closeJmsResources(theJMSConnection);
		}
	}

	/**
	 * Closes the supplied JMS connection if it is not null. If supplied
	 * connection is null, then do nothing.
	 *
	 * @param inJMSConnection JMS connection to close.
	 */
	private void closeJmsResources(Connection inJMSConnection) {
		if (inJMSConnection != null) {
			try {
				inJMSConnection.close();
			} catch (JMSException theException) {
			}
		}
	}
}