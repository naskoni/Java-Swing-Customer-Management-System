package cms.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFileChooser;

import cms.view.CustomerPanel;

public class CustomerPanelController implements KeyListener, FocusListener, ActionListener {
	
	private final CustomerPanel customerPanel = new CustomerPanel();

	public CustomerPanelController() {
		customerPanel.getLocationTown().addKeyListener(this);
		customerPanel.getContractDateTextField().addFocusListener(this);
		customerPanel.getContractDateTextField().addKeyListener(this);
		customerPanel.getContractFileButton().addActionListener(this);
		customerPanel.getLogoFileButton().addActionListener(this);
	}
	
	public CustomerPanel getCustomerPanel() {
		return customerPanel;
	}
	
	public void keyTyped(KeyEvent e) {}			
	
	@Override
	public void keyReleased(KeyEvent ke) {
//		if (ke.getKeyChar() == '1') {
//			customerPanel.getLocationTown().setSelectedIndex(1);
//		} else if (ke.getKeyChar() == '2') {
//			customerPanel.getLocationTown().setSelectedIndex(2);
//		} else if (ke.getKeyChar() == '3') {
//			customerPanel.getLocationTown().setSelectedIndex(3);
//		} else if (ke.getKeyChar() == '4') {
//			customerPanel.getLocationTown().setSelectedIndex(4);
//		} else {
//			customerPanel.getLocationTown().setEditable(true);
//		}				
	}				
	
	@Override
	public void keyPressed(KeyEvent ke) {
		if (ke.getKeyCode() == KeyEvent.VK_UP && ke.isControlDown()) {
			int value = 1;
			addDay(value);
		} else if (ke.getKeyCode() == KeyEvent.VK_DOWN && ke.isControlDown()) {
			int value = -1;
			addDay(value);
		}			
	}
	
	private void addDay(int value) {
		String content = customerPanel.getContractDateTextField().getText();
		String format = "dd.MM.yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		try {	
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(content));
		c.add(Calendar.DATE, value);
		customerPanel.getContractDateTextField().setText(sdf.format(c.getTime()));
		} catch (ParseException pe) {								
		}			
	}		
	
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) {
		String content = customerPanel.getContractDateTextField().getText();
		if (!content.isEmpty()) {
//			String fullFormat = "dd.MM.yyyy";
//			String shortFormat = "dd.MM";
//			SimpleDateFormat sdf = new SimpleDateFormat(fullFormat);
//			sdf.setLenient(false);
//			try {				
//				if (content.length() > 5) {
//					Date date = sdf.parse(content);
//					contractDateTextField.setText(sdf.format(date));
//				} else {
//					sdf = new SimpleDateFormat(shortFormat);
//					@SuppressWarnings("unused")
//					Date date = sdf.parse(content);
//					Calendar c = Calendar.getInstance();
//					String newDate = content + "." + c.get(Calendar.YEAR);
//					contractDateTextField.setText(newDate);
//				}				
//			} catch (ParseException pe) {
//				JOptionPane.showMessageDialog(null, INVALID_DATE);
//				contractDateTextField.requestFocus();
//			}			
		}			
	}		

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		
		if (e.getActionCommand() == "Choose contract...") {
			int returnValue = fc.showOpenDialog(customerPanel.getContractFileButton());			
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				customerPanel.setContractFilePath(fc.getSelectedFile().getAbsolutePath());					
			}
		}
		
		if (e.getActionCommand() == "Choose logo...") {
			int returnValue = fc.showOpenDialog(customerPanel.getLogoFileButton());			
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				customerPanel.setLogoFilePath(fc.getSelectedFile().getAbsolutePath());					
			}
		}				
	}		
}			

