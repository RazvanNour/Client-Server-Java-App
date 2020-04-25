package repository;


import domain.Cursa;
import domain.Loc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CursaRepo implements ICursaRepo {
    private final static Logger logger= LogManager.getLogger(CursaRepo.class);
    private JDBCUtils dbCutils;
    public CursaRepo(Properties properties){
        dbCutils=new JDBCUtils(properties);
    }
    @Override
    public Cursa findOne(Integer id) {
        logger.info("---findCursa---");
        Connection con=dbCutils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("select * from Cursa where Id=?")){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    int Id=resultSet.getInt("Id");
                    String destinatie=resultSet.getString("Destinatie");
                    String data=resultSet.getString("DataPlecarii");
                    int nrLocuriDisponibile=resultSet.getInt("NrLocuriDisponibile");
                    Cursa cursa=new Cursa(Id,destinatie,data,nrLocuriDisponibile);
                    return cursa;

                }
            }
        }catch (SQLException ex){
            System.out.println("eroare baza de date"+ex);
        }

        return null;
    }

    @Override
    public List<Cursa> findAll() {
        logger.info("---findAllCurse---");
        Connection connection=dbCutils.getConnection();
        List<Cursa>curse=new ArrayList<>();
        try(PreparedStatement preparedStatement=connection.prepareStatement("select * from Cursa")){
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int Id=resultSet.getInt("Id");
                    String destinatie=resultSet.getString("Destinatie");
                    String data=resultSet.getString("DataPlecarii");
                    int nrLocuriDisponibile=resultSet.getInt("NrLocuriDisponibile");
                    Cursa cursa=new Cursa(Id,destinatie,data,nrLocuriDisponibile);
                    curse.add(cursa);

                }
            }
        }catch (SQLException ex){
            System.out.println("Eroare baza de date"+ex);
        }
        return curse;
    }

    @Override
    public void Save(Cursa c) {
        Connection con=dbCutils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("insert into Cursa values (?,?,?,?)")){
            preparedStatement.setInt(1,c.getId());
            preparedStatement.setString(2,c.getDestinatie());
            preparedStatement.setString(3,c.getData_si_ora());
            preparedStatement.setInt(4,c.getNrLocuriDisponibile());
            preparedStatement.executeUpdate();
            for(int i=1;i<=18;i++){
                Connection con1=dbCutils.getConnection();
                try(PreparedStatement preparedStatement1=con1.prepareStatement("insert into Loc values(?,?,?) ")){
                    preparedStatement1.setInt(1,c.getId());
                    preparedStatement1.setInt(2,i);
                    preparedStatement1.setString(3,"-");
                    preparedStatement1.executeUpdate();}
            }

        }catch (SQLException ex){
            System.out.println("eroare baza de date"+ex);
        }

    }

    @Override
    public void Update(Integer id, Cursa c) {
        Connection con=dbCutils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("update Cursa set destinatie=?,DataPlecarii=?,NrLocuriDisponibile=? where Id=?")){
            preparedStatement.setString(1,c.getDestinatie());
            preparedStatement.setString(2,c.getData_si_ora());
            preparedStatement.setInt(3,c.getNrLocuriDisponibile());
            preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare baza de date"+ex);
        }

    }

    @Override
    public void Delete(Integer id) {
        Connection con=dbCutils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("delete from Cursa where Id=?")){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare baza de date"+ex);
        }

    }
    @Override
    public List<Loc> getAllLocuri(Integer id){

        Connection connection=dbCutils.getConnection();
        List<Loc>locuri=new ArrayList<>();
        try(PreparedStatement preparedStatement=connection.prepareStatement("select * from Loc where Id_Cursa=?")){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int nrLoc=resultSet.getInt("NrLoc");
                    String nume=resultSet.getString("Nume");
                    Loc l=new Loc(nume,nrLoc);
                    locuri.add(l);

                }
            }
        }catch (SQLException ex){
            System.out.println("Eroare baza de date"+ex);
        }
        return locuri;
    }
    @Override
    public void updateLocuri(Integer id,String nume,Integer locD,Integer locS) {
        Connection connection = dbCutils.getConnection();

        for (int i = locS; i <= locD; i++) {
            try (PreparedStatement preparedStatement=connection.prepareStatement("update Loc set Nume=? where NrLoc=? and Id_Cursa=?")){
                preparedStatement.setString(1,nume);
                preparedStatement.setInt(2,i);
                preparedStatement.setInt(3,id);
                preparedStatement.executeUpdate();
            }catch(SQLException ex){
                System.out.println("eroare" + ex);
            }
        }

    }

}
