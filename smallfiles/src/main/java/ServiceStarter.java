

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.haohiyo.smallfiles.hbase","com.haohiyo.smallfiles.web"})
@PropertySource(value = {"file:/opt/config/smallfiles/config.properties"})
public class ServiceStarter {

	@RequestMapping("/")
	String home() {
		return "Welcome to small-files storage service!";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceStarter.class, args);
	}

}
