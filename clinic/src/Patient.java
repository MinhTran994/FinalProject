import java.time.LocalDate;


public class Patient extends Person {
    // Private 
    private String patientId;
    private String address;
    private String insuranceProvider;
    private String emergencyContact;

    // constructor
    public Patient(String firstName, String lastName, LocalDate dateOfBirth, String phoneNumber,
                   String patientId, String address, String insuranceProvider, String emergencyContact) 
    {
        super(firstName, lastName, dateOfBirth, phoneNumber);
        if (isValidPatientId(patientId)) {
            this.patientId = patientId;
        } else {
            throw new IllegalArgumentException("Invalid patient ID. Must be 6  characters.");
        }
        this.address = address;
        this.insuranceProvider = insuranceProvider;
        this.emergencyContact = emergencyContact;
    }

    // Getters and Setters
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        if (isValidPatientId(patientId)) {
            this.patientId = patientId;
        } else {
            throw new IllegalArgumentException("Invalid patient ID.");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    // Patient ID validation 
    public boolean isValidPatientId(String id) {
        return id != null && id.matches("[a-zA-Z0-9]{6}");
    }

    // Override abstract method
   
    public String getRole() {
        return "Patient";
    }

    // toString
    
    public String toString() {
        return super.toString() + ", Role=" + getRole() +
                ", patientId='" + patientId + '\'' +
                ", address='" + address + '\'' +
                ", insuranceProvider='" + insuranceProvider + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                '}';
    }
}