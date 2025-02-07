package nl.jimkaplan.bpmcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class BpmCalculatorApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BpmCalculatorApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder springApplicationBuilder) {
		return springApplicationBuilder.sources(BpmCalculatorApplication.class);
	}

}
