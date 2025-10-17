import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClinicApp extends Application {
    private DataManager dataManager;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        dataManager = new DataManager();
        
        primaryStage.setTitle("Clinic Management System");
        
        // Main 
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        
    
        
        // Center output area
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(20);
        outputArea.setWrapText(true);
        mainLayout.setCenter(outputArea);
        
        // Bottom button panel
        HBox buttonPanel = createButtonPanel(primaryStage);
        mainLayout.setBottom(buttonPanel);
        
        Scene scene = new Scene(mainLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        showWelcomeMessage();
    }
    
  
    
    private HBox createButtonPanel(Stage stage) {
        HBox buttonPanel = new HBox(10);
        buttonPanel.setPadding(new Insets(10));
        buttonPanel.setAlignment(Pos.CENTER);
        
        Button addPatientBtn = new Button("Add Patient");
        addPatientBtn.setOnAction(e -> showAddPatientDialog(stage));
        
        Button viewPatientsBtn = new Button("View Patients");
        viewPatientsBtn.setOnAction(e -> displayAllPatients());
        
        Button searchPatientBtn = new Button("Search Patient");
        searchPatientBtn.setOnAction(e -> showSearchPatientDialog(stage));
        
        Button deletePatientBtn = new Button("Delete Patient");
        deletePatientBtn.setOnAction(e -> showDeletePatientDialog(stage));
        
        Button addAppointmentBtn = new Button("Add Appointment");
        addAppointmentBtn.setOnAction(e -> showAddAppointmentDialog(stage));
        
        Button viewAppointmentsBtn = new Button("View Appointments");
        viewAppointmentsBtn.setOnAction(e -> displayAllAppointments());
        
        Button deleteAppointmentBtn = new Button("Delete Appointment");
        deleteAppointmentBtn.setOnAction(e -> showDeleteAppointmentDialog(stage));
        
        Button addRecordBtn = new Button("Add Medical Record");
        addRecordBtn.setOnAction(e -> showAddRecordDialog(stage));
    
        Button deleteRecordBtn = new Button("Delete Record");
        deleteRecordBtn.setOnAction(e -> showDeleteRecordDialog(stage));
        
        Button clearBtn = new Button("Clear Screen");
        clearBtn.setOnAction(e -> outputArea.clear());
        
        buttonPanel.getChildren().addAll(
            addPatientBtn, viewPatientsBtn, searchPatientBtn, deletePatientBtn,
            addAppointmentBtn, viewAppointmentsBtn, deleteAppointmentBtn,
            addRecordBtn, deleteRecordBtn, clearBtn
        );
        
        return buttonPanel;
    }
    
    private void showAddPatientDialog(Stage owner) {
        Dialog<Patient> dialog = new Dialog<>();
        dialog.setTitle("Add New Patient");
        dialog.setHeaderText("Enter Patient Information");
        
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        DatePicker dobPicker = new DatePicker();
        TextField phoneField = new TextField();
        TextField patientIdField = new TextField();
        TextField addressField = new TextField();
        TextField insuranceField = new TextField();
        TextField emergencyField = new TextField();
        
        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Date of Birth:"), 0, 2);
        grid.add(dobPicker, 1, 2);
        grid.add(new Label("Phone (10 digits):"), 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(new Label("Patient ID (6 chars) Exp:PAT001"), 0, 4);
        grid.add(patientIdField, 1, 4);
        grid.add(new Label("Address:"), 0, 5);
        grid.add(addressField, 1, 5);
        grid.add(new Label("Insurance Provider:"), 0, 6);
        grid.add(insuranceField, 1, 6);
        grid.add(new Label("Emergency Contact:"), 0, 7);
        grid.add(emergencyField, 1, 7);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    Patient patient = new Patient(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        dobPicker.getValue(),
                        phoneField.getText(),
                        patientIdField.getText(),
                        addressField.getText(),
                        insuranceField.getText(),
                        emergencyField.getText()
                    );
                    return patient;
                } catch (Exception ex) {
                    showError("Error creating patient: " + ex.getMessage());
                    return null;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(patient -> {
            if (patient != null) {
                dataManager.addPatient(patient);
                outputArea.appendText(" Patient added successfully: " + patient.getFirstName() + " " + 
                                    patient.getLastName() + " (ID: " + patient.getPatientId() + ")\n\n");
            }
        });
    }
    
    private void showAddAppointmentDialog(Stage owner) {
        Dialog<Appointment> dialog = new Dialog<>();
        dialog.setTitle("Add New Appointment");
        dialog.setHeaderText("Enter Appointment Information");
        
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField appointmentIdField = new TextField();
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        TextField timeField = new TextField("09:00");
        TextField typeField = new TextField();
        TextField patientIdField = new TextField();
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("scheduled", "completed", "cancelled");
        statusCombo.setValue("scheduled");
        
        grid.add(new Label("Appointment ID (8 chars) Exp APTID001:"), 0, 0);
        grid.add(appointmentIdField, 1, 0);
        grid.add(new Label("Date:"), 0, 1);
        grid.add(datePicker, 1, 1);
        grid.add(new Label("Time (HH:MM):"), 0, 2);
        grid.add(timeField, 1, 2);
        grid.add(new Label("Type:"), 0, 3);
        grid.add(typeField, 1, 3);
        grid.add(new Label("Patient ID:"), 0, 4);
        grid.add(patientIdField, 1, 4);
        grid.add(new Label("Status:"), 0, 5);
        grid.add(statusCombo, 1, 5);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    Appointment appointment = new Appointment(
                        appointmentIdField.getText(),
                        datePicker.getValue(),
                        LocalTime.parse(timeField.getText()),
                        typeField.getText(),
                        patientIdField.getText(),
                        statusCombo.getValue()
                    );
                    return appointment;
                } catch (Exception ex) {
                    showError("Error creating appointment: " + ex.getMessage());
                    return null;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(appointment -> {
            if (appointment != null) {
                // Verify patient exists
                if (dataManager.findPatientById(appointment.getPatientId()) == null) {
                    showError("Patient ID not found: " + appointment.getPatientId() + 
                             "\nPlease add the patient first or use a valid Patient ID.");
                } else {
                    dataManager.addAppointment(appointment);
                    outputArea.appendText(" Appointment added successfully: " + appointment.getAppointmentId() + 
                                        " for patient " + appointment.getPatientId() + "\n\n");
                }
            }
        });
    }
    
    private void showAddRecordDialog(Stage owner) {
        Dialog<MedicalRecord> dialog = new Dialog<>();
        dialog.setTitle("Add Medical Record");
        dialog.setHeaderText("Enter Medical Record Information");
        
        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        TextField recordIdField = new TextField();
        TextField patientIdField = new TextField();
        DatePicker visitDatePicker = new DatePicker();
        visitDatePicker.setValue(LocalDate.now());
        TextField diagnosisField = new TextField();
        TextField treatmentField = new TextField();
        TextField medicationsField = new TextField();
        TextArea notesArea = new TextArea();
        notesArea.setPrefRowCount(3);
        
        grid.add(new Label("Record ID (MR#####) Exp: MR00001:"), 0, 0);
        grid.add(recordIdField, 1, 0);
        grid.add(new Label("Patient ID:"), 0, 1);
        grid.add(patientIdField, 1, 1);
        grid.add(new Label("Visit Date:"), 0, 2);
        grid.add(visitDatePicker, 1, 2);
        grid.add(new Label("Diagnosis:"), 0, 3);
        grid.add(diagnosisField, 1, 3);
        grid.add(new Label("Treatment:"), 0, 4);
        grid.add(treatmentField, 1, 4);
        grid.add(new Label("Medications:"), 0, 5);
        grid.add(medicationsField, 1, 5);
        grid.add(new Label("Notes:"), 0, 6);
        grid.add(notesArea, 1, 6);
        
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                try {
                    MedicalRecord record = new MedicalRecord(
                        recordIdField.getText(),
                        patientIdField.getText(),
                        visitDatePicker.getValue(),
                        diagnosisField.getText(),
                        treatmentField.getText(),
                        medicationsField.getText(),
                        notesArea.getText()
                    );
                    return record;
                } catch (Exception ex) {
                    showError("Error creating medical record: " + ex.getMessage());
                    return null;
                }
            }
            return null;
        });
        
        dialog.showAndWait().ifPresent(record -> {
            if (record != null) {
                // Verify patient exists
                if (dataManager.findPatientById(record.getPatientId()) == null) {
                    showError("Patient ID not found: " + record.getPatientId() + 
                             "\nPlease add the patient first or use a valid Patient ID.");
                } else {
                    dataManager.addMedicalRecord(record);
                    outputArea.appendText(" Medical record added successfully: " + record.getRecordId() + 
                                        " for patient " + record.getPatientId() + "\n\n");
                }
            }
        });
    }
    
   private void displayAllPatients() {
    outputArea.clear();
    outputArea.appendText("=== ALL PATIENTS ===\n\n");

    var patients = dataManager.getAllPatients();
    if (patients.isEmpty()) {
        outputArea.appendText("No patients found.\n");
        return;
    }

    for (Patient p : patients) {
        outputArea.appendText(formatPatient(p) + "\n");

        // Show related appointments
        List<Appointment> appts = dataManager.getAppointmentsByPatientId(p.getPatientId());
        outputArea.appendText("  --- Appointments ---\n");
        if (appts.isEmpty()) {
            outputArea.appendText("    (No appointments)\n");
        } else {
            for (Appointment a : appts) {
                outputArea.appendText("    " + a.getAppointmentId() + " | " +
                    a.getAppointmentDate() + " " + a.getAppointmentTime() +
                    " | " + a.getAppointmentType() + " | Status: " + a.getStatus() + "\n");
            }
        }

        // Show related medical records
        List<MedicalRecord> records = dataManager.getRecordsByPatientId(p.getPatientId());
        outputArea.appendText("  --- Medical Records ---\n");
        if (records.isEmpty()) {
            outputArea.appendText("    (No medical records)\n\n");
        } else {
            for (MedicalRecord r : records) {
                outputArea.appendText("    " + r.getRecordId() + " | Visit: " +
                    r.getVisitDate() + " | " + r.getDiagnosis() + " | " +
                    r.getTreatment() + "\n");
            }
            outputArea.appendText("\n");
        }

        outputArea.appendText("--------------------------------------------------\n\n");
    }
}
    
    private void displayAllAppointments() {
        outputArea.clear();
        outputArea.appendText("=== ALL APPOINTMENTS ===\n\n");
        var appointments = dataManager.getAllAppointments();
        if (appointments.isEmpty()) {
            outputArea.appendText("No appointments found.\n");
        } else {
            for (Appointment a : appointments) {
                outputArea.appendText(formatAppointment(a) + "\n");
            }
        }
    }
  
    private void showSearchPatientDialog(Stage owner) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Patient");
        dialog.setHeaderText("Search for a Patient");
        dialog.setContentText("Enter Patient ID:");
        
        dialog.showAndWait().ifPresent(patientId -> {
            Patient patient = dataManager.findPatientById(patientId);
            outputArea.clear();
            if (patient == null) {
                outputArea.appendText(" Patient not found with ID: " + patientId + "\n\n");
                outputArea.appendText("Available Patient IDs:\n");
                for (Patient p : dataManager.getAllPatients()) {
                    outputArea.appendText("  - " + p.getPatientId() + " (" + 
                                        p.getFirstName() + " " + p.getLastName() + ")\n");
                }
            } else {
                outputArea.appendText(" PATIENT FOUND\n\n");
                outputArea.appendText(formatPatient(patient));
                
                // Show related appointments
                outputArea.appendText("\n--- Appointments for this patient ---\n");
                List<Appointment> patientAppts = dataManager.getAppointmentsByPatientId(patientId);
                if (patientAppts.isEmpty()) {
                    outputArea.appendText("No appointments found.\n");
                } else {
                    for (Appointment a : patientAppts) {
                        outputArea.appendText(formatAppointment(a) + "\n");
                    }
                }
                
                // Show related medical records
                outputArea.appendText("\n--- Medical Records for this patient ---\n");
                List<MedicalRecord> patientRecords = dataManager.getRecordsByPatientId(patientId);
                if (patientRecords.isEmpty()) {
                    outputArea.appendText("No medical records found.\n");
                } else {
                    for (MedicalRecord r : patientRecords) {
                        outputArea.appendText(formatMedicalRecord(r) + "\n");
                    }
                }
            }
        });
    }
    
    private String formatPatient(Patient p) {
        return String.format("Patient: %s %s\n  ID: %s\n  DOB: %s\n  Phone: %s\n  Address: %s\n  Insurance: %s\n",
            p.getFirstName(), p.getLastName(), p.getPatientId(), p.getDateOfBirth(),
            p.getPhoneNumber(), p.getAddress(), p.getInsuranceProvider());
    }
    
    private String formatAppointment(Appointment a) {
        return String.format("Appointment: %s\n  Date: %s at %s\n  Type: %s\n  Patient: %s\n  Status: %s\n",
            a.getAppointmentId(), a.getAppointmentDate(), a.getAppointmentTime(),
            a.getAppointmentType(), a.getPatientId(), a.getStatus());
    }
    
    private String formatMedicalRecord(MedicalRecord r) {
        return String.format("Record: %s\n  Visit Date: %s\n  Diagnosis: %s\n  Treatment: %s\n  Medications: %s\n",
            r.getRecordId(), r.getVisitDate(), r.getDiagnosis(), r.getTreatment(), r.getMedications());
    }
    
    private void showWelcomeMessage() {
        outputArea.setText("Welcome to Simple Clinic Management System!\n\n");
        outputArea.appendText("Use the buttons below to:\n");
        outputArea.appendText("- Add and view patients INFO\n");
        outputArea.appendText("- Schedule and view appointments\n");
        outputArea.appendText("- Add medical records\n\n");
        outputArea.appendText("Data is saved to TXT files.\n");
    }
    
    private void showDeletePatientDialog(Stage owner) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Patient");
        dialog.setHeaderText("Delete a Patient");
        dialog.setContentText("Enter Patient ID to delete:");
        
        dialog.showAndWait().ifPresent(patientId -> {
            Patient patient = dataManager.findPatientById(patientId);
            if (patient == null) {
                showError("Patient not found with ID: " + patientId);
            } else {
                // Confirm deletion
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Deletion");
                confirmAlert.setHeaderText("Delete Patient: " + patient.getFirstName() + " " + patient.getLastName());
                confirmAlert.setContentText("Are you sure you want to delete this patient?\nThis will also delete all related appointments and medical records.");
                
                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        dataManager.deletePatient(patientId);
                        outputArea.appendText(" Patient deleted: " + patientId + "\n");
                        outputArea.appendText("  All related appointments and medical records have been removed.\n\n");
                    }
                });
            }
        });
    }
    
    private void showDeleteAppointmentDialog(Stage owner) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Appointment");
        dialog.setHeaderText("Delete an Appointment");
        dialog.setContentText("Enter Appointment ID to delete:");
        
        dialog.showAndWait().ifPresent(appointmentId -> {
            Appointment appointment = dataManager.findAppointmentById(appointmentId);
            if (appointment == null) {
                showError("Appointment not found with ID: " + appointmentId);
            } else {
                // Confirm deletion
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Deletion");
                confirmAlert.setHeaderText("Delete Appointment: " + appointmentId);
                confirmAlert.setContentText("Patient: " + appointment.getPatientId() + 
                                          "\nDate: " + appointment.getAppointmentDate() + 
                                          "\nAre you sure?");
                
                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        dataManager.deleteAppointment(appointmentId);
                        outputArea.appendText(" Appointment deleted: " + appointmentId + "\n\n");
                    }
                });
            }
        });
    }
    
    private void showDeleteRecordDialog(Stage owner) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Medical Record");
        dialog.setHeaderText("Delete a Medical Record");
        dialog.setContentText("Enter Record ID to delete:");
        
        dialog.showAndWait().ifPresent(recordId -> {
            MedicalRecord record = dataManager.findRecordById(recordId);
            if (record == null) {
                showError("Medical record not found with ID: " + recordId);
            } else {
                // Confirm deletion
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Deletion");
                confirmAlert.setHeaderText("Delete Medical Record: " + recordId);
                confirmAlert.setContentText("Patient: " + record.getPatientId() + 
                                          "\nVisit Date: " + record.getVisitDate() + 
                                          "\nAre you sure?");
                
                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        dataManager.deleteRecord(recordId);
                        outputArea.appendText(" Medical record deleted: " + recordId + "\n\n");
                    }
                });
            }
        });
    }
    

    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    // Inner DataManager class
    static class DataManager {
        private static final String PATIENTS_FILE = "patients.txt";
        private static final String APPOINTMENTS_FILE = "appointments.txt";
        private static final String RECORDS_FILE = "medical_records.txt";
        
        private List<Patient> patients;
        private List<Appointment> appointments;
        private List<MedicalRecord> medicalRecords;
        
        public DataManager() {
            patients = new ArrayList<>();
            appointments = new ArrayList<>();
            medicalRecords = new ArrayList<>();
            loadAllData();
        }
        
        // Patient methods
        public void addPatient(Patient patient) {
            patients.add(patient);
            savePatientsToFile();
        }
        
        public List<Patient> getAllPatients() {
            return new ArrayList<>(patients);
        }
        
        public Patient findPatientById(String patientId) {
            return patients.stream()
                .filter(p -> p.getPatientId().equals(patientId))
                .findFirst()
                .orElse(null);
        }
        
        // Appointment methods
        public void addAppointment(Appointment appointment) {
            appointments.add(appointment);
            saveAppointmentsToFile();
        }
        
        public List<Appointment> getAllAppointments() {
            return new ArrayList<>(appointments);
        }
        
        // Medical record methods
        public void addMedicalRecord(MedicalRecord record) {
            medicalRecords.add(record);
            saveMedicalRecordsToFile();
        }
        
        public List<MedicalRecord> getAllMedicalRecords() {
            return new ArrayList<>(medicalRecords);
        }
        
        public List<Appointment> getAppointmentsByPatientId(String patientId) {
            List<Appointment> result = new ArrayList<>();
            for (Appointment a : appointments) {
                if (a.getPatientId().equals(patientId)) {
                    result.add(a);
                }
            }
            return result;
        }
        
        public List<MedicalRecord> getRecordsByPatientId(String patientId) {
            List<MedicalRecord> result = new ArrayList<>();
            for (MedicalRecord r : medicalRecords) {
                if (r.getPatientId().equals(patientId)) {
                    result.add(r);
                }
            }
            return result;
        }
        
        public Appointment findAppointmentById(String appointmentId) {
            return appointments.stream()
                .filter(a -> a.getAppointmentId().equals(appointmentId))
                .findFirst()
                .orElse(null);
        }
        
        public MedicalRecord findRecordById(String recordId) {
            return medicalRecords.stream()
                .filter(r -> r.getRecordId().equals(recordId))
                .findFirst()
                .orElse(null);
        }
        
        // Delete methods
        public void deletePatient(String patientId) {
            patients.removeIf(p -> p.getPatientId().equals(patientId));
            //  remove all related appointments and records
            appointments.removeIf(a -> a.getPatientId().equals(patientId));
            medicalRecords.removeIf(r -> r.getPatientId().equals(patientId));
            savePatientsToFile();
            saveAppointmentsToFile();
            saveMedicalRecordsToFile();
        }
        
        public void deleteAppointment(String appointmentId) {
            appointments.removeIf(a -> a.getAppointmentId().equals(appointmentId));
            saveAppointmentsToFile();
        }
        
        public void deleteRecord(String recordId) {
            medicalRecords.removeIf(r -> r.getRecordId().equals(recordId));
            saveMedicalRecordsToFile();
        }
        
        // Save methods
        private void savePatientsToFile() {
            try (PrintWriter writer = new PrintWriter(new FileWriter(PATIENTS_FILE))) {
                for (Patient p : patients) {
                    writer.printf("%s|%s|%s|%s|%s|%s|%s|%s%n",
                        p.getFirstName(),
                        p.getLastName(),
                        p.getDateOfBirth(),
                        p.getPhoneNumber(),
                        p.getPatientId(),
                        p.getAddress(),
                        p.getInsuranceProvider(),
                        p.getEmergencyContact()
                    );
                }
            } catch (IOException e) {
                System.err.println("Error saving patients: " + e.getMessage());
            }
        }
        
        private void saveAppointmentsToFile() {
            try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENTS_FILE))) {
                for (Appointment a : appointments) {
                    writer.printf("%s|%s|%s|%s|%s|%s%n",
                        a.getAppointmentId(),
                        a.getAppointmentDate(),
                        a.getAppointmentTime(),
                        a.getAppointmentType(),
                        a.getPatientId(),
                        a.getStatus()
                    );
                }
            } catch (IOException e) {
                System.err.println("Error saving appointments: " + e.getMessage());
            }
        }
        
        private void saveMedicalRecordsToFile() {
            try (PrintWriter writer = new PrintWriter(new FileWriter(RECORDS_FILE))) {
                for (MedicalRecord r : medicalRecords) {
                    writer.printf("%s|%s|%s|%s|%s|%s|%s%n",
                        r.getRecordId(),
                        r.getPatientId(),
                        r.getVisitDate(),
                        r.getDiagnosis(),
                        r.getTreatment(),
                        r.getMedications(),
                        r.getNotes()
                    );
                }
            } catch (IOException e) {
                System.err.println("Error saving medical records: " + e.getMessage());
            }
        }
        
        // Load methods
        private void loadAllData() {
            loadPatientsFromFile();
            loadAppointmentsFromFile();
            loadMedicalRecordsFromFile();
        }
        
        private void loadPatientsFromFile() {
            File file = new File(PATIENTS_FILE);
            if (!file.exists()) {
                return;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 8) {
                        try {
                            Patient patient = new Patient(
                                parts[0], // firstName
                                parts[1], // lastName
                                LocalDate.parse(parts[2]), // dateOfBirth
                                parts[3], // phoneNumber
                                parts[4], // patientId
                                parts[5], // address
                                parts[6], // insuranceProvider
                                parts[7]  // emergencyContact
                            );
                            patients.add(patient);
                        } catch (Exception e) {
                            System.err.println("Error loading patient: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading patients file: " + e.getMessage());
            }
        }
        
        private void loadAppointmentsFromFile() {
            File file = new File(APPOINTMENTS_FILE);
            if (!file.exists()) {
                return;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 6) {
                        try {
                            Appointment appointment = new Appointment(
                                parts[0], // appointmentId
                                LocalDate.parse(parts[1]), // date
                                LocalTime.parse(parts[2]), // time
                                parts[3], // type
                                parts[4], // patientId
                                parts[5]  // status
                            );
                            appointments.add(appointment);
                        } catch (Exception e) {
                            System.err.println("Error loading appointment: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading appointments file: " + e.getMessage());
            }
        }
        
        private void loadMedicalRecordsFromFile() {
            File file = new File(RECORDS_FILE);
            if (!file.exists()) {
                return;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 7) {
                        try {
                            MedicalRecord record = new MedicalRecord(
                                parts[0], // recordId
                                parts[1], // patientId
                                LocalDate.parse(parts[2]), // visitDate
                                parts[3], // diagnosis
                                parts[4], // treatment
                                parts[5], // medications
                                parts[6]  // notes
                            );
                            medicalRecords.add(record);
                        } catch (Exception e) {
                            System.err.println("Error loading medical record: " + e.getMessage());
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading medical records file: " + e.getMessage());
            }
        }
    }
}