import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ClinicApp extends Application {

    private TextArea outputArea;

    
    public void start(Stage primaryStage) {
        primaryStage.setTitle(" Clinic Management");

        // ==== Buttons ====
        Button btnViewPatients = new Button("View Patients");
        Button btnAddPatient = new Button("Add Patient");
        Button btnViewRecords = new Button("View Records");
        Button btnExit = new Button("Exit");

        btnViewPatients.setOnAction(e -> displayAllPatients());
        btnAddPatient.setOnAction(e -> showAddPatientDialog(primaryStage));
        btnViewRecords.setOnAction(e -> displayAllRecords());
        btnExit.setOnAction(e -> primaryStage.close());

        //Output Area 
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(400);

        //Layout 
        VBox leftMenu = new VBox(10, btnViewPatients, btnAddPatient, btnViewRecords, btnExit);
        leftMenu.setPadding(new Insets(15));
        leftMenu.setPrefWidth(150);

        BorderPane root = new BorderPane();
        root.setLeft(leftMenu);
        root.setCenter(outputArea);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    

    private void displayAllPatients() {
        outputArea.setText("=== Displaying All Patients ===\n\n()\n");
    }

    private void displayAllRecords() {
        outputArea.setText("=== Displaying All Medical Records ===\n\n()\n");
    }

    private void showAddPatientDialog(Stage owner) {
        
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(owner);
        dialog.setTitle("Add New Patient");
        dialog.setHeaderText("Enter new patient name:");
        dialog.setContentText("Name:");
        dialog.showAndWait().ifPresent(name -> {
            outputArea.appendText("Added new patient: " + name + "\n");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
