import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        /**
         * подключиться к базе данных
         */
        DBConnection.connectToDB();
        /**
         * создать таблицу, если она еще не создана
         */
        DBConnection.createTable();
        /**
         * создаем экземпляры класса Person(людей с данными о них в виде имени, возраста и пола)
         */
        Person person1 = new Person("Alexander", 31, "M");
        Person person2 = new Person("Artem", 18, "M");
        Person person3 = new Person("Natalia", 12, "F");
        Person person4 = new Person("Sophie", 31, "F");
        /**
         * записываем информацию о людях в базу данных
         */
        DBConnection.writeToDB(person1);
        DBConnection.writeToDB(person2);
        DBConnection.writeToDB(person3);
        DBConnection.writeToDB(person4);
        /**
         * читаем всё из базы данных
         */
        DBConnection.readFromDB();
        /**
         * то что ниже можно раскоментировать и после каждого запуска будет очищаться таблица
         */
        DBConnection.deleteFromDB(person1);

        DBConnection.readFromDB();
        DBConnection.rebuildTable();
        DBConnection.closeDB();

    }
}