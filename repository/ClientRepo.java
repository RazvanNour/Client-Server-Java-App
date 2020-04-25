package repository;


import domain.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ClientRepo implements IClientRepo {
    private JDBCUtils dbUtils;
    public ClientRepo(Properties props){
        dbUtils=new JDBCUtils(props);
    }

    @Override
    public Client findOne(Integer id) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Client where Id=? ")){
            preStmt.setInt(1,id);
            try(ResultSet resultSet=preStmt.executeQuery()){
                if(resultSet.next()){

                    String nume=resultSet.getString("Nume");
                    String prenume=resultSet.getString("Prenume");
                    int varsta=resultSet.getInt("Varsta");
                    String email=resultSet.getString("Email");
                    Client client=new Client(id,nume,prenume,varsta,email);
                    return client;
                }
            }
        }catch (SQLException ex ){
            System.out.println("Eroare baza de date"+ex);
        }
        return null;

    }

    @Override
    public List<Client> findAll() {
        Connection con=dbUtils.getConnection();
        List<Client> clienti=new ArrayList<>();
        try(PreparedStatement preparedStatement=con.prepareStatement("select * from Client")){
            try(ResultSet result=preparedStatement.executeQuery()){
                while(result.next()){
                    int id=result.getInt("Id");
                    String nume=result.getString("Nume");
                    String prenume=result.getString("Prenume");
                    String email=result.getString("email");
                    int varsta=result.getInt("varsta");
                    Client client=new Client(id,nume,prenume,varsta,email);
                    clienti.add(client);
                }
            }
        }catch (SQLException ex){
            System.out.println("Eroare baza de date:"+ex);
        }
        return clienti;
    }

    @Override
    public void Save(Client c) {
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into Client values (?,?,?,?,?)")) {
            preparedStatement.setInt(1, c.getId());
            preparedStatement.setString(2, c.getNume());
            preparedStatement.setString(4, c.getEmail());
            preparedStatement.setInt(5, c.getVarsta());
            preparedStatement.setString(3,c.getPrenume());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("eroare baza de date" + ex);
        }
    }


    @Override
    public void Update(Integer id,Client c) {
        Connection connection=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement("update Client set nume=?,prenume=?,email=?,varsta=? where Id=?"))
        {
            preparedStatement.setInt(5, id);
            preparedStatement.setString(1, c.getNume());
            preparedStatement.setString(3, c.getEmail());
            preparedStatement.setInt(4, c.getVarsta());
            preparedStatement.setString(2,c.getPrenume());
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }
    }

    @Override
    public void Delete(Integer id) {
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("delete from Client where Id=?")){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }
    }
}