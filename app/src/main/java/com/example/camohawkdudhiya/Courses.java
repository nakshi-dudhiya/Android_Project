package com.example.camohawkdudhiya;

public class Courses {
    public String _id;
    public String program;
    public String semesterNum;
    public String courseTitle;
    public String courseDescription;



    @Override
    public String toString() {
        return program + " " + " (Sem: "+semesterNum+" )" + "\nCourse Title: " + courseTitle+ "\nCourse Description: " + courseDescription;

    }
}