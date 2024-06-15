package bugBust.transitgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableScheduling
public class TransitgoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransitgoApplication.class, args);
	}

}
