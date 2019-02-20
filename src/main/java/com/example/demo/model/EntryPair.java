package com.example.demo.model;

import lombok.Data;
@Data
public class EntryPair {
    private Entry entryStarted;
    private Entry entryFinished;

    public String getId(){
        return entryStarted.getId() == null? entryFinished.getId():entryStarted.getId();
    }

    public long getTimeDifference(){
        return  Long.valueOf(entryFinished.getTimestamp())  - Long.valueOf(entryStarted.getTimestamp());
    }

    public String getHost(){
        return entryStarted.getHost() == null? entryFinished.getHost():entryStarted.getHost();
    }

    public String getType(){
        return entryStarted.getType() == null? entryFinished.getType():entryStarted.getType();
    }

    public boolean isAlert(){
        return getTimeDifference() > 4;
    }

}
