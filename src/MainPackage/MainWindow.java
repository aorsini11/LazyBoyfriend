package MainPackage;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument.BranchElement;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import MainPackage.HistoryPanel;
import MainPackage.MessagePanel;

import com.twilio.sdk.*;
import com.twilio.sdk.resource.factory.*; 
import com.twilio.sdk.resource.instance.*; 
//import com.twilio.sdk.resource.list.*; 


public class MainWindow extends JFrame{
	
HistoryPanel history;
MessagePanel messager;
String fileName;
static ArrayList<String> complimentList;
	
public MainWindow(String title) throws IOException{
		
		super(title);
		setLayout(new FlowLayout());
		
		complimentList = new ArrayList<String>();
		messager = new MessagePanel("Text");
		history = new HistoryPanel("Text History");
		
		messager.setBackground(new Color(203,211,253));
		history.setBackground(new Color(193,201,253));
		

		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				//closeMethod();
			}
			
		});
		
		 
		add(messager);
		
		add(history);
		
		
	}





	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		try{
		
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		}
		catch(Exception e){
			System.out.println(e);
		}
		JFrame songLib = new MainWindow("Lazy Boyfriend");
		songLib.getContentPane().setBackground(new Color(203,211,253));
		songLib.setSize(700,650);
		songLib.setLocationRelativeTo(null);
		songLib.setResizable(false);
		songLib.setVisible(true);
		songLib.setDefaultCloseOperation(EXIT_ON_CLOSE);
		String fileName = (new java.io.File(".").getCanonicalPath())  + "\\Insults.txt";
		//String fileName = (new java.io.File(".").getCanonicalPath())  + "\\Compliments.txt";
		
		try{
			
			FileReader filereader = new FileReader(fileName);
			BufferedReader fileReader = new BufferedReader(filereader);
			readFromFile(fileReader);
			fileReader.close();
		
		}
		catch(FileNotFoundException ex){ 
			System.out.println(ex);
		}
		
	}

	public static void readFromFile(BufferedReader br) throws IOException{
		String compliment = br.readLine();
		
		while(compliment!=null){
			MainWindow.complimentList.add(compliment);
			compliment = br.readLine();
		}
		
		
	}


}
