package os.course_work.data_channel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import os.course_work.sysmonitor.CurrentProcess;

public class ReadProcesssList {
    private static ObservableList<CurrentProcess> running_processes = FXCollections.observableArrayList();

    public static ObservableList<CurrentProcess> getRunning_processes() {
        return running_processes;
    }

    public static void addProcess(CurrentProcess process) {
        running_processes.add(process);
    }
}
