package no.nav.arxaas;

import io.prometheus.client.hotspot.DefaultExports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ARXaaSApplication implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(ARXaaSApplication.class);

	public static void main(String[] args) {

		DefaultExports.initialize();
		SpringApplication.run(ARXaaSApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		logger.info("OptionNames: {}", args.getOptionNames());
	}
}