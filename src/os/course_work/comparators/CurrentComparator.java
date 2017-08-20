package os.course_work.comparators;

import os.course_work.sysmonitor.CurrentProcess;

import java.util.Comparator;

public class CurrentComparator {

    private static Comparator<CurrentProcess> currentComparator = new SortByName();

    public static Comparator<CurrentProcess> getCurrentComparator() {
        return currentComparator;
    }

    public static void setCurrentComparator(Comparator<CurrentProcess> currentComparator) {
        CurrentComparator.currentComparator = currentComparator;
    }
}
