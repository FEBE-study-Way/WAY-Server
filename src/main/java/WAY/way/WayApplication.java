package WAY.way;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableRedisRepositories
public class WayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WayApplication.class, args);
	}

}
