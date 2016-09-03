package cms.views;

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
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import cms.business.OperationsImpl;
import cms.business.TableReaderImpl;
import cms.controllers.CustomerPanelController;
import cms.interfaces.Operations;
import cms.interfaces.TableModelPersister;
import cms.interfaces.TableReader;
import cms.persistance.TableModelPersisterImpl;

public final class MainFrame {

	private JFrame frame;
	private TableModel tableModel;
	private JTable table;
	private JPanel controlPanel;
	private DetailedInfoPanel detailedPanel;

	private final TableModelPersister persister = new TableModelPersisterImpl();
	private final TableReader tableReader = new TableReaderImpl();
	private final MainController mainController = new MainController();

	public MainFrame() {
		createGui();
	}

	private void createGui() {
		frame = new JFrame("Customer Management System");
		frame.setSize(800, 600);
		frame.setLayout(new GridBagLayout());
		frame.addWindowListener(new WindowAdapter() { // NOSONAR : This anonymous class cannot be made a lambda, because it not implements a functional interface.
			@Override
			public void windowClosing(WindowEvent windowEvent) {
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
		tableModel = persister.load();
		table.addMouseListener(mainController);
		table.setModel(tableModel);
		table.removeColumn(table.getColumnModel().getColumn(5));
		table.removeColumn(table.getColumnModel().getColumn(4));
		table.getSelectionModel().addListSelectionListener(mainController);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.ipady = 180;
		c.weightx = 1.0;
		c.gridwidth = 3;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = 0;
		frame.add(paneTable, c);

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
		frame.add(controlPanel, c);

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
		frame.add(paneDetailedInfo, c);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private class MainController implements ActionListener, MouseListener, ListSelectionListener {

		private final Operations operations = new OperationsImpl();

		@Override
		public void actionPerformed(ActionEvent ae) {
			String command = ae.getActionCommand();
			if ("Add".equals(command)) {
				CustomerPanelController customerPanelController = new CustomerPanelController();
				operations.addNewCustomer(tableModel, customerPanelController.getCustomerPanel());
			} else if ("Edit".equals(command)) {
				editCustomer();
			} else if ("Delete".equals(command)) {
				int rowSelected = table.getSelectedRow();
				operations.deleteCustomer(tableModel, rowSelected);
				showDetailedInfo(-1);
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				editCustomer();
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// unneeded
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// unneeded
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// unneeded
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// unneeded
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			if (!lsm.isSelectionEmpty()) {
				int rowSelected = lsm.getMinSelectionIndex();
				showDetailedInfo(rowSelected);
			}
		}

		private void editCustomer() {
			int rowSelected = table.getSelectedRow();
			CustomerPanelController customerPanelController = new CustomerPanelController();
			operations.editCustomer(tableModel, customerPanelController.getCustomerPanel(), rowSelected);
			showDetailedInfo(rowSelected);
		}

		private void showDetailedInfo(int rowSelected) {
			if (rowSelected == -1) {
				detailedPanel.getCustomerNameTextField().setText("");
				detailedPanel.getlocationTextField().setText("");
				detailedPanel.getNotesTextArea().setText("");
				detailedPanel.getContractDateTextField().setText("");
				detailedPanel.getContractFileLinkLabel().setText("");
				detailedPanel.getImageLabel().setIcon(null);
			} else {
				String[] selectedClientDetails = tableReader.readRow(tableModel, rowSelected);

				detailedPanel.getCustomerNameTextField().setText(selectedClientDetails[0]);
				detailedPanel.getlocationTextField().setText(selectedClientDetails[1]);
				detailedPanel.getNotesTextArea().setText(selectedClientDetails[2]);
				detailedPanel.getContractDateTextField().setText(selectedClientDetails[3]);

				String contractPathString = selectedClientDetails[4];
				Path contractPath = Paths.get(contractPathString);
				String contractFileName = contractPath.getFileName().toString();
				detailedPanel.getContractFileLinkLabel().setText(contractFileName);
				detailedPanel.getContractFileLinkLabel().setName(contractPathString);

				String logoPath = selectedClientDetails[5];
				ImageIcon icon = new ImageIcon(logoPath);
				detailedPanel.getImageLabel().setIcon(icon);
				detailedPanel.getImageLabel().setName(logoPath);
			}
		}
	}
}