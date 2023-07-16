
/*  Driver downloaded from https://dev.mysql.com/downloads/connector/j/
    I created a folder JdbcDriver and moved the jar file in
    File -> Project Structure -> Modules -> Add -> Jar or Directories ->  select the driver jar file

    To establish a connection between the database and Java application we need to follow 7/8 steps
        1. import the java.sql package - to use the classes and interfaces from the package
        2. load and register the driver ?
        3. establish/create a connection
        4. create the statement
        5. execute the statement/query
        6. process the result of the query
        7. close the statement and the connection

 */
import java.sql.*;


public class JdbcApp {
    public static void main(String[] args) {

        /*
            load and register the driver, as it throws a checked exception we need to catch it / or declare that the main method throws it
            initially I had Class.forName("com.mysql.jdbc.Driver");
            I got console message :
                Loading class `com.mysql.jdbc.Driver'. This is deprecated. The new driver class is `com.mysql.cj.jdbc.Driver'.
                The driver is automatically registered via the SPI and manual loading of the driver class is generally unnecessary.
                Step 2 is not needed anymore, but for practice I leave it , also prints a confirmation
         */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver successfully registered.");
        }catch (ClassNotFoundException e){
            System.out.println("Fail to load and register the driver");
            e.printStackTrace();
        }

        insertRecords();
        readRecords();



    }//main method
    public static void insertRecords() {
         /*
            create a connection
            Connection is an interface , we can not create objects from it
            we need to have a class or a method that gives an instance of connection??
            DriverManager class has getConnection() method which returns a Connection object
            getConnection() has three parameters
            url - to connect to the database,The url argument represents a data source, and indicates what type of JDBC connectivity we are using
            user & password for mySql
         */
        try {
            String url = "jdbc:mysql://localhost:3306/myDB";
            String username = "root";
            String password = "learner5";

            //create a Connection object
            Connection connection = DriverManager.getConnection(url, username, password);

            //create a statement
            Statement statement = connection.createStatement();

            //create a query
            String query = "INSERT INTO events VALUES ('2023-01-10', 'Music festival')";
            String query1 = "INSERT INTO events VALUES ('2022-11-11', 'Cannes festival')";
            String query2 = "INSERT INTO events VALUES ('2021-1-11', 'Oscars')";

            //to insert we use execute.update() which returns an int representing number of rows affected
            int numberOfRowsAffected = statement.executeUpdate(query);
            System.out.println("Query : record added!");
            System.out.println("Rows affected " + numberOfRowsAffected + ".");

            numberOfRowsAffected = statement.executeUpdate(query1);
            System.out.println("\nQuery : record added!");
            System.out.println("Rows affected " + numberOfRowsAffected + ".");

            numberOfRowsAffected = statement.executeUpdate(query2);
            System.out.println("\nQuery : record added!");
            System.out.println("Rows affected " + numberOfRowsAffected + ".");
            System.out.println();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }//insert record method


    public static void readRecords() {

        try {
            String url = "jdbc:mysql://localhost:3306/myDB";
            String username = "root";
            String password = "learner5";

            //create a Connection object
            Connection connection = DriverManager.getConnection(url, username, password);

            //create a statement
            Statement statement = connection.createStatement();

            //create a query
            String query = "SELECT * FROM events";

            //to execute the statement / read the records from the database we use executeQuery() - returns a dataset data we save it in a ResultSet object
            ResultSet result = statement.executeQuery(query);


            while (result.next()) {
                String record = result.getDate("eventDate") + " " + result.getString("eventDescription");
                System.out.println(record);
            }

        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }


}//class
