package Model;

/**
 * This is model of table user and table login, focus on manipulating users
 */

import java.sql.ResultSet;

/**
 * uid-User IO
 * p-password
 * Return his position
 * return "" if user is not valid
 * (business model)
 */
public class UserModel {
    SqlHelper sqlHelper;
    public String[] checkUserPosition(String uid, String password){
        String position = "";
        String name = "";
        try{
            String sql = "SELECT NAME, POSITION FROM REG, LOGIN WHERE LOGIN.UID = REG.UID AND LOGIN.UID = ? AND LOGIN.PASSWORD = ?";
            String[] parameters = {uid, password};
            sqlHelper = new SqlHelper();
            ResultSet rs = sqlHelper.query(sql, parameters);
            if(rs.next()){
                name = rs.getString(1);
                position = rs.getString(2);
            }
        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally {
            sqlHelper.closeSource();

        }
        return new String[] {name, position};
    }
}
