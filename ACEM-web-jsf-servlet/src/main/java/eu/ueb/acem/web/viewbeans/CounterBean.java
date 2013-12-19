package eu.ueb.acem.web.viewbeans;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("counterBean")
@Scope("session")
public class CounterBean {
	
	private int count = 1;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void increment() {
		count++;
	}
}
