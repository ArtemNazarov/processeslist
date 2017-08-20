package os.course_work.comparators;

import os.course_work.sysmonitor.CurrentProcess;

import java.util.Comparator;

public class SortByParentID implements Comparator<CurrentProcess> {
    @Override
    public int compare(CurrentProcess o1, CurrentProcess o2) {
        return o1.getParentID().compareTo(o2.getParentID());
    }
}
