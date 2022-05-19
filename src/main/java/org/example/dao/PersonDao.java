package org.example.dao;

import org.example.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
  private static int PEOPLE_COUNTER;

  private static final String url="jdbc:postgresql://localhost:5432/EducationalWebApp";

  private static  final String username="postgres";
  private static final String password="1234";


  private static Connection connection;
  static {

      try {
          Class.forName("org.postgresql.Driver");
      } catch (ClassNotFoundException ex) {
          ex.printStackTrace();
      }
      try {
          connection = DriverManager.getConnection(url, username, password);
      }catch (SQLException throwables){
          throwables.printStackTrace();
      }
  }


    public List<Person> index()  {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "Select * from person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {

                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
 return people;
  }




     public Person show(int id) throws SQLException {

    //      return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
            PreparedStatement preparedStatement= connection.prepareStatement("select * from person where  id =?");

            preparedStatement.setInt(1,id);
          ResultSet resultSet=  preparedStatement.executeQuery();
           resultSet.next();

        Person person= new Person();
         person.setId(resultSet.getInt("id"));
         person.setName(resultSet.getString("name"));
         person.setEmail(resultSet.getString("email"));

            return  person;
    }

    public static void save(Person person) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO Person VALUES(1, ?, ?)");

            preparedStatement.setString(1, person.getName());

            preparedStatement.setString(2, person.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement =
                null;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM Person WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }}

}
