package com.training.myassistantapp;

public class Complaints {

    public String issue;

    public Complaints(){

    }

    public Complaints(String issue, String uid) {
        this.issue = issue;
    }

    @Override
    public String toString() {
        return "Complaints{" +
                "issue='" + issue + '\'' +
                '}';
    }
}
