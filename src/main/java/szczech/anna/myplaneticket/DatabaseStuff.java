package szczech.anna.myplaneticket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseStuff {

    public static Connection connectWithDB() throws SQLException {
        Connection connectionWithDB = DriverManager.getConnection("jdbc:sqlite:mojabaza.db");
        Statement statement = connectionWithDB.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `BILETY` (\n"
                + "	`Lp`	INTEGER,\n"
                + "	`Skad`	TEXT,\n"
                + "	`Dokad`	TEXT,\n"
                + "	`Data`	TEXT,\n"
                + "	`Cena`	TEXT,\n"
           //     + "	'Data_cennika` DATETIME DEFAULT CURRENT_TIMESTAMP,\n"
                + "	PRIMARY KEY(`Lp`)\n"
                + "      );");

        return connectionWithDB;
    }

    public static void addToDB(Connection connectionWithDB, VariableInDB connection) throws SQLException {
        PreparedStatement statement = connectionWithDB.prepareStatement("insert into BILETY (Skad, Dokad, Data, Cena) values(?,?,?,?)");
        statement.setString(1, connection.getFromWhere());
        statement.setString(2, connection.getWhere());
        statement.setString(3, connection.getDate());
        statement.setString(4, connection.getPrice());
        statement.executeUpdate();
    }
}
