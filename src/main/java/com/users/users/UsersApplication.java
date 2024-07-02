package com.users.users;

import com.users.users.models.Role;
import com.users.users.models.Utilisateurs;
import com.users.users.models.enums.Status;
import com.users.users.models.enums.TypeRole;
import com.users.users.repository.UsersRepository;
import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;


@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@AllArgsConstructor
public class UsersApplication  {

	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

	private PasswordEncoder passwordEncoder;
	private UsersRepository usersRepository;

	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		Utilisateurs admin = Utilisateurs.builder()
//				.status(Status.ACTIVER)
//				.email("emmanuelkakeu2@gmail.com")
//				.password(passwordEncoder.encode("admin"))
//				.role(
//						Role.builder()
//								.libelle(TypeRole.ADMIN)
//								.build()
//				)
//		.build();
//
//		admin = this.usersRepository.findByEmail("emmanuelkakeu2@gmail.com").orElse(admin);
//		this.usersRepository.save(admin);
//
//		Utilisateurs supplier = Utilisateurs.builder()
//				.status(Status.ACTIVER)
//				.email("supplier2@gmail.com")
//				.password(passwordEncoder.encode("supplier"))
//				.role(
//						Role.builder()
//								.libelle(TypeRole.SUPPLIER)
//								.build()
//				)
//				.build();
//
//		supplier = this.usersRepository.findByEmail("supplier2@gmail.com").orElse(supplier);
//		this.usersRepository.save(supplier);
//	}
}
