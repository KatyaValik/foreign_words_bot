package Bot;

import org.sqlite.JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    }

    public HashMap<String, String> getAllWords() {
        try (Statement statement = this.connection.createStatement()) {
            HashMap<String, String> words = new HashMap<String, String>();
            ResultSet resultSet = statement.executeQuery("SELECT word, translation FROM words");
            while (resultSet.next()) {
                words.put(resultSet.getString("word"),
                        resultSet.getString("translation"));
            }
            return words;

        } catch (SQLException e) {
            e.printStackTrace();
            return new HashMap<String, String>();
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

    public HashMap<String, String> getWordsFromUser(Long userId){
        List<String> words = getForeignWordsFromUser(userId);
        HashMap<String, String> result = new HashMap<String, String>();
        for (String word: words){
            String translation = translate(word);
            result.put(word, translation);
        }
        return result;
    }

    public List<String> getForeignWordsFromUser(Long userId){
        try (Statement statement = this.connection.createStatement()) {
            List<String> words = new ArrayList<String>();
            ResultSet resultSet = statement.executeQuery("SELECT word FROM dictionaries WHERE userId = " + userId);
            while (resultSet.next()) {
                words.add(resultSet.getString("word"));
            }
            return words;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    public String translate(String word) {
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT translation FROM words WHERE word = ?")) {
                statement.setObject(1, word);
                ResultSet translation = statement.executeQuery();
                return translation.getString("translation");
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void addWordToUser(Long userId, String foreignWord){
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO dictionaries(`userId`, `word`, 'isLearned') " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, userId);
            statement.setObject(2, foreignWord);
            statement.setObject(3, true);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
