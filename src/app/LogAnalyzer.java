package app;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.util.LogStat;
import app.util.LogStat.StatType;


public class LogAnalyzer {

	private static Map<String, LogStat<String, Integer>> ipMap = new HashMap<>();
	private static Map<String, LogStat<String, Integer>> urlMap = new HashMap<>();
	private static Map<String, LogStat<String, Integer>> bandWidthMap = new HashMap<>();
	private static Map<String, LogStat<String, Integer>> userAgentsMap = new HashMap<>();

	private static int EXECUTION_TIME = 10 * 60000;

	private static final int ACCESS_PAGE = 1;
	private static final int MOST_NUMBER_OF_HITS = 10;
	private static final int MOST_BANDWIDTH = 10;
	private static final int MOST_AGENT = 20;

	public static void main(String[] args) {

		String fileName = "resources/analyse.log";
		String line = null;
		try {
			if (args != null && args.length > 0) {
				EXECUTION_TIME = Integer.parseInt(args[0]) * 60000;
			}
			// read the log file
			FileReader fr = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fr);
			
			long startTime = System.currentTimeMillis();
			while ((line = bufferedReader.readLine()) != null) {
				processLine(line);

				// if the current time exceeds the allowed run time then stop
				if (System.currentTimeMillis() - startTime >= EXECUTION_TIME) {
					break;
				}
			}

			bufferedReader.close();

			sortStatsAndDisplay(urlMap,ACCESS_PAGE,"Most accessed page","Page","Hits");
			sortStatsAndDisplay(ipMap,MOST_NUMBER_OF_HITS,"Top 10 visitors","IP","Hits");
			sortStatsAndDisplay(bandWidthMap,MOST_BANDWIDTH,"Top 10 traffic bandwidth","IP","Size");
			sortStatsAndDisplay(userAgentsMap,MOST_AGENT,"Top 20 user agents","Agent","Hits");

			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void sortStatsAndDisplay(Map<String, LogStat<String, Integer>> map ,Integer numberOfResults ,String title,String col1,String col2) {
		List<LogStat<String,Integer>> ipList = new ArrayList<>(map.values());
		Collections.sort(ipList);
		StringBuilder sb = new StringBuilder();
		sb.append("##### ");
		sb.append(title);
		sb.append(" ####");
		sb.append("\n");
		sb.append(col1);
		sb.append("\t\t\t\t");
		sb.append(col2);
		sb.append("\n");
		sb.append("\n");
		int maxResults = numberOfResults > ipList.size() ? ipList.size() : numberOfResults;
		for(int x = 0; x < maxResults ;x++)
		{
			LogStat<String,Integer> stat = ipList.get(x);
			sb.append(stat.getAttribute());
			sb.append("\t\t\t");
			sb.append(stat.getValue());
			sb.append("\n");
		}
		sb.append("\n");
		sb.append("########### ");
		System.out.println(sb.toString());
	}

	public static void processLine(String line) {
		String[] arr = line.split("\"");
		// process IP
		String ip = getIp(arr[0]);
		updateStat(ipMap, StatType.IP, ip, 1, ip);

		// process url
		String url = getPage(arr[1]);
		updateStat(urlMap, StatType.URL, url, 1, url);

		// process bandwidth
		String size = getReqSize(arr[2].trim());
		updateStat(bandWidthMap, StatType.BANDWIDTH, ip, Integer.parseInt(size), ip);

		// process user agents
		String userAgent = getUserAgent(arr[5]);
		updateStat(userAgentsMap, StatType.USER_AGENT, userAgent, 1, userAgent);

	}

	/**
	 * Update stats 
	 * @param map
	 * @param type
	 * @param attribute
	 * @param value
	 * @param key
	 */
	public static void updateStat(Map<String, LogStat<String, Integer>> map, StatType type, String attribute,
			Integer value, String key) {
		LogStat<String, Integer> stat = map.get(key);
		if (stat == null) {
			stat = new LogStat<>();
			stat.setType(type);
			stat.setAttribute(attribute);
			stat.setValue(value);
			map.put(key, stat);
		} else {
			stat.setValue(stat.getValue() + value);
		}
	}

	public static String getIp(String line) {
		String[] arr = line.split("-");
		return arr[0].replaceAll("\\s+", "");
	}

	private static String getUserAgent(String line) {
		String[] arr = line.split("\\s+");
		return arr[0];
	}

	private static String getReqSize(String line) {
		String[] arr = line.split("\\s+");
		return arr[1];
	}

	public static String getPage(String line) {
		String[] arr = line.split("\\s+");
		return arr[1];
	}
}
