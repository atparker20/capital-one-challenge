// Author: Ali Parker
public class Call //implements Comparable<Call> 
{
	int hour;
	int minute;
	int second;
	int dispatchTime; //time between received and response time stamps, in seconds
	String timeOfDay;
	String day;
	String group; // Fire, Alarm, Potential Life-Threatening, Non Life-Threatening
	String zipcode;
	
	public Call() {
		
	}
	
	public Call(String received_time, String response_time, String date, String group, String zipcode) {
		calculateTime(received_time);
		calcTimeOfDay(received_time);
		dateToDay(date);
		calcDispatchTime(received_time, response_time);
		this.group = group;
		this.zipcode = zipcode;
	}
	
	public void calculateTime(String callTime) {
		// callTime = callTime.substring(11, 19)
		// Time format is hh:mm:ss
		hour = Integer.parseInt(callTime.substring(11, 13));
		minute = Integer.parseInt(callTime.substring(14, 16));
		second = Integer.parseInt(callTime.substring(17, 19));
		
	}
	
	public void calcTimeOfDay(String callTime) {
		//Late Night = 12:00am - 3:59am
		if (hour == 0 || hour < 4) {
			timeOfDay = "Late Night";
		}
		//Morning = 4:00am - 9:59am
		else if (hour > 3 && hour < 10) {
			timeOfDay = "Morning";
		}
		//Afternoon = 10:00am - 3:59pm
		else if (hour > 9 && hour < 16) {
			timeOfDay = "Afternoon";
		}
		//Evening = 4:00pm - 11:59pm
		else if (hour > 15 && hour < 25) {
			timeOfDay = "Evening";
		}
	}
	
	public int isolateDay(String date) {
		int day = Integer.parseInt(date.substring(2, 4));
		return day;
	}
	
	public void dateToDay(String date) {
		//Monday: 1/15/18 and 1/22/18
		int callDay = isolateDay(date);
		if(callDay == 15 || callDay == 22) {
			day = "Monday";
		}
		else if(callDay == 16 || callDay == 23) {
			day = "Tuesday";
		}
		else if(callDay == 17 || callDay == 24) {
			day = "Wednesday";
		}
		else if(callDay == 18) {
			day = "Thursday";
		}
		else if(callDay == 19) {
			day = "Friday";
		}
		else if(callDay == 13 || callDay == 20) {
			day = "Saturday";
		}
		else {
			day = "Sunday";
		}
	}
	
	//Calculates the total time in seconds between received timestamp and response timestamp
	public void calcDispatchTime(String received_time, String response_time) {
		if (response_time.equals("")) { //occurs when response is not needed
			response_time = received_time; //response time is zero
		}
		int resp_hour = Integer.parseInt(response_time.substring(11, 13));
		int resp_min = Integer.parseInt(response_time.substring(14, 16));
		int resp_sec = Integer.parseInt(response_time.substring(17, 19));
		int receivedTime = standardToSeconds(hour, minute, second);
		int responseTime = standardToSeconds(resp_hour, resp_min, resp_sec);
		if(responseTime < receivedTime) {
			responseTime += 86400; //add number of seconds in a day
		}
		int duration = responseTime - receivedTime;
		dispatchTime = duration;
		}
	
	//Translates standard time format into seconds
	public int standardToSeconds(int hour, int min, int sec) {
		final int MIN_PER_HOUR = 60;
		final int SEC_PER_HOUR = 60;
		int totalSeconds = (hour*MIN_PER_HOUR*SEC_PER_HOUR) + (min*MIN_PER_HOUR) + sec;
		
		return totalSeconds;
	}
	
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public void setMinutes(int minute) {
		this.minute = minute;
	}
	
	public int getSecond() {
		return second;
	}
	
	public void setSecond(int second) {
		this.second = second;
	}
	
	public int getDispatchTime() {
		return dispatchTime;
	}
	
	public void setDispatchTime(int dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	
	public String getTimeOfDay() {
		return timeOfDay;
	}
	
	public void setTimeOfDay(String timeOfDay) {
		this.timeOfDay = timeOfDay;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	
	@Override
	public String toString() {
		return hour + ":" + minute + ":" + second + " " + timeOfDay + " " + day + group + zipcode;
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
