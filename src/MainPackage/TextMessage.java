package MainPackage;
import java.util.*;

public class TextMessage {
public String text;
public String to;
public String from;
public Calendar time;

	public TextMessage(String Text, String To, String From, Calendar Time){
		text = Text;
		to = To;
		from = From;
		time = Time;
	}
}
