package os.course_work.visual.main;

import javafx.stage.Stage;

public class ReadTable {

    private static Stage table_stage;

    public static synchronized Stage getTableStage(){
        return table_stage;
    }

    public static void setTable_stage(Stage table_stage) {
        ReadTable.table_stage = table_stage;
    }
}

