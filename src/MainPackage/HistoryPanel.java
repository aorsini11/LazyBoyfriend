package MainPackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.twilio.sdk.TwilioRestClient;


public class HistoryPanel extends JPanel{
	static JList<String> list;
	JScrollPane scrollPane;
	public static TextMessage selected;
	static JLabel date, recipient;
	
	public HistoryPanel(String title){
		
		setBorder(BorderFactory.createTitledBorder(title));
		//setLayout(new FlowLayout());
		date = new JLabel("Date: ");
		date.setFont(new Font("Helvetica",Font.ROMAN_BASELINE,15));	
		recipient = new JLabel("Recipient: ");
		recipient.setFont(new Font("Helvetica",Font.ROMAN_BASELINE,15));
		list = new JList<String>();
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setSelectedIndex(0);
	    list.setBackground(new Color(238,246,255));
	    list.addListSelectionListener(new ListSelectionListener() {
		      public void valueChanged(ListSelectionEvent listSelectionEvent) {
		    	  
		    	  JList currlist = (JList) listSelectionEvent.getSource();
			      selected = getTextAtIndex(currlist.getSelectedIndex());
			      updateSelected();
		      }
	    });
	    list.setVisible(true);
		scrollPane= new JScrollPane(list);
		
		//list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		scrollPane.setPreferredSize(new Dimension(550,350));
		date.setPreferredSize(new Dimension(250,50));
		recipient.setPreferredSize(new Dimension(250,50));
		
		add(scrollPane);
		add(date);
		add(recipient);


	}
	
	public Dimension getPreferredSize() {
		return new Dimension(600,440);
	}
	
	public static TextMessage getTextAtIndex(int i){
		if(i<0||MessagePanel.messageList.size()<i)
			return null;
		else{
			return MessagePanel.messageList.get(i);
		}
	}
	
	public static void updateSelected(){
		if(selected==null){
			date.setText("Date: ");
			recipient.setText("Recipient: ");
		}
		else{
			Calendar time = selected.time;
			date.setText("Date: " + time.MONTH + "\\" + time.DAY_OF_MONTH + "   " + time.HOUR + ":" + time.MINUTE);
			recipient.setText("Recipient: " + selected.to);
		}
	}
	
	public static void UpdateList(ArrayList<TextMessage> messages){
	String[] text = new String[messages.size()];
	for(int i = messages.size(); i > 0; i--){
		text[messages.size() - i] = messages.get(i-1).text;
	}
	list.setListData(text);	
	}
	
}
