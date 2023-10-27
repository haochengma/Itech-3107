package au.edu.federation.itech3107.studentattendance30395779.bean;

import org.litepal.crud.LitePalSupport;

public class Student extends LitePalSupport {

    private Integer id;

    private String sid;

    private String name;

    private String coursename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }
}



