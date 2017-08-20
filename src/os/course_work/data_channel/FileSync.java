package os.course_work.data_channel;

import java.io.File;

public class FileSync {

    private static boolean firstLaunch = true;
    private static File file;

    {
        file.mkdirs();
    }

    public synchronized static File getFile() {
        return file;
    }

    public static boolean isFirstLaunch() {
        return firstLaunch;
    }

    public static void setFile(File file) {
        FileSync.file = file;
    }

    public static void setFirstLaunch(boolean firstLaunch) {
        FileSync.firstLaunch = firstLaunch;
    }

    private FileSync() {
    }
}
