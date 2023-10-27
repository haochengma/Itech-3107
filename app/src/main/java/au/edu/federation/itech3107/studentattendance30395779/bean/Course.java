package au.edu.federation.itech3107.studentattendance30395779.bean;

import org.litepal.crud.LitePalSupport;

public class Course extends LitePalSupport {

    private Integer id;

    // Course name
    private String name;

    private String tmp;
    // Start date
    private String start;
    // End date
    private String end;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}