package fitsweather;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ObservationLog {
    public static boolean createLog() {
        String basePath = FilesystemHelper.getFolderPath();
        File[] files = FilesystemHelper.getAllFitsFiles(basePath);
        List<FitsInfo> fitsInfo = new ArrayList<FitsInfo>();
        if (files != null) {
            for (File file : files) {
                FitsInfo fi = FitsHelper.getFitsInfo(file.getAbsolutePath(), file.getName());
                if (fi != null) fitsInfo.add(fi);
            }
        } else {
            System.out.println("No files to log.");
            return false;
        }

        return writeLongLog(basePath,fitsInfo);
    }

    private static boolean writeLongLog(String path, List<FitsInfo> fitsInfo) {
        List<String> lines = new ArrayList<>();
        Date date = new Date(System.currentTimeMillis() - 28800000);   //8 hours in milliseconds
        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        lines.add("[" + df.format(date) + "]\n");   //date info
        lines.add("target;filter;focus;frame_type;time;filename");    //header

        for (FitsInfo f : fitsInfo) {
            lines.add(f.getTarget() + ";" +
                    f.getFilter() + ";" +
                    f.getFocus() + ";" +
                    f.getType() + ";" +
                    f.getTime() + ";" +
                    f.getFilename());
        }
        String logDir = ConfigHelper.getInstance().getLogDir();
        Path file = Paths.get(path + df.format(date) + "_long.log");
        Path file2 = Paths.get(logDir + df.format(date) + "_long.log");
        new File(logDir).mkdir();
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
            Files.write(file2, lines, Charset.forName("UTF-8"));
        }catch (Exception ex){
            return false;
        }
        return true;
    }
}
