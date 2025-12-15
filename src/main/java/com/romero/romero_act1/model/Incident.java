package com.romero.romero_act1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El resumen es requerido")
    @Size(min = 3, max = 200, message = "El resumen debe tener entre 3 y 200 caracteres")
    @Column(nullable = false, length = 200)
    private String summary;

    @NotBlank(message = "El serviceName es requerido")
    @Size(min = 2, max = 120, message = "El serviceName debe tener entre 2 y 120 caracteres")
    @Column(nullable = false, length = 120)
    private String serviceName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private IncidentSeverity severity = IncidentSeverity.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private IncidentStatus status = IncidentStatus.OPEN;

    @Lob
    private String timelineNotes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.severity == null) this.severity = IncidentSeverity.MEDIUM;
        if (this.status == null) this.status = IncidentStatus.OPEN;
        if (this.status == IncidentStatus.CLOSED && this.closedAt == null) {
            this.closedAt = now;
        }
    }

    @PreUpdate
    void onUpdate() {
        LocalDateTime now = LocalDateTime.now();
        this.updatedAt = now;
        if (this.status == IncidentStatus.CLOSED && this.closedAt == null) {
            this.closedAt = now;
        }
    }

    public Incident() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }

    public IncidentSeverity getSeverity() { return severity; }
    public void setSeverity(IncidentSeverity severity) { this.severity = severity; }

    public IncidentStatus getStatus() { return status; }
    public void setStatus(IncidentStatus status) { this.status = status; }

    public String getTimelineNotes() { return timelineNotes; }
    public void setTimelineNotes(String timelineNotes) { this.timelineNotes = timelineNotes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getClosedAt() { return closedAt; }
    public void setClosedAt(LocalDateTime closedAt) { this.closedAt = closedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
