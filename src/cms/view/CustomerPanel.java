package cms.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CustomerPanel extends JPanel {
	
	private static final long serialVersionUID = -5775831575172282430L;	
	private static final String[] TOWNS = { "", "Sofia", "Plovdiv", "Varna", "Burgas" };
	private static final int CUSTOMER_NAME_LIMIT = 50;
	private static final int NOTES_LIMIT = 2000;
	
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
		customerNameTextField.setDocument(new JTextLimit(CUSTOMER_NAME_LIMIT));		
		
		locationTown = new JComboBox(TOWNS);		
		locationTown.setSelectedIndex(0);		
		
		notesTextArea = new JTextArea();
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setLineWrap(true);
		notesTextArea.setDocument(new JTextLimit(NOTES_LIMIT));
		notesTextArea.setFocusTraversalKeys(
				KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		JScrollPane notesPane = new JScrollPane();		
		notesPane.setViewportView(notesTextArea);
		notesPane.setPreferredSize(new Dimension(10, 1));		
		
		contractDateTextField = new JTextField();
		contractFileButton = new JButton("Choose contract...");
		logoFileButton = new JButton("Choose logo...");
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
	
	public JTextField getCustomerNameTextField() {
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
		return logoFileButton;
	}
}