package os.course_work.sysmonitor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessList {
    private static ObservableList<CurrentProcess> running_processes = FXCollections.observableArrayList();

    public static synchronized ObservableList<CurrentProcess> getRunning_processes() {
        return running_processes;
    }

    public static void addProcess(CurrentProcess process){
        running_processes.add(process);
    }

}
