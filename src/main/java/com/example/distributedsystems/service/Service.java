package com.example.distributedsystems.service;

import java.sql.*;

public class Service {
    protected String jdbcURL = "jdbc:postgresql://localhost:5433/diabetes";
    protected String username = "postgres";
    protected String password = "minos";
    protected String Query;
    public String read(String dateS, String dateF, String type){
        switch(type) {
            case "All":
                Query = "SELECT * FROM diabetes_data WHERE date between '"+dateS+"' and '"+dateF+"' ORDER BY date";
                try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
                    PreparedStatement st = connection.prepareStatement(Query);
                    ResultSet rs = st.executeQuery();
                    String result ="<table><tr><th>Glucose</th><th>Carb intake</th><th>Medication</th><th>Date</th></tr>";
                    while (rs.next())
                    {
                        result = result+"<tr data-id="+rs.getString(1)+"><td class='gluc'>"+rs.getString(3)+"</td><td class='carb'>"+rs.getString(4)+"</td><td class='med'>"+rs.getString(5)+"</td><td>"+rs.getString(6)+"</td></tr>";
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
                Query = "SELECT gluc,date FROM diabetes_data WHERE date between '"+dateS+"' and '"+dateF+"' ORDER BY date";
                try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
                    PreparedStatement st = connection.prepareStatement(Query);
                    ResultSet rs = st.executeQuery();
                    String result;
                    String xValues ="[";
                    String yValues ="[";
                    Double total = Double.valueOf(0);
                    Integer count =0 ;
                    Double average;
                    while (rs.next())
                    {
                        count++;
                        xValues+="'"+rs.getString(2) +"',";
                        yValues+=""+rs.getString(1) +",";
                        total += Double.parseDouble(rs.getString(1));
                    }
                    average = total/count;
                    xValues += "]";
                    yValues += "]";
                    result = "<script type='text/javascript'>var ctx =document.getElementById('resultGraph').getContext('2d');  var myChart = new Chart(ctx, {\n" +
                            "  type: \"line\",\n" +
                            "  data: {\n" +
                            "    labels: "+xValues+",\n" +
                            "    datasets: [{\n" +
                            "      backgroundColor: \"rgba(0,0,0,1.0)\",\n" +
                            "      borderColor: \"rgba(0,0,0,0.1)\",\n" +
                            "      data:"+ yValues+",\n" +
                            "    }]\n" +
                            "    },\n"+
                            "options: {\n" +
                            " plugins: {\n" +
                            "            title: {\n" +
                            "                display: true,\n" +
                            "                text: 'Average Glucose Level: "+average.toString()+"'"+
                            "            }\n" +
                            "        }\n" +
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
                Query = "SELECT carbs,date FROM diabetes_data WHERE date between '"+dateS+"' and '"+dateF+"' ORDER BY date";
                try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
                    PreparedStatement st = connection.prepareStatement(Query);
                    ResultSet rs = st.executeQuery();
                    String result;
                    String xValues ="[";
                    String yValues ="[";
                    Double total = Double.valueOf(0);
                    Integer count =0 ;
                    Double average;
                    while (rs.next())
                    {
                        count++;
                        xValues+="'"+rs.getString(2) +"',";
                        yValues+=""+rs.getString(1) +",";
                        total += Double.parseDouble(rs.getString(1));
                    }
                    average = total/count;
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
                            "options: {\n" +
                            " plugins: {\n" +
                            "            title: {\n" +
                            "                display: true,\n" +
                            "                text: 'Average Carb Intake: "+average.toString()+"'"+
                            "            }\n" +
                            "        }\n" +
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
    public String update(String recordId ,String gluc, String carbs, String meds){
        String insertData = "UPDATE diabetes_data SET  gluc="+gluc+", carbs="+carbs+", med_dose="+meds+" WHERE eid="+recordId;
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
            return "Data has been updated";
        } catch (SQLException e) {
            return e.toString();
        }
    }
    public String delete(String recordId){
        String insertData = "DELETE FROM diabetes_data WHERE eid="+recordId;
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
            return "Data has been deleted !";
        } catch (SQLException e) {
            return e.toString();
        }
    }
    public String insert(String userid, String gluc, String carbs, String meds){
        System.out.println(userid);
        String insertData = "INSERT INTO diabetes_data(uid, gluc, carbs, med_dose,date) VALUES ("+userid +","+gluc+","+carbs+","+meds +",CURRENT_DATE)";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
            connection.close();
            return "Data has been Inserted ";
        } catch (SQLException e) {
            return e.toString();
        }
    }
}
