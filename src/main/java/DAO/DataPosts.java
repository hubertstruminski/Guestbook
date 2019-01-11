package DAO;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static DAO.ConnectorDatabase.connect;

public class DataPosts implements IDataPosts{

    public void insertRecord(String name, String message) throws IOException {
        Statement stmt = null;
        PreparedStatement insertStatement = null;
        try {
            Connection c = connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();

            insertStatement = c.prepareStatement("INSERT INTO public.posts(name, message) VALUES (?, ?);");

            insertStatement.setString(1, name);
            insertStatement.setString(2, message);

            insertStatement.addBatch();
            insertStatement.executeBatch();
            c.commit();

            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public Map<String, String> selectAllData(){
        Map<String, String> hashMap = new HashMap<>();
        Statement stmt = null;
        try {
            Connection c = connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT name, message FROM public.posts;");
            while ( rs.next() ) {
                String  txtName = rs.getString("name");
                String txtArea  = rs.getString("message");

                hashMap.put(txtName, txtArea);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
            System.exit(0);
        }

        return hashMap;
    }

}
