package Model;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

/**
 * This model crud table
 * Can be reapplied to dish/client/takeout/..model
 */
public class CrudModel extends AbstractTableModel  {

    Vector<String> columns;
    Vector<Vector<String>> rows;
    Vector<String> temp;
    SqlHelper sqlHelper;
    ResultSetMetaData resultSetMetaData;
    String tableName;

    public CrudModel(String tableName){
        columns = new Vector<>();
        rows = new Vector<>();
        this.tableName = tableName;
    }

    public CrudModel query(String sql, String[] parameters){

        try{
            sqlHelper = new SqlHelper();
            ResultSet rs = sqlHelper.query(sql, parameters);

            resultSetMetaData = rs.getMetaData();

            for(int i = 0; i < resultSetMetaData.getColumnCount(); i++){
                this.columns.add(resultSetMetaData.getColumnName(i + 1));
            }

            while (rs.next()){
                temp = new Vector<>();
                for(int i = 0; i < resultSetMetaData.getColumnCount(); i++){
                    temp.add(rs.getString(i + 1));//better solution
                }
                rows.add(temp);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            sqlHelper.closeSource();
        }
        return this;

    }

    public boolean operate(String sql, String[] parameters){
        sqlHelper = new SqlHelper();
        return sqlHelper.operate(sql, parameters);
    }


    public CrudModel getLatestModel(){
        String sql = "SELECT * FROM " + tableName;
        return this.query(sql, new String[] {});//分页显示
    }


    @Override
    public int getRowCount() {
        return this.rows.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rows.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return this.columns.get(column);
    }
}
