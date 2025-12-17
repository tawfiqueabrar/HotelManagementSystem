package HotelManagementSystem.Ui;

import HotelManagementSystem.Javacore.model.Guest;
import HotelManagementSystem.Javacore.model.Room;
import HotelManagementSystem.Javacore.service.HotelService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardView {

    private static final int W = 900;
    private static final int H = 520;

    //Just transparent
    private static final String OVERLAY = "rgba(255,228,235,0.15)";

    public static void show(Stage stage, HotelService service) {

        TabPane tabs = new TabPane();

        Tab t1 = checkInTab(service);
        Tab t2 = checkOutTab(service);
        Tab t3 = roomsTab(service);
        Tab t4 = updateRoomTab(service);
        Tab t5 = allGuestsTab(service);
        Tab t6 = backupTab(service);

        for (Tab t : new Tab[]{t1, t2, t3, t4, t5, t6}) t.setClosable(false);
        tabs.getTabs().addAll(t1, t2, t3, t4, t5, t6);

        //BackgroundImage
        Image bg = new Image(
                DashboardView.class.getResource("/HotelManagementSystem/Images/dashboardpicture.png")
                        .toExternalForm()
        );

        ImageView bgView = new ImageView(bg);
        bgView.setPreserveRatio(false);
        bgView.fitWidthProperty().bind(stage.widthProperty());
        bgView.fitHeightProperty().bind(stage.heightProperty());

        // Overlay so UI is readable but image visible
        BorderPane overlay = new BorderPane(tabs);
        overlay.setStyle("-fx-background-color: " + OVERLAY + ";");

        StackPane root = new StackPane(bgView, overlay);

        // Safe root set
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root, W, H));
        } else {
            stage.getScene().setRoot(root);
        }

        stage.setTitle("Hotel Management System - Dashboard");
        stage.setWidth(W);
        stage.setHeight(H);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    //Tabs

    private static Tab checkInTab(HotelService service) {

        TextField name = new TextField();
        TextField nid = new TextField();
        TextField mobile = new TextField();
        TextField address = new TextField();

        ComboBox<String> room = new ComboBox<>(FXCollections.observableArrayList(service.availableRooms()));

        Label price = new Label("Price: ");
        price.setStyle("-fx-font-family:'Segoe UI','Arial'; -fx-font-size:14px; -fx-font-weight:700; -fx-text-fill:#111;");

        TextField paid = new TextField();
        paid.setEditable(false);

        Button btn = new Button("Check In");

        //2ndImage
        styleField(name);
        styleField(nid);
        styleField(mobile);
        styleField(address);
        styleField(paid);
        styleCombo(room);
        styleBtn(btn);

        GridPane g = form();
        g.setAlignment(Pos.TOP_LEFT);

        g.addRow(0, lbl("Name "), name);
        g.addRow(1, lbl("NID "), nid);
        g.addRow(2, lbl("Mobile "), mobile);
        g.addRow(3, lbl("Address "), address);
        g.addRow(4, lbl("Room "), room);
        g.addRow(5, lbl("Room Price "), price);
        g.addRow(6, lbl("Paid "), paid);
        g.add(btn, 1, 7);

        room.setOnAction(e -> {
            String r = room.getValue();
            if (r == null) return;
            int p = service.roomPrice(r);
            price.setText("Price: " + p);
            paid.setText(String.valueOf(p));
        });

        btn.setOnAction(e -> {
            try {
                String r = room.getValue();
                if (r == null) {
                    FX.error("Select room");
                    return;
                }

                int p = Integer.parseInt(paid.getText().trim());

                service.checkIn(new Guest(
                        name.getText().trim(),
                        nid.getText().trim(),
                        mobile.getText().trim(),
                        address.getText().trim(),
                        r,
                        p
                ));

                FX.info("Check In successful ");

                // refresh available rooms
                room.setItems(FXCollections.observableArrayList(service.availableRooms()));

                // clear
                name.clear();
                nid.clear();
                mobile.clear();
                address.clear();
                paid.clear();
                price.setText("Price: ");

            } catch (Exception ex) {
                FX.error(ex.getMessage());
            }
        });

        return new Tab("Check In", wrap(g));
    }

    private static Tab checkOutTab(HotelService service) {

        TextField mobile = new TextField();
        mobile.setPromptText("Enter 11 digit mobile number");

        Button out = new Button("Check Out");

        styleField(mobile);
        styleBtn(out);

        VBox box = new VBox(12,
                title("Checkout"),
                lbl("Mobile Number"),
                mobile,
                out
        );

        box.setPadding(new Insets(22));
        box.setAlignment(Pos.TOP_LEFT);
        box.setStyle("-fx-background-color: transparent;");

        out.setOnAction(e -> {
            try {
                service.checkOutByMobile(mobile.getText().trim());
                FX.info("Checkout successful ");
                mobile.clear();
            } catch (Exception ex) {
                FX.error(ex.getMessage());
            }
        });

        return new Tab("Check out", wrap(box));
    }

    private static Tab roomsTab(HotelService service) {

        TableView<Room> table = new TableView<>();

        TableColumn<Room, String> c1 = new TableColumn<>("Room");
        c1.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRoomNumber()));

        TableColumn<Room, String> c2 = new TableColumn<>("Type");
        c2.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRoomType()));

        TableColumn<Room, String> c3 = new TableColumn<>("Status");
        c3.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getAvailability()));

        TableColumn<Room, String> c4 = new TableColumn<>("Price");
        c4.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getPrice())));

        table.getColumns().addAll(c1, c2, c3, c4);
        table.setItems(FXCollections.observableArrayList(service.rooms()));
        table.setStyle("-fx-background-radius: 12; -fx-border-radius: 12;");

        Button refresh = new Button("Refresh");
        styleBtn(refresh);
        refresh.setOnAction(e -> table.setItems(FXCollections.observableArrayList(service.rooms())));

        VBox box = new VBox(12, refresh, table);
        box.setPadding(new Insets(16));
        box.setStyle("-fx-background-color: transparent;");

        return new Tab("Rooms", wrap(box));
    }

    private static Tab updateRoomTab(HotelService service) {

        TextField roomNo = new TextField();
        ComboBox<String> status = new ComboBox<>(FXCollections.observableArrayList("Available", "Occupied"));
        status.getSelectionModel().selectFirst();

        Button btn = new Button("Update");

        styleField(roomNo);
        styleCombo(status);
        styleBtn(btn);

        GridPane g = form();
        g.setAlignment(Pos.TOP_LEFT);
        g.addRow(0, lbl("Room No"), roomNo);
        g.addRow(1, lbl("Status"), status);
        g.add(btn, 1, 2);

        btn.setOnAction(e -> {
            try {
                int rows = service.updateRoom(roomNo.getText().trim(), status.getValue());
                FX.info(rows > 0 ? "Updated " : "Room not found");
            } catch (Exception ex) {
                FX.error(ex.getMessage());
            }
        });

        return new Tab("Update Room", wrap(g));
    }

    private static Tab allGuestsTab(HotelService service) {

        TableView<Guest> table = new TableView<>();

        TableColumn<Guest, String> c1 = new TableColumn<>("Name");
        c1.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));

        TableColumn<Guest, String> c2 = new TableColumn<>("NID");
        c2.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getNid()));

        TableColumn<Guest, String> c3 = new TableColumn<>("Mobile");
        c3.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getMobile()));

        TableColumn<Guest, String> c4 = new TableColumn<>("Address");
        c4.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getAddress()));

        TableColumn<Guest, String> c5 = new TableColumn<>("Room");
        c5.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getRoom()));

        TableColumn<Guest, String> c6 = new TableColumn<>("Paid");
        c6.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getPaid())));

        table.getColumns().addAll(c1, c2, c3, c4, c5, c6);
        table.setItems(FXCollections.observableArrayList(service.allGuests()));
        table.setStyle("-fx-background-radius: 12; -fx-border-radius: 12;");

        Button refresh = new Button("Refresh");
        styleBtn(refresh);
        refresh.setOnAction(e -> table.setItems(FXCollections.observableArrayList(service.allGuests())));

        VBox box = new VBox(12, refresh, table);
        box.setPadding(new Insets(16));
        box.setStyle("-fx-background-color: transparent;");

        return new Tab("All Guest Details", wrap(box));
    }

    private static Tab backupTab(HotelService service) {

        TextField path = new TextField("backup/guests.csv");
        Button btn = new Button("Backup");

        styleField(path);
        styleBtn(btn);

        VBox box = new VBox(12,
                title("Backup"),
                lbl("Save file path"),
                path,
                btn
        );

        box.setPadding(new Insets(22));
        box.setStyle("-fx-background-color: transparent;");

        btn.setOnAction(e -> {
            try {
                service.backupGuestsCsv(path.getText().trim());
                FX.info("Backup done ");
            } catch (Exception ex) {
                FX.error(ex.getMessage());
            }
        });

        return new Tab("Backup", wrap(box));
    }

    //UIHelpers

    private static Label title(String text) {
        Label l = new Label(text);
        l.setStyle(
                "-fx-font-family:'Segoe UI','Arial';" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: 800;" +
                        "-fx-text-fill: #111111;"
        );
        return l;
    }

    private static Label lbl(String text) {
        Label l = new Label(text);
        l.setStyle(
                "-fx-font-family:'Segoe UI','Arial';" +
                        "-fx-font-size: 15px;" +
                        "-fx-font-weight: 700;" +
                        "-fx-text-fill: #111111;"
        );
        return l;
    }

    //Compact width like 2ndImage
    private static void styleField(TextField t) {
        t.setPrefWidth(220);
        t.setStyle(
                "-fx-font-family:'Segoe UI','Arial';" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;" +
                        "-fx-border-color: #ffffffcc;" +
                        "-fx-text-fill: #111111;"
        );
    }

    private static void styleBtn(Button b) {
        b.setPrefWidth(130);
        b.setStyle(
                "-fx-font-family:'Segoe UI','Arial';" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;"
        );
    }

    private static void styleCombo(ComboBox<?> cb) {
        cb.setPrefWidth(220);
        cb.setStyle(
                "-fx-font-family:'Segoe UI','Arial';" +
                        "-fx-font-size: 14px;" +
                        "-fx-background-radius: 8;" +
                        "-fx-border-radius: 8;"
        );
    }

    private static GridPane form() {
        GridPane g = new GridPane();
        g.setPadding(new Insets(22));
        g.setHgap(14);
        g.setVgap(14);
        g.setStyle("-fx-background-color: transparent;");

        ColumnConstraints left = new ColumnConstraints();
        left.setMinWidth(170);

        ColumnConstraints right = new ColumnConstraints();
        right.setMinWidth(240);

        g.getColumnConstraints().addAll(left, right);
        return g;
    }

    private static Pane wrap(Pane p) {
        BorderPane bp = new BorderPane(p);
        bp.setPadding(new Insets(10));
        bp.setStyle("-fx-background-color: transparent;");
        return bp;
    }
}