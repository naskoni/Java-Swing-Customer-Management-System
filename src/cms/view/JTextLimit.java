package cms.view;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextLimit extends PlainDocument {	
	
	private static final long serialVersionUID = 6209019631723564996L;
	
	private int limit;
	
	public JTextLimit(int limit) {
		super();
		this.limit = limit;
	}	
	
	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException {
		if (str == null) {
			return;
		}
		
		super.insertString(offset, str, attr);
		
		if (getLength() > limit) {
			super.remove(limit, getLength() - limit);
		}
	}	
}
