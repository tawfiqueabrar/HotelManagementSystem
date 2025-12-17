package HotelManagementSystem.Ui;

import HotelManagementSystem.Javacore.service.HotelService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginView {

    private static final int W = 380;
    private static final int H = 180;

    //TransparentOverlay
    private static final String OVERLAY = "rgba(255,228,235,0.25)";

    public static void show(Stage stage, HotelService service) {

        TextField user = new TextField();
        PasswordField pass = new PasswordField();
        Button btn = new Button("Login");

        user.setPrefWidth(210);
        pass.setPrefWidth(210);
        btn.setPrefWidth(110);

        user.setStyle("-fx-font-family:'Segoe UI','Arial'; -fx-font-size:14px; -fx-background-radius:8; -fx-border-radius:8; -fx-border-color:#ffffffcc;");
        pass.setStyle("-fx-font-family:'Segoe UI','Arial'; -fx-font-size:14px; -fx-background-radius:8; -fx-border-radius:8; -fx-border-color:#ffffffcc;");
        btn.setStyle("-fx-font-family:'Segoe UI','Arial'; -fx-font-size:14px; -fx-font-weight:bold; -fx-background-radius:10;");

        Label u = new Label("Username");
        Label p = new Label("Password");
        u.setStyle("-fx-font-family:'Segoe UI','Arial'; -fx-font-size:14px; -fx-font-weight:700; -fx-text-fill:#111;");
        p.setStyle("-fx-font-family:'Segoe UI','Arial'; -fx-font-size:14px; -fx-font-weight:700; -fx-text-fill:#111;");

        //Form
        GridPane form = new GridPane();
        form.setPadding(new Insets(18));
        form.setHgap(10);
        form.setVgap(10);
        form.setAlignment(Pos.CENTER);
        form.setStyle("-fx-background-color: " + OVERLAY + ";");

        form.add(u, 0, 0);
        form.add(user, 1, 0);
        form.add(p, 0, 1);
        form.add(pass, 1, 1);
        form.add(btn, 1, 2);

        pass.setOnAction(e -> btn.fire());

        btn.setOnAction(e -> {
            try {
                boolean ok = service.login(user.getText().trim(), pass.getText());
                if (ok) {
                    DashboardView.show(stage, service);
                } else {
                    FX.error("Wrong username or password");
                }
            } catch (Exception ex) {
                FX.error(ex.getMessage());
            }
        });

        //BackgroundImage
        Image bg = new Image(
                LoginView.class.getResource("/HotelManagementSystem/Images/loginpicture.png")
                        .toExternalForm()
        );

        ImageView bgView = new ImageView(bg);
        bgView.setFitWidth(W);
        bgView.setFitHeight(H);
        bgView.setPreserveRatio(false);

        StackPane root = new StackPane(bgView, form);

        stage.setTitle("Hotel Management System - Login");
        stage.setScene(new Scene(root, W, H));
        stage.setWidth(W);
        stage.setHeight(H);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}