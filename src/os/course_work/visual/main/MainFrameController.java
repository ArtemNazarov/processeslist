package os.course_work.visual.main;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import os.course_work.comparators.*;
import os.course_work.data_channel.FileSync;
import os.course_work.data_channel.ReadProcess;
import os.course_work.data_channel.ReadProcesssList;
import os.course_work.data_channel.WriterProcess;
import os.course_work.sysmonitor.CurrentProcess;
import os.course_work.sysmonitor.ProcessList;
import os.course_work.sysmonitor.ProcessMonitor;
import os.course_work.threads.ProgramThreads;
import os.course_work.threads.Tasks;
import os.course_work.visual.dialogs.DialogManager;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrameController implements Initializable {

    public TableView<CurrentProcess> processesTable;
    public RadioMenuItem processNameSort;
    public CheckMenuItem autoRefreshListItem;
    public TableColumn<CurrentProcess, String> processColumn;
    public TableColumn<CurrentProcess, Integer> pIDColumn;
    public TableColumn<CurrentProcess, Integer> parentIDColumn;
    public TableColumn<CurrentProcess, Integer> threadCountColumn;
    public TableColumn<CurrentProcess, Integer> priorityColumn;
    ResourceBundle bundle = ResourceBundle.getBundle("os.course_work.bundles.ResBundle");
    private Timer monitorTimer = new Timer();
    private Timer writerTimer = new Timer();
    private Timer readTimer = new Timer();
    ProcessMonitor taskMonitor = new ProcessMonitor();
    WriterProcess taskWriter = new WriterProcess();
    ReadProcess taskReader = new ReadProcess();

    public void closeProgram(ActionEvent actionEvent) {
        Main.closeApp();
    }

    public void createMonitor(boolean withRepeat) {
        ProcessMonitor current_task = new ProcessMonitor();
        Tasks.tasks.add(current_task);
        if (withRepeat)
            monitorTimer.scheduleAtFixedRate(current_task, 1000, 3000);
        else
            monitorTimer.schedule(current_task, 1000);
    }

    public void createWriter(boolean withRepeat) {
        WriterProcess task = new WriterProcess();
        Tasks.tasks.add(task);
        if (withRepeat)
            writerTimer.scheduleAtFixedRate(task, 1000, 4000);
        else
            writerTimer.schedule(task, 1000);
    }

    public void createReader(boolean withRepeat) {
        ReadProcess task = new ReadProcess();
        Tasks.tasks.add(task);
        if (withRepeat)
            readTimer.scheduleAtFixedRate(task, 1000, 5000);
        else
            readTimer.schedule(task, 1000);
    }

    public Stage createReadProcessTable() {
        Stage stage = new Stage();
        ObservableList<CurrentProcess> running_processes = ReadProcesssList.getRunning_processes();
        TableColumn<CurrentProcess, String> processColumn = new TableColumn<>(bundle.getString("process"));
        processColumn.setPrefWidth(246);
        TableColumn<CurrentProcess, Integer> pIDColumn = new TableColumn<>("PID");
        pIDColumn.setPrefWidth(93);
        TableColumn<CurrentProcess, Integer> parentIDColumn = new TableColumn<>(bundle.getString("parentID"));
        parentIDColumn.setPrefWidth(69);
        TableColumn<CurrentProcess, Integer> threadsCountCol = new TableColumn<>(bundle.getString("threadsCount"));
        threadsCountCol.setPrefWidth(79);
        TableColumn<CurrentProcess, Integer> basicPriCol = new TableColumn<>(bundle.getString("priority"));
        basicPriCol.setPrefWidth(200);
        TableView<CurrentProcess> tableView = new TableView<>();
        tableView.setEditable(true);
        tableView.getColumns().addAll(processColumn, pIDColumn, parentIDColumn, threadsCountCol, basicPriCol);
        tableView.setItems(running_processes);
        processColumn.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        pIDColumn.setCellValueFactory(celldata -> celldata.getValue().fPIDProperty().asObject());
        parentIDColumn.setCellValueFactory(celldata -> celldata.getValue().parentIDProperty().asObject());
        threadsCountCol.setCellValueFactory(celldata -> celldata.getValue().threadsCountProperty().asObject());
        basicPriCol.setCellValueFactory(celldata -> celldata.getValue().basicPriorityProperty().asObject());
        tableView.setItems(running_processes);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        stage.setScene(new Scene(tableView, 810, 600));
        stage.setTitle(bundle.getString("readProcesses"));
        return stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProgramThreads.tasks.addAll(monitorTimer, writerTimer, readTimer);
        processNameSort.setSelected(true);
        processColumn.setCellValueFactory(celldata -> celldata.getValue().nameProperty());
        pIDColumn.setCellValueFactory(celldata -> celldata.getValue().fPIDProperty().asObject());
        parentIDColumn.setCellValueFactory(celldata -> celldata.getValue().parentIDProperty().asObject());
        threadCountColumn.setCellValueFactory(celldata -> celldata.getValue().threadsCountProperty().asObject());
        priorityColumn.setCellValueFactory(celldata -> celldata.getValue().basicPriorityProperty().asObject());
        processesTable.setItems(ProcessList.getRunning_processes());
        ReadTable.setTable_stage(createReadProcessTable());
    }

    public void sortByProcessName(ActionEvent actionEvent) {
        Comparator<CurrentProcess> comparator = new SortByName();
        CurrentComparator.setCurrentComparator(comparator);
    }

    public void sortByPID(ActionEvent actionEvent) {
        Comparator<CurrentProcess> comparator = new SortByPID();
        CurrentComparator.setCurrentComparator(comparator);
    }

    public void sortByParentID(ActionEvent actionEvent) {
        Comparator<CurrentProcess> comparator = new SortByParentID();
        CurrentComparator.setCurrentComparator(comparator);
    }

    public void sortByThreadsCount(ActionEvent actionEvent) {
        Comparator<CurrentProcess> comparator = new SortByThreadsCount();
        CurrentComparator.setCurrentComparator(comparator);
    }

    public void sortByPriority(ActionEvent actionEvent) {
        Comparator<CurrentProcess> comparator = new SortByPriority();
        CurrentComparator.setCurrentComparator(comparator);
    }


    public void showTable(ActionEvent actionEvent) {
        if (!ReadTable.getTableStage().isShowing()) {
            ReadTable.setTable_stage(createReadProcessTable());
            ReadTable.getTableStage().show();
        }
    }

    public void chooseFile() {
        if (FileSync.isFirstLaunch()) {
            FileChooser filech = new FileChooser();
            filech.setTitle(bundle.getString("dataChannel"));
            filech.setInitialDirectory(new File("resources/used"));
            filech.getExtensionFilters().add(new FileChooser.ExtensionFilter(bundle.getString("text"), "*.txt"));
            filech.getExtensionFilters().add(new FileChooser.ExtensionFilter(bundle.getString("allFiles"), "*.*"));
            filech.setInitialFileName("processes");
            FileSync.setFile(filech.showSaveDialog(Main.getPrimary()));
            FileSync.setFirstLaunch(false);
            createMonitor(true);
            createWriter(true);
            createReader(true);
        }
    }

    public void autoRefresh(ActionEvent actionEvent) {
    	
        if (autoRefreshListItem.isSelected()) {
        	Tasks.tasks.clear();
            createMonitor(true);
            createWriter(true);
            createReader(true);
        } else {
        	for (TimerTask task : Tasks.tasks) {
				task.cancel();
			}
        }
    }

    public void refreshList(ActionEvent actionEvent) {
    	Tasks.tasks.clear();
        createMonitor(false);
        createWriter(false);
        createReader(false);
    }

    public void showHelp() {
        File help = new File("resources/help/ProcessMonitor.chm");
        Desktop desktop = null;
        if (Desktop.isDesktopSupported())
            desktop = Desktop.getDesktop();
        try {
            desktop.open(help);
        } catch (IOException e) {
            DialogManager.showErrorDialog(bundle.getString("error.open.file"));
        } catch (IllegalArgumentException e) {
            DialogManager.showErrorDialog(bundle.getString("error.fileNotFound"), e);
        }
    }

    public void showAbout(ActionEvent actionEvent) {
        DialogManager.showInformationDialog(bundle.getString("aboutProgramLabel"), bundle.getString("about"));
    }
}
