package repository;

import domain.Client;
import domain.Cursa;
import domain.RezervareLoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RezervareRepo implements IRezervareRepo {
    private JDBCUtils jdbCutils;
    private ClientRepo clientRepo;
    private CursaRepo cursaRepo;
    public RezervareRepo(Properties props, ClientRepo clientRepo, CursaRepo cursaRepo){
        jdbCutils=new JDBCUtils(props);
        this.clientRepo = clientRepo;
        this.cursaRepo = cursaRepo;
    }

    @Override
    public RezervareLoc findOne(Integer id) {
        Connection con=jdbCutils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("select *from Rezervare where Id=?")){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    int id_client=resultSet.getInt("Id_Client");
                    int id_cursa=resultSet.getInt("Id_Cursa");
                    int nrLoc=resultSet.getInt("NrLoc");
                    Client c= clientRepo.findOne(id_client);
                    Cursa d= cursaRepo.findOne(id_cursa);
                    RezervareLoc rezervareLoc =new RezervareLoc(id,d,c,nrLoc);
                    return rezervareLoc;

                }
            }
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }
        return null;
    }

    @Override
    public void Save(RezervareLoc r) {
        Connection connection=jdbCutils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement("insert into Rezervare values (?,?,?,?)")){
            preparedStatement.setInt(1,r.getId());
            preparedStatement.setInt(2,r.getCursa().getId());
            preparedStatement.setInt(3,r.getClient().getId());
            preparedStatement.setInt(4,r.getNrLoc());
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare baza de date"+ex);
        }
    }

    @Override
    public void Update(Integer id, RezervareLoc r) {
        Connection connection=jdbCutils.getConnection();
        try(PreparedStatement preparedStatement=connection.prepareStatement("update Rezervare set Id_Client=?,Id_Cursa=?,NrLoc=? where Id=?")){
            preparedStatement.setInt(1,r.getClient().getId());
            preparedStatement.setInt(2,r.getCursa().getId());
            preparedStatement.setInt(3,r.getNrLoc());
            preparedStatement.setInt(4,id);
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }

    }

    @Override
    public void Delete(Integer id) {
        Connection  con=jdbCutils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("delete from Rezervare where Id=?")){
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }

    }

    @Override
    public List<RezervareLoc> findAll() {
        Connection con=jdbCutils.getConnection();
        List<RezervareLoc> rezervari=new ArrayList<>();
        try(PreparedStatement preparedStatement=con.prepareStatement("select * from Rezervare")){
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int id=resultSet.getInt("Id");
                    int id_client=resultSet.getInt("Id_Client");
                    int id_cursa=resultSet.getInt("Id_Cursa");
                    int nrLoc=resultSet.getInt("NrLoc");
                    Cursa c= cursaRepo.findOne(id_cursa);
                    Client d= clientRepo.findOne(id_client);
                    RezervareLoc rezervareLoc =new RezervareLoc(id,c,d,nrLoc);
                    rezervari.add(rezervareLoc);
                }
            }
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);

        }
        return rezervari;
    }

    public List<RezervareLoc>findRezervareCursa(Integer id){
        Connection con=jdbCutils.getConnection();
        List<RezervareLoc>l=new ArrayList<>();
        try(PreparedStatement preparedStatement=con.prepareStatement("select *from Rezervare where Id_Client=?")){
            preparedStatement.setInt(1,id);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    int id_client=resultSet.getInt("Id_Client");
                    int id_cursa=resultSet.getInt("Id_Cursa");
                    int nrLoc=resultSet.getInt("NrLoc");
                    Client c= clientRepo.findOne(id_client);
                    Cursa d= cursaRepo.findOne(id_cursa);
                    RezervareLoc rezervareLoc =new RezervareLoc(id,d,c,nrLoc);
                    l.add(rezervareLoc);

                }
            }
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);
        }
        return l;
    }
    @Override
    public int size(){
        Connection con=jdbCutils.getConnection();
        List<RezervareLoc> rezervari=new ArrayList<>();
        int i=0;
        try(PreparedStatement preparedStatement=con.prepareStatement("select * from Rezervare")){
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                while(resultSet.next()){
                    int id=resultSet.getInt("Id");
                    int id_client=resultSet.getInt("Id_Client");
                    int id_cursa=resultSet.getInt("Id_Cursa");
                    int nrLoc=resultSet.getInt("NrLoc");
                    Cursa c= cursaRepo.findOne(id_cursa);
                    Client d= clientRepo.findOne(id_client);
                    RezervareLoc rezervareLoc =new RezervareLoc(id,c,d,nrLoc);
                    rezervari.add(rezervareLoc);
                    i++;
                }
            }
        }catch (SQLException ex){
            System.out.println("eroare la baza de date"+ex);

        }
        return i;
    }
}



