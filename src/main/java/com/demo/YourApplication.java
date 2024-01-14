package com.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main class of the application, annotated
 * with @SpringBootApplication to enable Spring Boot auto-configuration.
 */

@SpringBootApplication
public class YourApplication implements ApplicationRunner {

	@Autowired
	private WorkEntryAnalysisService analysisService;

	@Autowired
	WorkEntryService workEntryService;

	public static void main(String[] args) {
		SpringApplication.run(YourApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws IOException {
		System.out.println("Parsing from Execl sheet::");
		workEntryService.parseAndSaveData("C:\\Users\\cstus\\Downloads\\Assignment_Timecard.xlsx");
		System.out.println("Parsing Completed");
		System.out.println("Saving the data in DB is completed");
		System.out.println("Analysing the data !!!!");
		analysisService.analyzeAndPrintResults();
		System.out.println("\nAnalysis Completed, Program stopped");
	}
}
