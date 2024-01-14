package com.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class WorkEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String positionId;
    private String positionStatus;
    private LocalDateTime time;
    private LocalDateTime timeOut;
    private String timecardHours;
    private LocalDate payCycleStartDate;
    private LocalDate payCycleEndDate;
    private String employeeName;
    private String fileNumber;
    
   
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPositionStatus() {
		return positionStatus;
	}
	public void setPositionStatus(String positionStatus) {
		this.positionStatus = positionStatus;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public LocalDateTime getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(LocalDateTime timeOut) {
		this.timeOut = timeOut;
	}
	public String getTimecardHours() {
		return timecardHours;
	}
	public void setTimecardHours(String timecardHours) {
		this.timecardHours = timecardHours;
	}
	public LocalDate getPayCycleStartDate() {
		return payCycleStartDate;
	}
	public void setPayCycleStartDate(LocalDate payCycleStartDate) {
		this.payCycleStartDate = payCycleStartDate;
	}
	public LocalDate getPayCycleEndDate() {
		return payCycleEndDate;
	}
	public void setPayCycleEndDate(LocalDate payCycleEndDate) {
		this.payCycleEndDate = payCycleEndDate;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getFileNumber() {
		return fileNumber;
	}
	public void setFileNumber(String fileNumber) {
		this.fileNumber = fileNumber;
	}
	@Override
	public String toString() {
		return "WorkEntry [positionId=" + positionId + ", positionStatus=" + positionStatus + ", time=" + time
				+ ", timeOut=" + timeOut + ", timecardHours=" + timecardHours + ", payCycleStartDate="
				+ payCycleStartDate + ", payCycleEndDate=" + payCycleEndDate + ", employeeName=" + employeeName
				+ ", fileNumber=" + fileNumber + "]";
	}
    
    

}
