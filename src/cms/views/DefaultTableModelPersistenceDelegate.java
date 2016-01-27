package cms.views;

import java.beans.DefaultPersistenceDelegate;
import java.beans.Encoder;
import java.beans.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class DefaultTableModelPersistenceDelegate extends DefaultPersistenceDelegate {    

    @Override
    protected void initialize(Class<?> type, Object oldInstance, Object newInstance, Encoder encoder)
    {
        DefaultTableModel model = (DefaultTableModel)oldInstance;

        Vector<String> columnNames = new Vector<String>(model.getColumnCount());

        for (int i = 0; i < model.getColumnCount(); i++)
        {
            columnNames.add(model.getColumnName(i));
        }

        Object[] columnNamesData = new Object[] { columnNames };
        encoder.writeStatement(new Statement(oldInstance, "setColumnIdentifiers", columnNamesData));

        @SuppressWarnings("rawtypes")
		Vector row = model.getDataVector();

        for (int i = 0; i < model.getRowCount(); i++)
        {
            Object[] rowData = new Object[] { row.get(i) };
            encoder.writeStatement(new Statement(oldInstance, "addRow", rowData));
        }
    }
}