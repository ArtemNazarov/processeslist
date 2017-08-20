package os.course_work.sysmonitor;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.W32APIOptions;
import os.course_work.comparators.CurrentComparator;

import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.TimerTask;

public class ProcessMonitor extends TimerTask {

	ResourceBundle bundle = ResourceBundle.getBundle("os.course_work.bundles.ResBundle");

	public void run() {
		ProcessList.getRunning_processes().clear();
		Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
		Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();
		WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
		try {
			while (kernel32.Process32Next(snapshot, processEntry)) {
				CurrentProcess currentProcess = new CurrentProcess(Native.toString(processEntry.szExeFile),
						processEntry.th32ProcessID.intValue(), processEntry.cntThreads.intValue(),
						processEntry.pcPriClassBase.intValue(), processEntry.th32ParentProcessID.intValue());
				ProcessList.addProcess(currentProcess);
			}
		} finally {
			kernel32.CloseHandle(snapshot);
		}
		Comparator<CurrentProcess> comparator = CurrentComparator.getCurrentComparator();
		ProcessList.getRunning_processes().sort(comparator);
	}
}
