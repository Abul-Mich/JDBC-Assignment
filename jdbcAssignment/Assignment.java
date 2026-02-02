package jdbcAssignment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import env.Utils;

public class Assignment {

    public static void main(String[] args) {
        String var1 = "jdbc:postgresql://localhost:5432/jdbc";
        String var2 = "postgres";
        String var3 = Utils.getPassword();
        Connection var4 = null;
        Statement var5 = null;
        ResultSet var6 = null;

        try {
            Class.forName("org.postgresql.Driver");
            var4 = DriverManager.getConnection(var1, var2, var3);
            System.out.println("Connected to PostgreSQL database successfully!");
            System.out.println("Username: Michel Daher Geha\n");
            var5 = var4.createStatement();

            var5.executeUpdate("CREATE TABLE IF NOT EXISTS personnes (" +
                    "nom VARCHAR(20)," +
                    "age INT )");

            var6 = var5.executeQuery("SELECT * FROM personnes");

            while (var6.next()) {
                String var7 = var6.getString("nom");
                int var8 = var6.getInt("age");
                System.out.println(var7 + " a " + var8 + " ans");
            }

            System.out.println("\nResetting the table personnes and inserting new rows...\n");

            var5.executeUpdate("TRUNCATE TABLE personnes");

            var5.executeUpdate("INSERT INTO personnes (nom, age)" +
                    "VALUES ('michel daher geha', 21)," +
                    "('will smith', 50);");

            var6 = var5.executeQuery("SELECT * FROM personnes");

            while (var6.next()) {
                String var7 = var6.getString("nom");
                int var8 = var6.getInt("age");
                System.out.println(var7 + " a " + var8 + " ans");
            }
        } catch (ClassNotFoundException var19) {
            System.out.println("PostgreSQL JDBC Driver not found!");
            var19.printStackTrace();
        } catch (SQLException var20) {
            System.out.println("Connection failed!");
            var20.printStackTrace();
        } finally {
            try {
                if (var6 != null) {
                    var6.close();
                }

                if (var5 != null) {
                    var5.close();
                }

                if (var4 != null) {
                    var4.close();
                }

                System.out.println("Connection closed.");
            } catch (SQLException var18) {
                var18.printStackTrace();
            }
        }

    }
}
