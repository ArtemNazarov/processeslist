package os.course_work.data_channel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.TimerTask;

import javafx.application.Platform;
import os.course_work.visual.dialogs.MyNotification;
import os.course_work.visual.dialogs.DialogManager;
import os.course_work.sysmonitor.CurrentProcess;
import os.course_work.sysmonitor.ProcessList;
import os.course_work.threads.ProgramThreads;
import os.course_work.threads.Tasks;

public class WriterProcess extends TimerTask {

	ResourceBundle bundle = ResourceBundle.getBundle("os.course_work.bundles.ResBundle");

	@Override
	public void run() {
		BufferedWriter bufwriter = null;
		try {
			bufwriter = new BufferedWriter(new FileWriter(FileSync.getFile(), false));
			for (CurrentProcess process : ProcessList.getRunning_processes())
				bufwriter.append(process.toString() + '\n');
			Platform.runLater(() -> MyNotification.createSuccessfulNotification(bundle.getString("success.write"),
					bundle.getString("success")));
			bufwriter.close();
		} catch (IOException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					MyNotification.createErrorNotification(bundle.getString("error.write.file"),
							bundle.getString("error"));
					DialogManager.showErrorDialog(bundle.getString("error.write.file"), e);
					Tasks.tasks.get(1).cancel();
				}
			});
		} catch (NullPointerException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					DialogManager.showErrorDialog(bundle.getString("error.nofile"), e);
					Tasks.tasks.get(1).cancel();
				}
			});
		}
	}

}
