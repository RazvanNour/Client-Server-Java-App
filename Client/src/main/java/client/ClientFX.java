package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objectProtocol.ServiceCursaObjectProxy;
import services.IServiceCursa;

import java.util.Properties;

public class ClientFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loginLoader=new FXMLLoader(getClass().getResource("/view/UserView.fxml"));
        FXMLLoader principalLoader=new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));

        Parent loginP=loginLoader.load();
        Parent appP=principalLoader.load();

        ControllerUtilizator userCtr=loginLoader.getController();
        MainWindowController princCtr=principalLoader.getController();

        Properties clientProp=new Properties();
        clientProp.load(ClientFX.class.getResourceAsStream("/client.properties"));
        System.out.println("client properties set...");
        clientProp.list(System.out);

        String serverIp=clientProp.getProperty("chat.server.host");
        int serverPort=Integer.parseInt(clientProp.getProperty("chat.server.port"));

        System.out.println("IP: "+serverIp+"; Port: "+serverPort);

        IServiceCursa server=new ServiceCursaObjectProxy(serverIp,serverPort);
        princCtr.setServer(server);
        princCtr.setController(princCtr);

        userCtr.setServer(server);
        userCtr.setController(princCtr);
        userCtr.setParent(appP);

        primaryStage.setScene(new Scene(loginP));
        userCtr.setStage(primaryStage);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }


}
