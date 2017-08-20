package os.course_work.data_channel;

import os.course_work.sysmonitor.CurrentProcess;
import os.course_work.threads.Tasks;
import os.course_work.visual.dialogs.DialogManager;

import java.io.*;
import java.util.ResourceBundle;
import java.util.TimerTask;

import javafx.application.Platform;

public class ReadProcess extends TimerTask {

	private ResourceBundle bundle = ResourceBundle.getBundle("os.course_work.bundles.ResBundle");

	@Override
	public void run() {
		BufferedReader bf = null;
		try {
			bf = new BufferedReader(new FileReader(FileSync.getFile()));
		} catch (FileNotFoundException e) {
			Platform.runLater(() -> DialogManager
					.showErrorDialog(bundle.getString("error") + ". " + bundle.getString("error.fileNotFound"), e));
		} catch (NullPointerException e) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					DialogManager.showErrorDialog(bundle.getString("error.nofile"), e);
					Tasks.tasks.get(2).cancel();
				}
			});
		}
		String tmp = "";
		try {
			ReadProcesssList.getRunning_processes().clear();
			while ((tmp = bf.readLine()) != null) {
				String[] parts = tmp.split("\\t");
				CurrentProcess process = new CurrentProcess(parts[0], Integer.parseInt(parts[1]),
						Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
				ReadProcesssList.addProcess(process);
			}
			bf.close();
		} catch (IOException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					DialogManager.showErrorDialog(bundle.getString("error.read"), e);
					Tasks.tasks.get(2).cancel();
				}
			});
		}
	}
}
