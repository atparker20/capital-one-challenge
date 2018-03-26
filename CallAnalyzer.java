//Author: Ali Parker
//import java.awt.List;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class CallAnalyzer {
	private static ArrayList<Call> callList;
	
	public CallAnalyzer() {
		callList = new ArrayList<Call>(); //initialize array
	}
	
	public CallAnalyzer(String fileName) {
		this();
		parseCalls(fileName); //read in and parse .csv file
		
	}
    
	//Source: http://stackoverflow.com/questions/4716503/best-way-to-read-a-text-file
	private static String readFile(String filename) {
		String content = null;
		File file = new File(filename); // for ex test.txt
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
	public static void parseCalls(String file) {
		//create an array containing each call separately
		String[] splitList = readFile(file).split("\n"); //split each call
		int counter = 0;
		for(String s : splitList){ //iterate through indi
			counter++;
			if(counter == 1){ //skip the first row of descriptions
				continue; 
			}
			String[] splitCall = s.split(","); //add individual call data to array
			//creates and adds a Call object to callList
			callList.add(new Call(splitCall[6], splitCall[9], splitCall[4], splitCall[25], splitCall[17]));
		}
	}
	 
	public String toString() {
		//creates a string representation of the list of calls using a string buffer
		//faster performance than appending strings
		StringBuffer listOfCalls = new StringBuffer();
		for (Call c : callList) {
			listOfCalls.append(c.toString());
			listOfCalls.append("\n"); //separate each call with a line break
		}
		return listOfCalls.toString();
	}

	/* --------------------------------*/
	/* Part One: Display Three Metrics */
	/* ------------------------------- */
	
	//Returns a string ranking the zipcodes by number off occurrences on call list
	public String rankZipcodes() {
		if(callList.size() == 0) {
			return "Error: Please read in a file";
		}
		//HashMap with zipcodes as keys, zipcode occurrences as values 
		HashMap<String, Integer> zipCount = new HashMap<String, Integer>();
		//all Zipcodes in under jurisdiction of SFPD
		zipCount.put("94102", 0);
		zipCount.put("94103", 0);
		zipCount.put("94104", 0);
		zipCount.put("94105", 0);
		zipCount.put("94107", 0);
		zipCount.put("94108", 0);
		zipCount.put("94108", 0);
		zipCount.put("94110", 0);
		zipCount.put("94111", 0);
		zipCount.put("94112", 0);
		zipCount.put("94114", 0);
		zipCount.put("94115", 0);
		zipCount.put("94116", 0);
		zipCount.put("94117", 0);
		zipCount.put("94118", 0);
		zipCount.put("94121", 0);
		zipCount.put("94122", 0);
		zipCount.put("94123", 0);
		zipCount.put("94124", 0);
		zipCount.put("94127", 0);
		zipCount.put("94129", 0);
		zipCount.put("94130", 0);
		zipCount.put("94131", 0);
		zipCount.put("94132", 0);
		zipCount.put("94133", 0);
		zipCount.put("94134", 0);
		zipCount.put("94158", 0);
		
		for(Call c : callList) {
			for(String a : zipCount.keySet()) {
				if(c.getZipcode().contains(a)) {
					zipCount.put(a, zipCount.get(a) + 1);
				}
			}
		}
		StringBuffer topZipcodes = new StringBuffer("The occurrences of zipcodes are ranked in descending order:\n");
		int rank = 1; 
		for(String a : sortByValues(zipCount).keySet()) { //sort keys into descending order
			topZipcodes.append(rank + ":\t" + "Zipcode: " + a + "\t" + "Occurrences: " + zipCount.get(a) + "\n");
			rank++;
		}
		return topZipcodes.toString();
	}
	
	//Translates seconds into standard time format
	//Helper function for groupStats()
	public String secondsToStandard(int seconds) {
		int minutesPerHour = 60;
		int secondsPerHour = 60;
		
		int minutes = seconds / secondsPerHour;
		seconds -= minutes * secondsPerHour; //subtract minutes from total seconds
		
		int hours = minutes / minutesPerHour;
		minutes -= hours * minutesPerHour; //subtract hours from total minutes
		
		return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
	}
	
	//Returns the average response time for call type group
	//Helper function for groupStats()
	public int[] groupOccurrances() {
		//TreeMap with call type group as keys, group occurrences as values
		TreeMap<String, Integer> groups = new TreeMap<String, Integer>();
		//Counters to determine max value
		groups.put("Fire", 0);
		groups.put("Alarm", 0);
		groups.put("Potentially Life-Threatening", 0);
		groups.put("Non Life-threatening", 0);
		
		for(String s : groups.keySet()) {
			for(Call c : callList) {
				if(c.getGroup().contains(s)) {
					//count every occurrence of each group
					groups.put(s, groups.get(s) + 1);
				}
			}
		}
		//array holding number of times each call type group occurred
		int[] groupArr = new int[]{groups.get("Fire"), groups.get("Alarm"), 
				groups.get("Potentially Life-Threatening"), groups.get("Non Life-threatening")};
		return groupArr;
		
	}
	
	//Calculates average dispatch time for each call type group
	//Fire, Alarm, Potentially Life-Threatening, Non Life-Threatening
	public String groupStats() {
		int fireTotal = 0;
		int alarmTotal = 0;
		int lifeThreatTotal = 0;
		int nonLifeThreatTotal = 0;
		//create Hashmap with call type groups as keys, Call objects as values
		HashMap<String, Call> typeGroups = new HashMap<String, Call>();
		
		for(Call c : callList) {
			typeGroups.put(c.getGroup(), c);
			if(c.getGroup().equals("Fire")) {
				fireTotal += c.getDispatchTime(); //total dispatch time
			}
			else if(c.getGroup().equals("Alarm")) {
				alarmTotal += c.getDispatchTime(); 
			}
			else if(c.getGroup().equals("Potentially Life-Threatening")) {
				lifeThreatTotal += c.getDispatchTime(); 
			}
			else {
				nonLifeThreatTotal += c.getDispatchTime(); 
			}
		}
		//Format: groupOccurrances()[] = {Fire, Alarm, Potentially Life-Threatening, Non Life-threatening
		int fireAverage = fireTotal/groupOccurrances()[0]; //calculate average dispatch time
		int alarmAverage = alarmTotal/groupOccurrances()[1];
		int lifeThreatAverage = lifeThreatTotal/groupOccurrances()[2];
		int nonLifeThreatAverage = nonLifeThreatTotal/groupOccurrances()[3];
		
		String stringReturn = "";
		String[] types = {"Fire", "Alarm", "Potentially Life-Threatening", "Non Life-threatening"};
		int[] averages = {fireAverage, alarmAverage, lifeThreatAverage, nonLifeThreatAverage};
		
		for (int i=0; i < 4; i++) {
			stringReturn += types[i] + ": " + secondsToStandard(averages[i]) + " for " 
		+ groupOccurrances()[i] + " calls\n";
		}
		return ("The average dispatch time and number of occurrences for each Call Type Group are listed below: \n"
				+ stringReturn);
	}
	
	//Calculate number of calls during each time of day, for every day
	public String days() {
		//create Treemap with day + time of day as keys, occurrences of each as value
		TreeMap<String, Integer> days = new TreeMap<String, Integer>();
		days.put("Monday Late Night", 0);
		days.put("Monday Morning", 0);
		days.put("Monday Afternoon", 0);
		days.put("Monday Evening", 0);
		days.put("Tuesday Late Night", 0);
		days.put("Tuesday Morning", 0);
		days.put("Tuesday Afternoon", 0);
		days.put("Tuesday Evening", 0);
		days.put("Wednesday Late Night", 0);
		days.put("Wednesday Morning", 0);
		days.put("Wednesday Afternoon", 0);
		days.put("Wednesday Evening", 0);
		days.put("Thursday Late Night", 0);
		days.put("Thursday Morning", 0);
		days.put("Thursday Afternoon", 0);
		days.put("Thursday Evening", 0);
		days.put("Friday Late Night", 0);
		days.put("Friday Morning", 0);
		days.put("Friday Afternoon", 0);
		days.put("Friday Evening", 0);
		days.put("Saturday Late Night", 0);
		days.put("Saturday Morning", 0);
		days.put("Saturday Afternoon", 0);
		days.put("Saturday Evening", 0);
		days.put("Sunday Late Night", 0);
		days.put("Sunday Morning", 0);
		days.put("Sunday Afternoon", 0);
		days.put("Sunday Evening", 0);

		for(Call c : callList) {
			String day = c.getDay();
			String tod = c.getTimeOfDay();
			String k = day + " " + tod; //key representing day and time of day
			int oldCount = days.get(k); //number of calls during specified time of day
			days.put(k, oldCount + 1);
		
		}
		//make into loop 
		String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		String[] timeOfDay = {"Late Night", "Morning", "Afternoon", "Evening"};
		String dayReturn = "";
		for(int i = 0; i < dayNames.length; i++) { //iterate through list of days
			dayReturn += "\n" + dayNames[i] + ": \n"; 
			for(int j = 0; j < timeOfDay.length; j++) { //iterate through list of times of day
				dayReturn += "\t" + timeOfDay[j] + " = " + days.get(dayNames[i] + " " + timeOfDay[j]) + "\n";
			}
		}
		return ("The number of calls received by SFPD during each time of day are listed below: \n"     
				+ "(Late Evening = 12:00am – 3:59am, " + "Morning = 4:00am – 9:59am, " 
				+ "Afternoon = 10:00am – 3:59pm, " + "Evening = 4:00pm – 11:59pm)" + "\n"  
				+ dayReturn);
	}
	
    public static void main(String[] args) {
    		CallAnalyzer c = new CallAnalyzer("sfpd_dispatch_data_subset.csv");
    		System.out.println(c.rankZipcodes());
    		System.out.println(c.days());
    		System.out.println(c.groupStats());
    }
	
    
	//Source: http://javarevisited.blogspot.com/2012/12/how-to-sort-hashmap-java-by-key-and-value.html#ixzz3U78tPDdc
	@SuppressWarnings("rawtypes")
	public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
        LinkedList<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
      
        Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

            @SuppressWarnings("unchecked")
			@Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
      
        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
      
        for(Map.Entry<K,V> entry: entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
      
        return sortedMap;
    }
}
