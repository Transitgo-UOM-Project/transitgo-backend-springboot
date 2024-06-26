package bugBust.transitgo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long activityId;
    private String activityType;
    @Lob
    private String description;
    private String info;
    private LocalDateTime dateTime;

    private String pacStatus;
}
