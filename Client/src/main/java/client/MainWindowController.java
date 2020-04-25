package client;

import domain.Client;
import domain.Cursa;
import domain.Loc;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.AppException;
import services.ICursaObserver;
import services.IServiceCursa;

import java.util.List;

public class MainWindowController implements  ICursaObserver {

    private IServiceCursa cursaService;

    private MainWindowController mainWindowController;
    private String user;
    private Stage one,two;

    @FXML
    private TableView<Cursa> tabel_curse;

    @FXML
    private ListView<String> lista_locuri;
    @FXML
    private TextField destinatie_field;

    @FXML
    private TextField data_field;
    @FXML
    private TextField IdR_field;

    @FXML
    private TextField Client_field;






    public MainWindowController() {

    }

    public void setServer(IServiceCursa server){
        this.cursaService=server;
    }

    public void setController(MainWindowController controller){this.mainWindowController =controller;}
    public void setUser(String username){
        this.user=username;
    }
    public void setStages(Stage one,Stage two){
        this.one=one;
        this.two=two;
    }
    @FXML
    public void handleCautaCursa() throws AppException{
        String destinatie=destinatie_field.getText();
        String dataOra=data_field.getText();
        List<Cursa>lista=cursaService.getAllCurse();
        for(Cursa c:lista){
            if(c.getDestinatie().equals(destinatie)&&c.getData_si_ora().equals(dataOra))
            {
                lista_locuri.getItems().clear();
                List<Loc>rez=cursaService.getLocuriCursa(c.getId());
                for(Loc l:rez)
                    lista_locuri.getItems().add(l.toString());
            }
        }

    }
    @FXML
    public void handleAdaugaRezervare() throws AppException{
        int nrLoc=Integer.parseInt(IdR_field.getText());
        String nume=Client_field.getText();
        Client rez=cursaService.findNumeClient(nume);
        Cursa c=tabel_curse.getSelectionModel().getSelectedItem();
        cursaService.AdaugaRezervare((cursaService.NrRezervari()+1),rez.getId(),c.getId(),nrLoc);
        updateCurse(cursaService.getAllCurse());


    }
    @FXML
    public void handleLogout(){
        try{
            cursaService.logout(user, mainWindowController);
            one.close();
            two.close();


        }catch (AppException e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void updateCurse(List<Cursa> l) throws AppException {
        tabel_curse.getItems().clear();
        for(Cursa c:l)
            tabel_curse.getItems().add(c);
    }
}

