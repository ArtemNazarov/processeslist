package os.course_work.visual.dialogs;

import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

public class MyNotification {

    public static void createSuccessfulNotification(String text, String title){
        Notifications notification = Notifications.create();
        notification.text(text);
        notification.title(title);
        notification.showInformation();
        notification.position(Pos.BOTTOM_LEFT);
    }
    public static void createErrorNotification(String text, String title){
        Notifications notification =  Notifications.create();
        notification.text(text);
        notification.title(title);
        notification.showError();
        notification.position(Pos.BOTTOM_LEFT);
    }

}
