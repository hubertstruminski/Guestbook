package DAO;

import Model.Post;

import java.io.IOException;
import java.util.List;

public interface IDataPosts {

    void insertRecord(String name, String message, String data) throws IOException;
    List<Post> selectAllData();
}
