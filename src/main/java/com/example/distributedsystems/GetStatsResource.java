package com.example.distributedsystems;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/get-stats")
public class GetStatsResource {
    @GET
    @Produces("text/plain")
    public String getstats(@QueryParam("dateS") String dateS, @QueryParam("dateF") String dateF, @QueryParam("type") String type) throws SQLException {
        String jdbcURL = "jdbc:postgresql://localhost:5433/diabetes";
        String username = "postgres";
        String password = "minos";
        String Query;
        switch(type) {
            case "All":
                Query = "SELECT * FROM diabetes_data WHERE date between '"+dateS+"' and '"+dateF+"' ORDER BY date";
                try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
                    PreparedStatement st = connection.prepareStatement(Query);
                    ResultSet rs = st.executeQuery();
                    String result ="<table><tr><th>Glucose</th><th>Carb intake</th><th>Medication</th><th>Date</th></tr>";
                    while (rs.next())
                    {
                        result = result+"<tr data-id="+rs.getString(1)+" ><td>"+rs.getString(3)+"</td><td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td></tr>";
                    }
                    result += "</table>";
                    rs.close();
                    st.close();
                    connection.close();
                    return result;
                } catch (SQLException e) {
                    return e.toString();
                }
            case "Glucose":
                Query = "SELECT gluc,date,AVG(carbs) FROM diabetes_data WHERE date between '"+dateS+"' and '"+dateF+"' ORDER BY date";
                try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
                    PreparedStatement st = connection.prepareStatement(Query);
                    ResultSet rs = st.executeQuery();
                    String result;
                    String xValues ="[";
                    String yValues ="[";
                    String avgLevel="";
                    while (rs.next())
                    {
                        xValues+="'"+rs.getString(2) +"',";
                        yValues+=""+rs.getString(1) +",";
                        avgLevel=rs.getString(3);
                    }
                    xValues += "]";
                    yValues += "]";
                    result = "<script type='text/javascript'>var ctx =document.getElementById('resultGraph').getContext('2d');  var myChart = new Chart(ctx, {\n" +
                            "  type: \"line\",\n" +
                            "  data: {\n" +
                            "    labels: "+xValues+",\n" +
                            "    datasets: [{\n" +
                            "      backgroundColor: \"rgba(0,0,0,1.0)\",\n" +
                            "      borderColor: \"rgba(0,0,0,0.1)\",\n" +
                            "      data:"+ yValues+"\n," +
                            "options: {\n" +
                            "        plugins: {\n" +
                            "            title: {\n" +
                            "                display: true,\n" +
                            "                text: 'Average Glucose Level: '\n"+avgLevel+
                            "            }\n" +
                            "        }\n" +
                            "    }"+
                            "    }]\n" +
                            "  },\n" +
                            "});</script>";
                    rs.close();
                    st.close();
                    connection.close();
                    return result;
                } catch (SQLException e) {
                    return e.toString();
                }

            default:
                Query = "SELECT carbs,date,AVG(carbs) FROM diabetes_data WHERE date between '"+dateS+"' and '"+dateF+"' ORDER BY date";
                try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
                    PreparedStatement st = connection.prepareStatement(Query);
                    ResultSet rs = st.executeQuery();
                    String result;
                    String xValues ="[";
                    String yValues ="[";
                    while (rs.next())
                    {
                        xValues+="'"+rs.getString(2) +"',";
                        yValues+=""+rs.getString(1) +",";
                    }
                    xValues += "]";
                    yValues += "]";
                    result = "<script type='text/javascript'>var ctx =document.getElementById('resultGraph').getContext('2d');  var myChart = new Chart(ctx, {\n" +
                            "  type: \"line\",\n" +
                            "  data: {\n" +
                            "    labels: "+xValues+",\n" +
                            "    datasets: [{\n" +
                            "      backgroundColor: \"rgba(0,0,0,1.0)\",\n" +
                            "      borderColor: \"rgba(0,0,0,0.1)\",\n" +
                            "      data:"+ yValues+"\n" +
                            "    }]\n" +
                            "  },\n" +
                            "});</script>";
                    rs.close();
                    st.close();
                    connection.close();
                    return result;
                } catch (SQLException e) {
                    return e.toString();
                }
        }

    }
}

