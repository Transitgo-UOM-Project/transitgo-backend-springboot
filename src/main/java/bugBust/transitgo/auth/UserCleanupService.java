package bugBust.transitgo.auth;

import bugBust.transitgo.model.User;
import bugBust.transitgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCleanupService {

    @Autowired
    private UserRepository userRepository;

    public UserCleanupService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public List<User> findUnverifiedUsers(LocalDateTime cutoffTime){
        List<User> usersByOtpTimeStamp = userRepository.findAllByOtpTimestampBefore(cutoffTime);
        List<User> userByEnabledFalse = userRepository.findAllByEnabledFalse();

        return usersByOtpTimeStamp.stream()
                .filter(userByEnabledFalse::contains)
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 86400000) //every 24 hour
    public void deleteUnverifiedUser() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24)  ;
        List<User> unverifiedUsers = findUnverifiedUsers(cutoffTime);

        userRepository.deleteAll(unverifiedUsers);

    }
}
