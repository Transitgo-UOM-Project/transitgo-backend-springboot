package bugBust.transitgo.dto;

import bugBust.transitgo.model.ActivityLog;
import bugBust.transitgo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogDto {
    private ActivityLog activityLog;
    private Long id;
    private Long userId;
    private String activityType;
    private String description;
    private LocalDateTime dateTime;
    private Long activityId;
    private List<ActivityLog> ActivityLogList;
}
