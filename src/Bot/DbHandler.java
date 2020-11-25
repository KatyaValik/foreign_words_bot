package Bot;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DbHandler {

    private static final String CON_STR = "jdbc:sqlite:.\\src\\Bot\\words.db";
    private static DbHandler instance = null;

    public static synchronized DbHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DbHandler();
        return instance;
    }

    private Connection connection;

    private DbHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
        System.out.println(getAllWords());
    }

    public List<String> getAllWords() {
        try (Statement statement = this.connection.createStatement()) {
            List<String> words = new ArrayList<String>();
            ResultSet resultSet = statement.executeQuery("SELECT word, translation FROM words");
            while (resultSet.next()) {
                words.add(String.format("%s - %s",
                        resultSet.getString("word"),
                        resultSet.getString("translation")));
            }
            return words;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void addWord(String word, String translation) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO words(`word`, `translation`) " +
                        "VALUES(?, ?)")) {
            statement.setObject(1, word);
            statement.setObject(2, translation);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWord(String word) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM words WHERE word = ?")) {
            statement.setObject(1, word);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getWordsFromUser(Long userId){
        try (Statement statement = this.connection.createStatement()) {
            List<String> words = new ArrayList<String>();
            ResultSet resultSet = statement.executeQuery("SELECT word FROM users WHERE id = " + userId);
            while (resultSet.next()) {
                words.add(resultSet.getString("word"));
            }
            return words;

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void addWordToUser(Long userId, String word){
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO words(`id`, `word`, 'isLearned') " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, userId);
            statement.setObject(2, word);
            statement.setObject(3, true);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
