import java.time.LocalDate;


public class MedicalRecord {
    // Private fields
    private String recordId;
    private String patientId;  
    private LocalDate visitDate;
    private String diagnosis;
    private String treatment;
    private String medications;
    private String notes;

    // Constructor
    public MedicalRecord(String recordId, String patientId, LocalDate visitDate,
                         String diagnosis, String treatment, String medications, String notes) {
        if (isValidRecordId(recordId)) {
            this.recordId = recordId;
        } else {
            throw new IllegalArgumentException("Invalid record ID.");
        }
        this.patientId = patientId;
        if (isValidVisitDate(visitDate)) {
            this.visitDate = visitDate;
        } else {
            throw new IllegalArgumentException("Visit date must be today or in the past.");
        }
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.medications = medications;
        this.notes = notes;
    }

    // Getters and Setters
    public String getRecordId() {
        return recordId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

   public void setVisitDate(LocalDate visitDate) {
    if (isValidVisitDate(visitDate)) {
        this.visitDate = visitDate;
    } else {
        throw new IllegalArgumentException("Visit date must be today or in the past.");
    }
}

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getMedications() {
        return medications;
    }

    public void setMedications(String medications) {
        this.medications = medications;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Validation methods
    public boolean isValidRecordId(String id) {
        return id != null && id.matches("MR[0-9]{5}");  // MR by 5 digits
    }

    public boolean isValidVisitDate(LocalDate date) {
        return date != null && !date.isAfter(LocalDate.now());
    }

    // toString
    @Override
    public String toString() {
        return "MedicalRecord{" +
                "recordId='" + recordId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", visitDate=" + visitDate +
                ", diagnosis='" + diagnosis + '\'' +
                ", treatment='" + treatment + '\'' +
                ", medications='" + medications + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}