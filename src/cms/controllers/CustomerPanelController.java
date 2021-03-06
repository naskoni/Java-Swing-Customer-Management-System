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

import cms.views.CustomerPanel;

public class CustomerPanelController implements KeyListener, FocusListener, ActionListener {

	private final CustomerPanel customerPanel = new CustomerPanel();

	public CustomerPanelController() {
		customerPanel.getContractDateTextField().addFocusListener(this);
		customerPanel.getContractDateTextField().addKeyListener(this);
		customerPanel.getContractFileButton().addActionListener(this);
		customerPanel.getLogoFileButton().addActionListener(this);
		customerPanel.getLocationTown().addKeyListener(new NumKeyListener());
	}

	public CustomerPanel getCustomerPanel() {
		return customerPanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// unneeded
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		// unneeded
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

	@Override
	public void focusGained(FocusEvent e) {
		// unneeded
	}

	@Override
	public void focusLost(FocusEvent e) {
		String content = customerPanel.getContractDateTextField().getText();
		if (!content.isEmpty() && content.length() <= 5) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
				sdf.parse(content);
				Calendar c = Calendar.getInstance();
				String newDate = content + "." + c.get(Calendar.YEAR);
				customerPanel.getContractDateTextField().setText(newDate);
			} catch (ParseException pe) {
				// ignoring this exception
			}
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
			// ignoring this exception
		}
	}

	private class NumKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// unneeded
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyChar() == '1') {
				customerPanel.getLocationTown().setSelectedIndex(1);
			} else if (e.getKeyChar() == '2') {
				customerPanel.getLocationTown().setSelectedIndex(2);
			} else if (e.getKeyChar() == '3') {
				customerPanel.getLocationTown().setSelectedIndex(3);
			} else if (e.getKeyChar() == '4') {
				customerPanel.getLocationTown().setSelectedIndex(4);
			} else {
				customerPanel.getLocationTown().setEditable(true);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// unneeded
		}
	}
}
