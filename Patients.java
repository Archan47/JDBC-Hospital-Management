package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Patients {

    private final Connection connection;
    private final Scanner scanner;

    public Patients(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
    }


    public void addPatient(){
        scanner.nextLine();
        System.out.println("Enter Patient's Name : ");
        String name = scanner.nextLine();

        System.out.println("Enter Age : ");
        int age = scanner.nextInt();

        System.out.println("Enter Gender : ");
        String gender = scanner.next();


        try{
            String query = "INSERT INTO Patients(name,age,gender) VALUES(?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows>0){
                System.out.println("[ Patient ] : " + name + " added Successfully !!");
            }
            else {
                System.out.println("Failed to add patient");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatient(){
        String query = "Select * from Patients";

        try{

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("+------------+------------------------------+---------+---------+");
            System.out.println("| Patient Id |            Name              |   Age   |  Gender |");
            System.out.println("+------------+------------------------------+---------+---------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                System.out.printf("|%-12s|%-30s|%-9s|%-10s|\n", id, name, age,gender);
                System.out.println("+------------+------------------------------+---------+---------+");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean getPatientById(int id){
        String query = "Select * from Patients WHERE id = ? ";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
               return true;
            }
            else{
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




}
