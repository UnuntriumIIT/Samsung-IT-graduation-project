package com.example.dailydiary.dailydiaryalphav003;


public class PostData {
    public String postTitle;
    public String postDesc;
    public String what_img;
    public String date_main;
    public String day_result;

    PostData(String postTitle, String postDesc, String what_img, String date_main, String day_result) {
        this.postTitle =  postTitle;
        this.postDesc = postDesc;
        this.what_img = what_img;
        this.date_main = date_main;
        this.day_result = day_result;
    }
    public String getWhat_img() {
        return what_img;
    }

    public String getDate_main() {
        return date_main;
    }
}
