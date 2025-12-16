package HotelManagementSystem;

import HotelManagementSystem.Repository.HotelRepository;
import HotelManagementSystem.Javacore.service.*;
import HotelManagementSystem.Ui.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        HotelRepository repo = new HotelRepository();
        HotelService service = new HotelServiceImpliment(repo);
        LoginView.show(stage, service);
    }

    public static void main(String[] args) {
        launch(args);
    }
}