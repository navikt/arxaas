package no.oslomet.aaas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AaaSApplication {
	
	//Jacoco plugin test
	public String getMessage(String name) {
		StringBuilder result = new StringBuilder();
		if (name == null || name.trim().length() == 0) {
			result.append("Please provide a name!");
		} else {
			result.append("Hello " + name);
		}
		return result.toString();
	}

	public static void main(String[] args) {
		SpringApplication.run(AaaSApplication.class, args);
	}

}

