import java.time.LocalDate;
import java.time.LocalTime;


public class Appointment {
    // Private fields
    private String appointmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String appointmentType;
    private String patientId; 
    private String status;    

    // Constructor
    public Appointment(String appointmentId, LocalDate appointmentDate, LocalTime appointmentTime,
                       String appointmentType, String patientId, String status) {
        if (isValidAppointmentId(appointmentId)) {
            this.appointmentId = appointmentId;
        } else {
            throw new IllegalArgumentException("Invalid appointment ID.");
        }
        if (isValidDate(appointmentDate)) {
            this.appointmentDate = appointmentDate;
        } else {
            throw new IllegalArgumentException("Appointment date must be today or in the future.");
        }
        this.appointmentTime = appointmentTime;
        this.appointmentType = appointmentType;
        this.patientId = patientId;
        this.status = status;
    }

    // Getters and Setters
    public String getAppointmentId() {
        return appointmentId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

   public void setAppointmentDate(LocalDate appointmentDate) {
    if (isValidDate(appointmentDate)) {
        this.appointmentDate = appointmentDate;
    } else {
        throw new IllegalArgumentException("Invalid date. Appointment must be today or in the future.");
    }
}

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Validation methods
    public boolean isValidAppointmentId(String id) {
        return id != null && id.matches("[A-Z0-9]{8}");  // 8 uppercase 
    }

    public boolean isValidDate(LocalDate date) {
        return date != null && !date.isBefore(LocalDate.now());
    }

    // Status management method
    public void cancelAppointment() {
        this.status = "cancelled";
    }

    // toString
 
    public String toString() {
        return "Appointment{" +
                "appointmentId='" + appointmentId + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", appointmentTime=" + appointmentTime +
                ", appointmentType='" + appointmentType + '\'' +
                ", patientId='" + patientId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}