package os.course_work.sysmonitor;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CurrentProcess {

	private StringProperty name;
	private SimpleIntegerProperty fPID;
	private SimpleIntegerProperty threadsCount;
	private SimpleIntegerProperty basicPriority;
	private SimpleIntegerProperty parentID;

	@Override
	public String toString() {
		return name.get() + "\t" + fPID.intValue() + "\t" + threadsCount.intValue() + "\t" + basicPriority.intValue() + "\t" + parentID.intValue() + "\t";
	}

	public SimpleIntegerProperty threadsCountProperty() {
		return threadsCount;
	}

	public Integer getThreadsCount() {
		return threadsCount.get();
	}

	public Integer getBasicPriority() {
		return basicPriority.get();
	}

	public Integer getParentID() {
		return parentID.get();
	}

	public SimpleIntegerProperty basicPriorityProperty() {
		return basicPriority;
	}


	public SimpleIntegerProperty parentIDProperty() {
		return parentID;
	}


	public CurrentProcess(String name, int fPID, int threadsCount,
			int basicPriority, int parentID) {
		super();
		this.name = new SimpleStringProperty(name);
		this.fPID = new SimpleIntegerProperty(fPID);
		this.threadsCount = new SimpleIntegerProperty(threadsCount);
		this.basicPriority = new SimpleIntegerProperty(basicPriority);
		this.parentID = new SimpleIntegerProperty(parentID);
	}




	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public Integer getfPID() {
		return fPID.get();
	}

	public SimpleIntegerProperty fPIDProperty() {
		return fPID;
	}

}
