package mybuss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MybussBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybussBackendApplication.class, args);

		System.out.println("Corriendo");
	}

}
