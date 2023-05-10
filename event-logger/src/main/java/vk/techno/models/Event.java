package vk.techno.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String eventName;
    @Column(name = "address")
    private String ipAddress;
    @Column(name = "date")
    private LocalDateTime eventDateTime;
    @Column(name = "authorized")
    private String authorized;

}