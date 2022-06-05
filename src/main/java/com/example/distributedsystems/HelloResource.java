package com.example.distributedsystems;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Path("/insertData")
public class HelloResource{
    @GET
    @Produces("text/plain")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String hello(@QueryParam("muserid") String userid, @QueryParam("gluc") String gluc, @QueryParam("carbs") String carbs, @QueryParam("meds") String meds) throws SQLException {
        String jdbcURL = "jdbc:postgresql://localhost:5433/diabetes";
        String username = "postgres";
        String password = "minos";
        String insertData = "INSERT INTO diabetes_data(uid, gluc, carbs, med_dose,date) VALUES ("+userid +","+gluc+","+carbs+","+meds +",CURRENT_DATE)";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
            return "Data has been submitted successfully gluc level is: ";
        } catch (SQLException e) {
            return e.toString();
        }
    }
}

