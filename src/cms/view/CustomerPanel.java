package cms.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CustomerPanel extends JPanel {
	
	private static final long serialVersionUID = -5775831575172282430L;	
	private static final String[] TOWNS = { "", "Sofia", "Plovdiv", "Varna", "Burgas" };
	
	private JLabel customerNameLabel;
	private JLabel locationLabel;
	private JLabel notesLabel;
	private JLabel contractDateLabel;
	private JLabel contractLabel;
	private JLabel logoLabel;
	private JLabel requiredLabel;
	
	private JTextField customerNameTextField;	
	@SuppressWarnings("rawtypes")
	private JComboBox locationTown;	
	private JTextArea notesTextArea;
	private JTextField contractDateTextField;	
	private JButton contractFileButton;		
	private JButton logoFileButton;
	private String contractFilePath;
	private String logoFilePath;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomerPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 2));		
		
		customerNameLabel = new JLabel("Name: *");
		locationLabel = new JLabel("Location: ");
		notesLabel = new JLabel("Notes: ");
		contractDateLabel = new JLabel("Date of signing the contract:        ");
		contractLabel = new JLabel("Contract: ");
		logoLabel = new JLabel("Logo: ");
		requiredLabel = new JLabel("    * - required field");
		
		customerNameTextField = new JTextField();
		customerNameTextField.setDocument(new JTextLimit(50));		
		
		locationTown = new JComboBox(TOWNS);		
		locationTown.setSelectedIndex(0);
		locationTown.addKeyListener(new NumKeyListener());
		
		notesTextArea = new JTextArea();
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setLineWrap(true);
		notesTextArea.setDocument(new JTextLimit(2000));
		notesTextArea.setFocusTraversalKeys(
				KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		JScrollPane notesPane = new JScrollPane();		
		notesPane.setViewportView(notesTextArea);
		notesPane.setPreferredSize(new Dimension(10, 1));		
		
		contractDateTextField = new JTextField();
		contractDateTextField.addFocusListener(new CustFocusListener());
		contractDateTextField.addKeyListener(new CtrlArrowKeyListener());
		
		contractFileButton = new JButton("Choose contract...");
		contractFileButton.addActionListener(new FileButtonsListener());
		logoFileButton = new JButton("Choose logo...");
		logoFileButton.addActionListener(new FileButtonsListener());
		
		contractFilePath = new String();
		logoFilePath = new String();
		
		panel.add(customerNameLabel);
		panel.add(customerNameTextField);		
		panel.add(locationLabel);
		panel.add(locationTown);
		panel.add(notesLabel);
		panel.add(notesPane);
		panel.add(contractDateLabel);
		panel.add(contractDateTextField);
		panel.add(contractLabel);
		panel.add(contractFileButton);
		panel.add(logoLabel);
		panel.add(logoFileButton);
		panel.add(requiredLabel);
		
		add(panel);
	}
	
	public JTextField getCustNameTextField() {
		return customerNameTextField;
	}
	
	@SuppressWarnings("rawtypes")
	public JComboBox getLocationTown() {
		return locationTown;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setLocationTown(String[] town) {
		this.locationTown = new JComboBox(town);
		this.locationTown.setSelectedIndex(1);
	}
	
	public JTextArea getNotesTextArea() {
		return notesTextArea;
	}

	public JTextField getContractDateTextField() {
		return contractDateTextField;
	}

	public String getContractFilePath() {
		return contractFilePath;
	}

	public void setContractFilePath(String contractFilePath) {
		this.contractFilePath = contractFilePath;
	}
	
	public String getLogoFilePath() {
		return logoFilePath;
	}
	
	public void setLogoFilePath(String logoFilePath) {
		this.logoFilePath = logoFilePath;
	}
	
	public JButton getContractFileButton() {
		return contractFileButton;
	}
	
	public JButton getLogoFileButton() {
		return contractFileButton;
	}

	
	private class NumKeyListener implements KeyListener {		
		
		public void keyTyped(KeyEvent e) {}			
		
		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyChar() == '1') {
				locationTown.setSelectedIndex(1);
			} else if (e.getKeyChar() == '2') {
				locationTown.setSelectedIndex(2);
			} else if (e.getKeyChar() == '3') {
				locationTown.setSelectedIndex(3);
			} else if (e.getKeyChar() == '4') {
				locationTown.setSelectedIndex(4);
			} else {
				locationTown.setEditable(true);
			}				
		}			
		
		public void keyPressed(KeyEvent e) {}
	}
	
	private class CtrlArrowKeyListener implements KeyListener {
		
		@Override
		public void keyPressed(KeyEvent ke) {
			if (ke.getKeyCode() == KeyEvent.VK_UP && ke.isControlDown()) {
				int value = 1;
				addDay(value);
			}
			else if (ke.getKeyCode() == KeyEvent.VK_DOWN && ke.isControlDown()) {
				int value = -1;
				addDay(value);
			}			
		}
		
		private void addDay(int value) {
			String content = contractDateTextField.getText();
			String format = "dd.MM.yyyy";
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sdf.setLenient(false);
			try {	
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(content));
			c.add(Calendar.DATE, value);
			contractDateTextField.setText(sdf.format(c.getTime()));
			} catch (ParseException pe) {
				//JOptionPane.showMessageDialog(null, INVALID_DATE);					
			}			
		}

		public void keyReleased(KeyEvent ke) {}
		
		public void keyTyped(KeyEvent ke) {}
		
	}
	
	private class CustFocusListener implements FocusListener {
		
		public void focusGained(FocusEvent e) {}

		@Override
		public void focusLost(FocusEvent e) {
			String content = contractDateTextField.getText();
			if (!content.isEmpty()) {
//				String fullFormat = "dd.MM.yyyy";
//				String shortFormat = "dd.MM";
//				SimpleDateFormat sdf = new SimpleDateFormat(fullFormat);
//				sdf.setLenient(false);
//				try {				
//					if (content.length() > 5) {
//						Date date = sdf.parse(content);
//						contractDateTextField.setText(sdf.format(date));
//					} else {
//						sdf = new SimpleDateFormat(shortFormat);
//						@SuppressWarnings("unused")
//						Date date = sdf.parse(content);
//						Calendar c = Calendar.getInstance();
//						String newDate = content + "." + c.get(Calendar.YEAR);
//						contractDateTextField.setText(newDate);
//					}				
//				} catch (ParseException pe) {
//					JOptionPane.showMessageDialog(null, INVALID_DATE);
//					contractDateTextField.requestFocus();
//				}			
			}			
		}		
	}
	
	private class FileButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			
			if (e.getActionCommand() == "Choose contract...") {
				int returnValue = fc.showOpenDialog(contractFileButton);			
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					setContractFilePath(fc.getSelectedFile().getAbsolutePath());					
				}
			}
			
			if (e.getActionCommand() == "Choose logo...") {
				int returnValue = fc.showOpenDialog(logoFileButton);			
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					setLogoFilePath(fc.getSelectedFile().getAbsolutePath());					
				}
			}				
		}		
	}			
}