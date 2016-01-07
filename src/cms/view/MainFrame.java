package cms.view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cms.business.Operations;
import cms.controllers.CustomerPanelController;
import cms.persistance.TableModelPersister;

public final class MainFrame {
	
	private JFrame mainFrame;
	private DefaultTableModel tableModel;
	private JTable table;
	private JPanel controlPanel;
	private DetailedInfoPanel detailedPanel;
	
	private final TableModelPersister tableModelPersister = new TableModelPersister();
	private final MainController mainController = new MainController();	

	public MainFrame() {
		createGui();		
	}		
	
	private void createGui(){
		mainFrame = new JFrame("Customer Management System");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new GridBagLayout());		
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent){
				System.exit(0);
			}        
		});
		
		table = new JTable() {			
			private static final long serialVersionUID = 147848978001448883L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    };
		};
		
		JScrollPane paneTable = new JScrollPane();		
		paneTable.setViewportView(table);		
		tableModel = tableModelPersister.loadTableModel();
		table.addMouseListener(mainController);		
		table.setModel(tableModel);
		table.removeColumn(table.getColumnModel().getColumn(5));
		table.removeColumn(table.getColumnModel().getColumn(4));
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;        
		c.ipady = 180;
		c.weightx = 1.0;
		c.gridwidth = 3;        
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		mainFrame.add(paneTable, c);
		
		JButton buttonAdd = new JButton("Add");		
		buttonAdd.addActionListener(mainController);
		JButton buttonEdit = new JButton("Edit");		
		buttonEdit.addActionListener(mainController);
		JButton buttonDelete = new JButton("Delete");
		buttonDelete.addActionListener(mainController);
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());		
		controlPanel.add(buttonAdd);
		controlPanel.add(buttonEdit);		
		controlPanel.add(buttonDelete);
		c.ipady = 0;
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		mainFrame.add(controlPanel, c);
		
		detailedPanel = new DetailedInfoPanel();
		JScrollPane paneDetailedInfo = new JScrollPane();		
		paneDetailedInfo.setViewportView(detailedPanel);
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 300;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_END;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 2; 
		mainFrame.add(paneDetailedInfo, c);		
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true); 
	}	

	private class MainController implements ActionListener, MouseListener {
		
		private final Operations operations = new Operations();
		private final CustomerPanelController customerPanelController = 
				new CustomerPanelController();
		
		public void actionPerformed(ActionEvent ae) {
			String command = ae.getActionCommand();  
			if (command.equals("Add"))  {				
				//CustomerPanel panel = new CustomerPanel();
				operations.addNewCustomer(tableModel, customerPanelController.getCustomerPanel());	
			} else if (command.equals("Edit"))  {
				this.editCustomer();
			} else if (command.equals("Delete")) {
				int rowSelected = table.getSelectedRow();
				operations.deleteCustomer(tableModel, rowSelected);
				this.showDetailedInfo(tableModel, detailedPanel, -1);
			}  	
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				this.editCustomer();
			} else if (e.getClickCount() == 1) {				
				int rowSelected = table.getSelectedRow();
				this.showDetailedInfo(tableModel, detailedPanel, rowSelected);
			}
		}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}
		
		private void editCustomer() {
			int rowSelected = table.getSelectedRow();
			CustomerPanel customerPanel = new CustomerPanel();
			operations.editCustomer(tableModel, customerPanel, rowSelected);
			this.showDetailedInfo(tableModel, detailedPanel, rowSelected);
		}
		
		private void showDetailedInfo(DefaultTableModel tableModel, 
				DetailedInfoPanel detailedPanel, int rowSelected) {
			if (rowSelected == -1) {
				detailedPanel.getCustomerNameTextField().setText("");
				detailedPanel.getlocationTextField().setText("");
				detailedPanel.getNotesTextArea().setText("");
				detailedPanel.getContractDateTextField().setText("");
				detailedPanel.getContractFileLinkLabel().setText("");
				detailedPanel.getImageLabel().setIcon(null);
				
			} else {
				String[] SelectedClientDetails = operations.readRow(tableModel, rowSelected);
				
				detailedPanel.getCustomerNameTextField().setText(SelectedClientDetails[0]);
				detailedPanel.getlocationTextField().setText(SelectedClientDetails[1]);
				detailedPanel.getNotesTextArea().setText(SelectedClientDetails[2]);
				detailedPanel.getContractDateTextField().setText(SelectedClientDetails[3]);
				
				String contractPathString = SelectedClientDetails[4];
				Path contractPath = Paths.get(contractPathString);
				String contractFileName = contractPath.getFileName().toString();
				detailedPanel.getContractFileLinkLabel().setText(contractFileName);		
				detailedPanel.getContractFileLinkLabel().setName(contractPathString);
				
				String logoPath = SelectedClientDetails[5];		
				ImageIcon icon = new ImageIcon(logoPath);		
				detailedPanel.getImageLabel().setIcon(icon);		
				detailedPanel.getImageLabel().setName(logoPath);
			}		
		}
	}	
}