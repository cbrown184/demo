package com.example.demo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Entry {

    final private String id;
    final private String state;
    final private String timestamp;
    final private String host;
    final private String type;

    Entry() {
        id = null;
        state = null;
        timestamp = null;
        host = null;
        type = null;
    }
}
