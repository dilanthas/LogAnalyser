package app.util;

import java.util.Comparator;

public class LogStat<N,C> implements Comparable<LogStat> {

	public enum StatType
	{
		IP,URL,BANDWIDTH,USER_AGENT
	}
	
	private StatType type;
	
	public StatType getType() {
		return type;
	}
	public void setType(StatType type) {
		this.type = type;
	}
	public N getAttribute() {
		return attribute;
	}
	public void setAttribute(N attribute) {
		this.attribute = attribute;
	}
	public C getValue() {
		return value;
	}
	public void setValue(C value) {
		this.value = value;
	}
	private N attribute;
	private C value;

		@Override
	public int compareTo(LogStat o) {
		Integer stat1 = (Integer) o.getValue();
		Integer stat2 = (Integer) this.value;
		if(stat1 > stat2)
		{
			return 1;
		}
		if(stat2 > stat1)
		{
			return -1;
		}	
		else
		{
			return 0;
		}
	}
	
}
