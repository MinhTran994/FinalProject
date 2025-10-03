import java.time.LocalDate;

public class MedicalRecordTest {
    public static void main(String[] args) {
        System.out.println("MedicalRecord Test");

        // Test valid constructor
        MedicalRecord record = new MedicalRecord("MR00123", "PT1234", LocalDate.now(),
                "Flu", "Rest", "Tylenol", "Patient feels better");
        System.out.println(record + "\n");

        // Test validation
        System.out.println("Valid Record ID? " + record.isValidRecordId("MR00123"));
        System.out.println("Valid Visit Date? " + record.isValidVisitDate(LocalDate.now()) + "\n");

        // Test setter
        record.setDiagnosis("Updated: Cold");
        System.out.println("Updated Diagnosis: " + record.getDiagnosis());

        // Test invalid date setter
        try {
            record.setVisitDate(LocalDate.now().plusDays(1));
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}