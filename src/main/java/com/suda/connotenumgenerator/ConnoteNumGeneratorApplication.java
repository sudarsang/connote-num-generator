package com.suda.connotenumgenerator;

import com.suda.connotenumgenerator.domain.CarrierAccount;
import com.suda.connotenumgenerator.processor.ConnoteGenerator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class to call the ConnoteGenerator's generate method.
 * This is done using Spring Boot with the assumption that there is a high chance this feature can be extended to be used
 * with a REST API to generate next connote number. Also it is possible to implement CSV based loading of input or any similar input methodologies.
 */
@SpringBootApplication
public class ConnoteNumGeneratorApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ConnoteNumGeneratorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length < 6) {
			System.out.println("Please pass the correct arguments");
			System.out.println("List of arguments are: <Prefix> <Account Number> <Index Digits> <Last Used Index> <Index range start> <Index range end>");
			return;
		}

		final var prefix = args[0];
		final var accountNumber = args[1];
		try {
			final var indexDigits = Integer.parseInt(args[2]);
			final var lastUsedIndex = Integer.parseInt(args[3]);
			final var rangeStart = Integer.parseInt(args[4]);
			final var rangeEnd = Integer.parseInt(args[5]);

			ConnoteGenerator connoteGenerator = new ConnoteGenerator();
			final String connoteNumber = connoteGenerator.generate(new CarrierAccount(prefix, accountNumber, indexDigits, lastUsedIndex, rangeStart, rangeEnd));

			System.out.printf("The next Consignment Note Number is: \n%s", connoteNumber);
		} catch (NumberFormatException e) {
			System.out.println("Please pass proper numbers for last 4 parameters: " + e);
		}
	}
}
