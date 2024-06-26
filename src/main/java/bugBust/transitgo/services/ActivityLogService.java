package bugBust.transitgo.services;

import bugBust.transitgo.dto.ActivityLogDto;
import bugBust.transitgo.model.ActivityLog;
import bugBust.transitgo.repository.ActivityLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ActivityLogService {
    private final ActivityLogRepository activityLogRepository;

    public ActivityLogDto logActivity(Long userId, String activityType, String description, String info, Long activityId){
        ActivityLogDto response = new ActivityLogDto();
        ActivityLog activityLog = ActivityLog.builder()
                .userId(userId)
                .activityType(activityType)
                .description(description)
                .info(info)
                .dateTime(LocalDateTime.now())
                .activityId(activityId)
                .build();
        activityLogRepository.save(activityLog);

        response.setActivityLog(activityLog);

        return response;
    }

    public ActivityLogDto getActivityLogsForUser(Long userId){
        ActivityLogDto response = new ActivityLogDto();
        response.setActivityLogList(activityLogRepository.findByUserIdOrderByDateTimeDesc(userId));
        return response;
    }

    @Transactional
    public void deleteActivityByActivityId(Long activityId){

        activityLogRepository.deleteByActivityId(activityId);
    }
}
