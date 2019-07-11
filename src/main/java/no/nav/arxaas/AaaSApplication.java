package no.nav.arxaas;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AaaSApplication implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(AaaSApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AaaSApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		logger.info("OptionNames: {}", args.getOptionNames());
	}
}