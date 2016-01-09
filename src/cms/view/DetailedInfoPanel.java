package cms.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DetailedInfoPanel extends JPanel {
	
	private JLabel customerNameLabel;
	private JLabel locationLabel;
	private JLabel notesLabel;
	private JLabel contractDateLabel;
	private JLabel contractLabel;
	private JLabel contractFileLinkLabel;
	private JLabel logoLabel;
	
	private JTextField customerNameTextField;
	private JTextField locationTextField;	
	private JTextArea notesTextArea;
	private JTextField contractDateTextField;
	
	private ImageLabel imageLabel;
	
	public DetailedInfoPanel() {
		JPanel commonPanel = new JPanel();
		commonPanel.setLayout(new BoxLayout(commonPanel, BoxLayout.X_AXIS));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));		
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));		
		
		customerNameLabel = new JLabel("Name of customer: ");
		
		locationLabel = new JLabel("Location: ");		
		
		notesLabel = new JLabel("Notes: ");
		
		contractDateLabel = new JLabel("Date of signing the contract: ");
		
		contractLabel = new JLabel("Contract: (click to view)");
		
		logoLabel = new JLabel("Logo: (click to enlarge)");
		
		contractFileLinkLabel = new JLabel();
		contractFileLinkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contractFileLinkLabel.setForeground(Color.blue);
		contractFileLinkLabel.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            try {
	                Desktop.getDesktop().open(
	                		new File(contractFileLinkLabel.getName()));
	            } catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
	        }
	    });
		
		imageLabel = new ImageLabel();
		imageLabel.setPreferredSize(new Dimension(200, 200));
		imageLabel.setSize(new Dimension(200, 200));		
		imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		imageLabel.addMouseListener(new MouseAdapter() {
			@Override
	        public void mouseClicked(MouseEvent e) {
	            try {
	                Desktop.getDesktop().open(
	                		new File(imageLabel.getName()));
	            } catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
	        }
		});
		
		customerNameTextField = new JTextField();	
		customerNameTextField.setEditable(false);
		customerNameTextField.setBackground(Color.white);		
		
		locationTextField = new JTextField();
		locationTextField.setEditable(false);
		locationTextField.setBackground(Color.white);
		
		notesTextArea = new JTextArea();
		notesTextArea.setEditable(false);
		notesTextArea.setWrapStyleWord(true);
		notesTextArea.setLineWrap(true);
		JScrollPane notesPane = new JScrollPane();		
		notesPane.setViewportView(notesTextArea);
		notesPane.setPreferredSize(new Dimension(400, 180));
		
		contractDateTextField = new JTextField();		
		contractDateTextField.setEditable(false);
		contractDateTextField.setBackground(Color.white);		
		
		leftPanel.add(customerNameLabel);		
		leftPanel.add(customerNameTextField);
		leftPanel.add(locationLabel);
		leftPanel.add(locationTextField);
		leftPanel.add(notesLabel);
		leftPanel.add(notesPane);		
		
		rightPanel.add(logoLabel);
		rightPanel.add(imageLabel);
		rightPanel.add(contractDateLabel);
		rightPanel.add(contractDateTextField);
		rightPanel.add(contractLabel);
		rightPanel.add(contractFileLinkLabel);
		
		commonPanel.add(leftPanel);
		commonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
		commonPanel.add(rightPanel);
		
		add(commonPanel);
	}
	
	public JTextField getCustomerNameTextField() {
		return customerNameTextField;
	}

	public JTextField getlocationTextField() {
		return locationTextField;
	}

	public JTextArea getNotesTextArea() {
		return notesTextArea;
	}

	public JTextField getContractDateTextField() {
		return contractDateTextField;
	}
	
	public JLabel getContractFileLinkLabel() {
		return contractFileLinkLabel;
	}
	
	public ImageLabel getImageLabel() {
		return imageLabel;
	}	
}