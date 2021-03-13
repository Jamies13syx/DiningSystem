package Model;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

public class ReportModel extends AbstractTableModel {
    String [] columns;
    Vector<String[]> rows;

    public ReportModel(String [] columnName, Vector<String[]> data){
        columns = columnName;
        rows = data;
    }


    @Override
    public int getRowCount() {
        return this.rows.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return this.columns[column];
    }
}
