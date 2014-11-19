package fr.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import fr.dao.TaskDAO;
import fr.model.Task;

public class TwilioService {

	private static String ACCOUNT_SID; 
	private static  String AUTH_TOKEN; 
	private TaskDAO dao = new TaskDAO();
	
	public TwilioService(){
		ACCOUNT_SID = PropertiesLoader.getInstance().getAccountSID();
		AUTH_TOKEN = PropertiesLoader.getInstance().getAuthToken();
	}
	
	public Message SendSMS(Task t) throws TwilioRestException{
		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		Task oldTask = dao.getTaskById(t.getId());
	    // Build a filter for the MessageList
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("Body", oldTask.getTitle().toString() + "task has been marked as done"));
	    params.add(new BasicNameValuePair("From", PropertiesLoader.getInstance().getFromNumber()));
	    params.add(new BasicNameValuePair("To", PropertiesLoader.getInstance().getToNumber()));
	    
	    MessageFactory messageFactory = client.getAccount().getMessageFactory();
	    return messageFactory.create(params); 
	}
	
	
}
