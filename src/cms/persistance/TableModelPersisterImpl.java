package cms.persistance;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import cms.interfaces.TableModelPersister;
import cms.view.DefaultTableModelPersistenceDelegate;
import cms.view.OptionDialogs;

public class TableModelPersisterImpl implements TableModelPersister {
	
	private static final String FILE_CANNOT_BE_FOUND_OR_CREATED = "The file \"data.xml\" can not "
			+ "be created or accessed. The program can not continue.";
	private static final String[] COLUMN_NAMES = { "Name", "Location", "Notes",
			"Date of signing the contract", "Contract", "Logo" };
	
	private final File data = new File("data.xml");
	private final OptionDialogs optionDialogs = new OptionDialogs();
	
	public TableModelPersisterImpl() {		
	}
	
	@Override
	public TableModel load() {
		DefaultTableModel tableModel = null;
		
		if (data.exists()) {		
			try (InputStream is = new BufferedInputStream(new FileInputStream(data)); 
					XMLDecoder xd = new XMLDecoder(is)) {					
				tableModel = (DefaultTableModel) xd.readObject();
			} catch (FileNotFoundException fnfe) {
				optionDialogs.displayErrorMessage(FILE_CANNOT_BE_FOUND_OR_CREATED);				
			} catch (IOException ioe) {
				// ignoring this exception
			}
		}		
		
		if (tableModel == null) {
			tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		}

		return tableModel; 		
	}
		
	@Override
	public void save(TableModel tableModel) {		
		try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(data))) {
			encoder.setPersistenceDelegate(DefaultTableModel.class,
					new DefaultTableModelPersistenceDelegate());
			encoder.writeObject(tableModel);
		} catch (FileNotFoundException fnfe) {
			optionDialogs.displayErrorMessage(FILE_CANNOT_BE_FOUND_OR_CREATED);
			System.exit(0);
		} 	
	}
}
