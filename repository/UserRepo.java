package repository;

import domain.Utilizator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepo implements IUserRepo {
    private JDBCUtils jdbCutils;
    public UserRepo(Properties properties){
        jdbCutils=new JDBCUtils(properties);
    }

    @Override
    public Utilizator findOne(Integer id) {
        Connection con=jdbCutils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("select * from User where id=?")){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    String username=resultSet.getString("username");
                    String password=resultSet.getString("password");
                    Utilizator utilizator =new Utilizator(id,username,password);
                    return utilizator;
                }
            }
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }
        return null;
    }

    @Override
    public List<Utilizator> findAll() {
        Connection con=jdbCutils.getConnection();
        List<Utilizator> useri=new ArrayList<>();
        try(PreparedStatement preparedStatement=con.prepareStatement("select *from User")){
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int id=resultSet.getInt("Id");
                    String username=resultSet.getString("username");
                    String password=resultSet.getString("password");
                    Utilizator utilizator =new Utilizator(id,username,password);
                    useri.add(utilizator);
                }
            }
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }
        return useri;
    }

    @Override
    public void Save(Utilizator c) {
        Connection connection=jdbCutils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement("insert into User values(?,?,?)")){
            preparedStatement.setInt(1,c.getId());
            preparedStatement.setString(2,c.getUsername());
            preparedStatement.setString(3,c.getPassword());
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }

    }

    @Override
    public void Delete(Integer id) {
        Connection con=jdbCutils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("delete from User where id=?")){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }
    }

    @Override
    public void Update(Integer id, Utilizator c) {
        Connection connection=jdbCutils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement("update User set username=?,password=? where id=?")){
            preparedStatement.setString(1,c.getUsername());
            preparedStatement.setString(2,c.getPassword());
            preparedStatement.setInt(3,id);
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }
    }
    @Override
    public boolean Login(String username,String password){
        Connection connection=jdbCutils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement("select * from User where username=?")){
            preparedStatement.setString(1,username);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    String parola=resultSet.getString("password");
                    if(password.equals(parola))
                        return true;
                }
            }
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }
        return false;
    }
}
