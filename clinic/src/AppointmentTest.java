import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentTest {
    public static void main(String[] args) 
    {
        System.out.println("Appointment Test");
         
        // Test valid constructor
        Appointment appt = new Appointment("APPT0001", LocalDate.now().plusDays(1),
                LocalTime.of(10, 0), "Checkup", "PT1234", "scheduled");
        System.out.println(appt + "\n");
        
        // Test validation
        System.out.println("Valid Date? " + appt.isValidDate(LocalDate.now().plusDays(1)));
        System.out.println("Valid ID? " + appt.isValidAppointmentId("APPT0001") + "\n");

        // Test status management
        appt.cancelAppointment();
        System.out.println("After cancel: " + appt.getStatus());
        
        // Test invalid date setter
        try {
            appt.setAppointmentDate(LocalDate.now().minusDays(1));
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}