package com.oen.core.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Hours;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oen.core.service.impl.EnergyServiceImpl;

public class OENUtils {
	
	private static final Logger LOG = LoggerFactory.getLogger(OENUtils.class);
	public static String DATE_TIME_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormat.forPattern(DATE_TIME_INPUT_FORMAT);
	
	
	public static DateTime convertDateTimeLocalToUTC(String dateTimeInput, String timeZoneID) {
    	DateTimeZone dateTimeZone = DateTimeZone.forID(timeZoneID);
    	DateTime localDateTime =  NbanoMasterConstants.DATETIME_FORMATTER
    			.withZone(dateTimeZone)
    			.parseDateTime(dateTimeInput);
    	DateTime UTCDateTime = localDateTime.toDateTime(DateTimeZone.UTC);
    	return UTCDateTime;
    }
	
	public static Double calculateConsumedPower(Integer deviceWatt, String attributeType, String attributeValue) {
		Double consumptions = 0.00;
		switch (attributeType.toLowerCase()) {
			case "brightness" : consumptions = powerConsumptionForBrightness(deviceWatt, attributeValue); 	
				break;
			case "color": consumptions = powerConsumptionForColor(deviceWatt, attributeValue);	
				break;
			case "power" : if(attributeValue.equals("1")) {
								consumptions = Double.valueOf(deviceWatt);
						   }
			default:
				break;
		}
		return consumptions;
	}
	
	public static Double powerConsumptionForBrightness(Integer deviceWatt, String attributeValue) {
		Double consumptions = (Double.valueOf(attributeValue) * Double.valueOf(deviceWatt.toString())) / 100;
		return consumptions;
	}
	
	public static Double powerConsumptionForColor(Integer deviceWatt, String attributeValue) {
		Double consumptions = (Double.valueOf(attributeValue) * Double.valueOf(deviceWatt.toString())) / 100;
		return consumptions;
	}
	
	public static Integer numberHoursOfThisMonth(LocalDateTime currentTime) {
		String month = String.valueOf(currentTime.getMonthOfYear());
		String year = String.valueOf(currentTime.getYear());
		String monthStartDate = year+"-"+month+"-01 00:00:00";
		String monthEndDate = currentTime.toString(DATE_TIME_INPUT_FORMAT);
		LocalDateTime startDate = DATETIME_FORMATTER.parseDateTime(monthStartDate).toLocalDateTime();
		LocalDateTime endDate = DATETIME_FORMATTER.parseDateTime(monthEndDate).toLocalDateTime();
		Integer hours = Hours.hoursBetween(startDate, endDate).getHours();
		return hours;
	}
	
	public static Long totalNoOfSeconds(LocalDateTime currentTime) {
		String month = String.valueOf(currentTime.getMonthOfYear());
		String year = String.valueOf(currentTime.getYear());
		String monthStartDate = year+"-"+month+"-01 00:00:00";
		String monthEndDate = currentTime.toString(DATE_TIME_INPUT_FORMAT);
		LocalDateTime startDate = DATETIME_FORMATTER.parseDateTime(monthStartDate).toLocalDateTime();
		LocalDateTime endDate = DATETIME_FORMATTER.parseDateTime(monthEndDate).toLocalDateTime();
		Long totalSeconds = Long.valueOf(Seconds.secondsBetween(startDate, endDate).getSeconds());
		LOG.info("####################################");
		LOG.info("TOTAL SECONDS TILL NOW OF PRESENT MONTH :: "+totalSeconds);
		LOG.info("####################################");
		return totalSeconds;
	}

}
