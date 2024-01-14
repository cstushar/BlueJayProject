package com.demo;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Service
public class WorkEntryAnalysisService {

	// Inject WorkEntryRepository to access work entry data
	@Autowired
	private WorkEntryRepository workEntryRepository;

	// Analyze and print results method
	public void analyzeAndPrintResults() {
		// Get all work entries from the repository
		List<WorkEntry> entries = workEntryRepository.findAll();
		System.out.println("");
		
		// Analyze consecutive days
		analyzeConsecutiveDays(entries, 7);
		System.out.println("");

		// Analyze short breaks
		hasShortBreaks(entries, 10, 1);
		System.out.println("");

		// Find employees with long shifts
		findEmployeesWithLongShifts(entries, 14);

	}

	// Method to analyze consecutive days
	public void analyzeConsecutiveDays(List<WorkEntry> workEntries, int consecutiveDays) {
		Map<String, List<WorkEntry>> fileNumberGroups = workEntries.stream()
				.collect(Collectors.groupingBy(WorkEntry::getFileNumber));

		for (List<WorkEntry> entries : fileNumberGroups.values()) {
			if (hasConsecutiveDays(entries, consecutiveDays)) {
				System.out.println("Employee with File Number: " + entries.get(0).getFileNumber() + " has worked for "
						+ consecutiveDays + " consecutive days.");
			}
		}
	}

	// Method to check if an employee has consecutive days
	private boolean hasConsecutiveDays(List<WorkEntry> entries, int consecutiveDays) {
		if (entries == null || entries.size() < consecutiveDays) {
			return false; // No consecutive days if the list is null or has fewer entries than
							// consecutiveDays
		}

		int consecutiveCount = 1; // Start with 1 as the first day is always consecutive to itself

		for (int i = 0; i < entries.size() - 1; i++) {
			WorkEntry currentEntry = entries.get(i);
			WorkEntry nextEntry = entries.get(i + 1);

			// Check for null values and skip the row if any value is null
			if (currentEntry.getTime() == null || nextEntry.getTime() == null) {
				consecutiveCount = 1; // Reset count if there's a gap
				continue;
			}

			// Check if the entries are consecutive days
			LocalDate currentDate = currentEntry.getTime().toLocalDate();
			LocalDate nextDate = nextEntry.getTime().toLocalDate();

			if (nextDate.minusDays(1).equals(currentDate)) {
				consecutiveCount++;

				if (consecutiveCount == consecutiveDays) {
					System.out.println("Employee Name: " + currentEntry.getEmployeeName() + " has worked for "
							+ consecutiveDays + " consecutive days.");
					return true; // Consecutive days found
				}
			} else {
				consecutiveCount = 1; // Reset count if there's a gap
			}
		}

		return false; // No consecutive days found

	}

	// Method to check if an employee has less than 10 hours between shifts
	private boolean hasShortBreaks(List<WorkEntry> entries, double maxBreakHours, double minBreakHours) {
	    Set<String> processedFileNumbers = new HashSet<>();

	    for (int i = 0; i < entries.size() - 1; i++) {
	        WorkEntry currentEntry = entries.get(i);
	        WorkEntry nextEntry = entries.get(i + 1);

	        // Assuming the "Time Out" and "Time" properties are of type LocalDateTime
	        LocalDateTime dateTimeOut = currentEntry.getTimeOut();
	        LocalDateTime dateTime = nextEntry.getTime();

	        // Check for null values
	        if (dateTimeOut != null && dateTime != null) {
	            // Calculate the duration between shifts
	            Duration breakDuration = Duration.between(dateTimeOut, dateTime);

	            // Convert break duration to hours as a double
	            double breakHours = breakDuration.toMinutes() / 60.0;

	            // Check if the break duration is within the specified range and the file number is not processed
	            if (breakHours > minBreakHours && breakHours < maxBreakHours
	                    && processedFileNumbers.add(currentEntry.getFileNumber())) {
	                System.out.println("Employee with File Number: " + currentEntry.getFileNumber() + " has less than "
	                        + maxBreakHours + " hours but more than " + minBreakHours + " hours between shifts.");
	            }
	        }
	    }

	    return false; // No breaks found
	}

	// Method to find employees who worked for more than 14 hours in a single shift
	public void findEmployeesWithLongShifts(List<WorkEntry> workEntries, int maxShiftHours) {
		Map<String, List<WorkEntry>> fileNumberGroups = workEntries.stream()
				.collect(Collectors.groupingBy(WorkEntry::getFileNumber));

		for (List<WorkEntry> entries : fileNumberGroups.values()) {
			if (hasLongShifts(entries, maxShiftHours)) {
				System.out.println("Employee with File Number: " + entries.get(0).getFileNumber()
						+ " has worked for more than " + maxShiftHours + " hours in a single shift.");
			}
		}
	}

	// Method to check if an employee worked for more than a specified duration in a single shift
	private boolean hasLongShifts(List<WorkEntry> entries, int maxShiftHours) {
	    Set<String> processedFileNumbers = new HashSet<>();

	    for (WorkEntry entry : entries) {
	        LocalDateTime timeIn = entry.getTime();
	        LocalDateTime timeOut = entry.getTimeOut();

	        // Check for null values
	        if (timeIn != null && timeOut != null) {
	            // Calculate the duration of the shift in hours
	            long shiftDurationHours = Duration.between(timeIn, timeOut).toHours();

	            // Compare the shift duration with the specified maximum duration
	            if (shiftDurationHours > maxShiftHours && processedFileNumbers.add(entry.getFileNumber())) {
	                System.out.println("Employee with File Number: " + entry.getFileNumber() + " has worked for more than "
	                        + maxShiftHours + " hours in a single shift.");
	            }
	        }
	    }

	    return false; // No long shifts found
	}
}
