import java.sql.*;

public class Repository {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/calculator";
    private static final String DRIVER_CLASS_NAME = "org.postgresql.Driver";

    public static void printValuesInDatabase(){
        Connection connection= null;
        Statement statement = null;
        try{
            Class.forName(DRIVER_CLASS_NAME);
            connection = DriverManager.getConnection(URL,USER,PASSWORD);

           statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery("Select * from calculator_result;");

           while(resultSet.next()){
               int id = resultSet.getInt("id");
               double result = resultSet.getDouble("result_value");
               System.out.println("ID: " + id  + " Value: " + result);
           }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void saveValueToDatabase(double resultToSave) {
        Connection connection= null;
        Statement statement = null;
        try{
            Class.forName(DRIVER_CLASS_NAME);
            connection = DriverManager.getConnection(URL,USER,PASSWORD);

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("Select * from calculator_result;");


            int lastId = 0;
            while(resultSet.next()){
                lastId = resultSet.getInt("id");
            }

            int idOfCurrentValue = lastId+1;

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO calculator_result values (?, ?);");
            preparedStatement.setInt(1,idOfCurrentValue);
            preparedStatement.setDouble(2,resultToSave);

            preparedStatement.addBatch();
            preparedStatement.executeBatch();


        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
