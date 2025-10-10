import java.time.LocalDate;

public class PatientTest {
    public static void main(String[] args) {
        System.out.println("Patient Test");

        // Test valid constructor
        Patient patient = new Patient("minh", "tran", LocalDate.of(1990, 1, 1), "1234567890",
                "PT1234", "123 Somewhere", "Blue Cross", "Minh Tran - 555-1234");
        System.out.println(patient);
        System.out.println("Role: " + patient.getRole());
        System.out.println("Valid Patient ID? " + patient.isValidPatientId("PT1234") + "\n");

        // Test invalid phone (should throw exception)
        try {
            patient.setPhoneNumber("hahaha");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage() + "\n");
        }

        // Test setter
        patient.setAddress("456 Ivytech");
        System.out.println("Updated Address: " + patient.getAddress());
    }
}