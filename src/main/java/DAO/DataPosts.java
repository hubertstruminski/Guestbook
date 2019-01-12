package DAO;

import Model.Post;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static DAO.ConnectorDatabase.connect;

public class DataPosts implements IDataPosts{

    public void insertRecord(String name, String message, String data) throws IOException {
        Statement stmt = null;
        PreparedStatement insertStatement = null;
        try {
            Connection c = connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();

            insertStatement = c.prepareStatement("INSERT INTO public.posts(name, message, data) VALUES (?, ?, ?);");

            insertStatement.setString(1, name);
            insertStatement.setString(2, message);
            insertStatement.setString(3, data);

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

    public List<Post> selectAllData(){
        List<Post> result = new ArrayList<>();

        Statement stmt = null;
        try {
            Connection c = connect();
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT name, message, data FROM public.posts;");
            while ( rs.next() ) {
                String  txtName = rs.getString("name");
                String txtArea  = rs.getString("message");
                String txtData = rs.getString("data");

                result.add(new Post(txtName, txtArea, txtData));
            }

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName()+ ": " + e.getMessage());
            System.exit(0);
        }

        return result;
    }

}
