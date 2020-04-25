package client;


import domain.Cursa;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import services.AppException;
import services.IServiceCursa;

import java.io.IOException;
import java.util.List;

public class ControllerUtilizator {
    @FXML
    private TextField User_Id;

    @FXML
    private PasswordField Password_Id;
    private IServiceCursa cursaService;

    private MainWindowController mainWindowController;
    private Parent mainParent;
    private Stage stage1;
    public ControllerUtilizator(){

    }
    public void setServer(IServiceCursa server){
        this.cursaService=server;
    }

    public void setParent(Parent p){
        mainParent=p;
    }

    public void setStage(Stage s){
        this.stage1=s;
    }

    public void setController(MainWindowController controller){
        mainWindowController =controller;
    }
    @FXML
    public void handleLogin() throws AppException,IOException{
       String username=User_Id.getText();
       String password=Password_Id.getText();
       mainWindowController.setUser(username);
       try {
          boolean rez=cursaService.Logare(username, password, mainWindowController);
          if(rez){
              List<Cursa>curse=cursaService.getAllCurse();
              System.out.println(curse);
              mainWindowController.updateCurse(curse);
              Stage stage=new Stage();
              stage.setTitle("Rezervari: "+username);
              stage.setScene(new Scene(mainParent));
              mainWindowController.setStages(stage,stage1);
              stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent event) {
                        try{
                            cursaService.logout(username, mainWindowController);
                            System.exit(0);
                        }catch (AppException e){
                            System.out.println(e.getMessage());
                        }
                    }
                });
                stage.show();

          }
       }catch (AppException e){
           System.out.println(e.getMessage());
       }


    }


}
