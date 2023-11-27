
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * класс работы с базой данных
 */
public class DBConnection {
    public static final String DRIVER_NAME = "org.sqlite.JDBC";
    public static final String DB_FULL_NAME = "jdbc:sqlite:sqlite_persons.db";
    public static final String DB_NAME = "persons";
    public static final String DB_CONNECTED = "DB_CONNECTED";
    public static final String SELECT_NOT_ORDERED = "SELECT * FROM \'" + DB_NAME + "\'  ; ";
    public static final String DROP_TABLE = "DROP TABLE '" + DB_NAME + "' ;";
    public static final String CREATE_TABLE = "CREATE TABLE if not exists '" + DB_NAME + "' ('id' INTEGER PRIMARY KEY AUTOINCREMENT , 'name' text , 'age' INTEGER , 'gender' text );";
    public static final String INSERT_INTO_TABLE = "INSERT INTO '"  + DB_NAME +  "' ('name' , 'age' , 'gender' ) " +
            " VALUES (?,?,?);";
    public static final String DB_CONNECTION_ERROR = "DB_CONNECTION_ERROR";
    public static final String DRIVER_CONNECTED = "DRIVER_CONNECTED";
    public static final String DRIVER_ERROR = "DRIVER_ERROR";
    public static final String SQL_EXCEPTION = "SQL_EXCEPTION";
    public static final String TABLE_CREATED_OR_EXIST = "TABLE_CREATED_OR_EXIST";
    public static final String TABLE_END = "TABLE_END";
    public static final String DB_CLOSED = "DB_CLOSED";
    public static final String TABLE_DROP_AND_CREATED_NEW = "TABLE_DROP_AND_CREATED_NEW";
    //TODO написать sql запрос для удаления одной строки из бд и метод DELETE на java
    public static final String DELETE_ROW_FROM_TABLE = "DELETE FROM" + DB_NAME + "";
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    /**
     * подключиться к базе данных
     */
    public static void connectToDB() {
        connection = null;
        try {
            Class.forName(DRIVER_NAME);
            System.out.println(DRIVER_CONNECTED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(DRIVER_ERROR);
        }
        try {
            connection = DriverManager.getConnection(DB_FULL_NAME);
            statement = connection.createStatement();
            System.out.println(DB_CONNECTED);
        } catch (SQLException e) {
            System.out.println(DB_CONNECTION_ERROR);
        }
    }

    /**
     * создать таблицу, если она еще не создана
     */
    public static void createTable() {
        try {
            statement = connection.createStatement();
            statement.execute(CREATE_TABLE);
            System.out.println(TABLE_CREATED_OR_EXIST);
        } catch (SQLException sqlException) {
            System.out.println(SQL_EXCEPTION);
        }
    }

    /**
     * добавить запись в базу данных
     * @param p
     * @throws SQLException
     */
    public static void writeToDB(Person p) throws SQLException {
        try {
            String sql = INSERT_INTO_TABLE;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, p.getName());
            preparedStatement.setInt(2, p.getAge());
            preparedStatement.setString(3, p.getGender());
            preparedStatement.executeUpdate();
            System.out.println("Inserted person is - " + p.toString());
        } catch (Exception e) {
            System.out.println(SQL_EXCEPTION);
        }
    }
    public static void deleteFromDB(Person p) throws SQLException {
        try {
            PreparedStatement pstatement = connection.prepareStatement("DELETE FROM persons WHERE name = ?");
            pstatement.setString(1, p.getName());
            pstatement.setInt(2, p.getAge());
            pstatement.setString(3, p.getGender());
        }catch (Exception e){
            System.out.println(SQL_EXCEPTION);
        }
    }

    /**
     * пересоздание таблицы для удаления данных
     */
    public static void rebuildTable() {
        try {
            statement = connection.createStatement();
            statement.execute(DROP_TABLE);
            System.out.println(TABLE_DROP_AND_CREATED_NEW);
            createTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * прочитать все данные из базы данных и вывести их в консоль
     * @throws ClassNotFoundException
     * @throws SQLException
     */
//    public static void readFromDB() throws ClassNotFoundException, SQLException {
//        statement = connection.createStatement();
//        int count = 0;
//        resultSet = statement.executeQuery(SELECT_NOT_ORDERED);
//        System.out.println("SELECT ALL FROM DATABASE:");
//        while(resultSet.next()) {
//            int id = resultSet.getInt("id");
//            String  name = resultSet.getString("name");
//            int age = resultSet.getInt("age");
//            String gender = resultSet.getString("gender");
//            Person p = new Person(name, age, gender);
//            System.out.println("ID=" + id + " " + p.toString());
//            count++;
//        }
//        System.out.println("PRINTED ROWS COUNT = " + count);
//        System.out.println(TABLE_END);
//    }
    // TODO исправить метод так, чтобы он возвращал коллекцию записей из бд
    public static List<Person> readFromDB() throws ClassNotFoundException, SQLException {
        statement = connection.createStatement();
        int count = 0;
        resultSet = statement.executeQuery(SELECT_NOT_ORDERED);
        System.out.println("SELECT ALL FROM DATABASE:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String gender = resultSet.getString("gender");
            Person p = new Person(name, age, gender);
            System.out.println("ID=" + id + " " + p.toString());
            count++;
        }
        System.out.println("PRINTED ROWS COUNT = " + count);
        System.out.println(TABLE_END);
        List<Person> persons = null;
        try {
            Statement statement = connection.createStatement();
            {
                persons = new ArrayList<Person>();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM persons");
                while (resultSet.next()) {
                    persons.add((new Person(
                            resultSet.getString("name"),
                            resultSet.getInt("age"),
                            resultSet.getString("gender"))));
                }
            }
            System.out.println("PERSONS IN DATABASE: " + persons.toString());
        } catch (Exception e) {
            System.out.println("compilation is broken");
        }
        return persons;
    }

    /**
     * закрываем подключение к базе данных для экономии ресурсов
     * @throws SQLException
     */
    public static void closeDB() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
        System.out.println(DB_CLOSED);
    }
}
