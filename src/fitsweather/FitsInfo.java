package fitsweather;

public class FitsInfo {
    private String target;
    private String filter;
    private Integer focus;
    private String time;
    private String type;
    private String filename;


    public FitsInfo(String target, String filter, Integer focus, String time, String type, String filename) {
        this.target = target;
        this.filter = filter;
        this.focus = focus;
        this.time = time;
        this.filename = filename;
        this.type = type;
    }

    @Override
    public String toString() {
        return "target='" + target + '\'' +
                ", filter='" + filter + '\'' +
                ", focus=" + focus +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }

    public String getTarget() {
        return target;
    }

    public String getFilter() {
        return filter;
    }

    public Integer getFocus() {
        return focus;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public String getFilename() {
        return filename;
    }
}
