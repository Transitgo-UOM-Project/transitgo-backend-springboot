package bugBust.transitgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@EnableScheduling
public class TransitgoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TransitgoApplication.class, args);
	}

}
