package com.demo;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.util.Iterator;

import java.nio.file.Paths;

@Service
public class WorkEntryService {

	private final WorkEntryRepository workEntryRepository;

	public WorkEntryService(WorkEntryRepository workEntryRepository) {
		this.workEntryRepository = workEntryRepository;
	}
	// Parse and save data from the excel file to the database

	public void parseAndSaveData(String filePath) throws IOException {
		List<String[]> excelData = readExcelData(filePath);

		for (String[] row : excelData) {
			WorkEntry workEntry = new WorkEntry();

			workEntry.setPositionId(row[0]);
			workEntry.setPositionStatus(row[1]);

			// Parse and set the 'Time' and 'Time Out' fields
			workEntry.setTime(parseDateTime(row[2]));
			workEntry.setTimeOut(parseDateTime(row[3]));

			workEntry.setTimecardHours(row[4]);

			// Parse and set the 'Pay Cycle Start Date' and 'Pay Cycle End Date' fields
			workEntry.setPayCycleStartDate(parseDate(row[5]));
			workEntry.setPayCycleEndDate(parseDate(row[6]));

			workEntry.setEmployeeName(row[7]);
			workEntry.setFileNumber(row[8]);
			// Save the WorkEntry object to the database

			workEntryRepository.save(workEntry);
		}
	}

	private List<String[]> readExcelData(String filePath) throws IOException {
		List<String[]> excelData = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(Paths.get(filePath).toFile())) {

			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();

				List<String> rowData = new ArrayList<>();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case STRING:
						rowData.add(cell.getStringCellValue());
						break;
					case NUMERIC:
						// Check for date format and handle accordingly
						if (DateUtil.isCellDateFormatted(cell)) {
							// Handle Date values
							rowData.add(cell.getLocalDateTimeCellValue().toString());
						} else {
							// Handle Numeric values
							rowData.add(String.valueOf(cell.getNumericCellValue()));
						}
						break;
					// Add more cases for other cell types as needed
					default:
						// Handle other cell types if necessary
						break;
					}
				}

				excelData.add(rowData.toArray(new String[0]));
			}
		}

		return excelData;
	}

	public LocalDateTime parseDateTime(String dateTimeString) {
		if (dateTimeString != null && !dateTimeString.isEmpty()) {
			try {
				return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public LocalDate parseDate(String dateString) {
		if (dateString != null && !dateString.isEmpty()) {
			try {
				return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			} catch (DateTimeParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
