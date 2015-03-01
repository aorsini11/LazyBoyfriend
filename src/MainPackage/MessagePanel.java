package MainPackage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Stack;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*; 
import com.twilio.sdk.resource.instance.*; 

public class MessagePanel extends JPanel implements ActionListener{
		JButton start;
		JButton stop;
		JButton reset;
		JLabel status;
		JTextField number;
		static ArrayList<TextMessage> messageList;
		Timer countdownTimer;
        int timeRemaining = 10;
        final JLabel label;
		
		TwilioRestClient client;
		
		public static final String ACCOUNT_SID = ""; 
		public static final String AUTH_TOKEN = "";
		
		public MessagePanel(String title){
			GridLayout experimentLayout = new GridLayout(0,3,10,10);
			setLayout(experimentLayout);
			
			start = new JButton("Start");
			stop = new JButton("Stop");
			reset = new JButton("Reset");
			status = new JLabel("Status: ");
			number = new JTextField("Recipient Phone Number");
			number.setHorizontalAlignment(JTextField.CENTER);
			status.setFont(new Font("Helvetica",Font.ROMAN_BASELINE,15));
			
			
			start.setPreferredSize(new Dimension(150,25));
			start.addActionListener(this);
			stop.setPreferredSize(new Dimension(150,25));
			stop.addActionListener(this);
			reset.addActionListener(this);
			
			number.setPreferredSize(new Dimension(150,25));
			status.setPreferredSize(new Dimension(300,25));
			
			messageList = new ArrayList<TextMessage>();
			countdownTimer = new Timer(1000, new CountdownTimerListener());
			
			label = new JLabel("", JLabel.CENTER);
			label.setFont(new Font("Helvetica",Font.ROMAN_BASELINE,15));
			
			add(start);
			add(stop);
			add(reset);		
			add(status);
			add(number);
			add(label);
			}
		
		
		public Dimension getPreferredSize() {
			return new Dimension(650,100);
		}

		public void actionPerformed(ActionEvent e){ 
			 if(e.getSource()==start){
				 countdownTimer.start();

			 }
			 else if(e.getSource()==stop){
				 countdownTimer.stop();
			 }
			 else if(e.getSource()==reset){
				 countdownTimer.stop();
				 timeRemaining = 10;
				 label.setText("");
				 status.setText("Status: ");
				 messageList.clear();	
				 HistoryPanel.UpdateList(messageList);
				 HistoryPanel.selected = null;
				 HistoryPanel.updateSelected();
			 }
		}
		
		class CountdownTimerListener implements ActionListener {
		      public void actionPerformed(ActionEvent e) {
				if (--timeRemaining > 0) {
		            label.setText(String.valueOf(timeRemaining) + " seconds until next text");
		         } else {
		            prepareMessage();
		            timeRemaining = 10;
		            label.setText(String.valueOf(timeRemaining) + " seconds until next text");
		         }
		      }
		   }
		private void prepareMessage(){
			 try{
			 			 
			 ArrayList<String> tem = new ArrayList<String>();
			 String randomMessage = getRandomMessage();
			 sendMessage(number.getText(),"+17328565372",randomMessage,"Alex");	
			 TextMessage tempMessage = new TextMessage(randomMessage,number.getText(),"+17328565372",Calendar.getInstance(TimeZone.getTimeZone("America/New York")));
			 messageList.add((tempMessage));
			 HistoryPanel.UpdateList(messageList);
			 HistoryPanel.selected = HistoryPanel.getTextAtIndex(0);
			 HistoryPanel.updateSelected();
			 status.setText("Status: Sent text number " + messageList.size());
			 }
			 catch(Exception j){
				 System.out.println("Unable to send Message: " + j);
				 status.setText("Status: Unable to send Message");
			 }
		}
		
		private void sendMessage(String to, String from, String text, String sender) throws TwilioRestException{
			// Build the parameters
			String body = text + "\n" + "-From " + sender;
			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
			 List<NameValuePair> params = new ArrayList<NameValuePair>(); 
			 params.add(new BasicNameValuePair("To", to)); 
			 params.add(new BasicNameValuePair("From", from)); 
			 params.add(new BasicNameValuePair("Body", body));   
		
			 MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
			 Message message = messageFactory.create(params); 
			 System.out.println(message.getSid()); 
		}
		public String getRandomMessage(){
			if(MainWindow.complimentList.size()==0)
				return "";
			double num = Math.random()*10000;
			int index2 = (int)num % MainWindow.complimentList.size();
			return MainWindow.complimentList.get(index2);
		}
}
