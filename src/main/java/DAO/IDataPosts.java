package DAO;

import java.io.IOException;

public interface IDataPosts {

    void insertRecord(String name, String message) throws IOException;
}
