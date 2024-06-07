package gestion.commandeProduit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class CommandeProduitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommandeProduitApplication.class, args);
	}

}
