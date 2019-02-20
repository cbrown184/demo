package com.example.demo.service;

import com.example.demo.model.Entry;
import com.example.demo.model.EntryPair;
import com.example.demo.model.Event;
import com.example.demo.persistance.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public void processEntryPairs(List<EntryPair> eventList){
        eventList.parallelStream().forEach(entryPair -> {
            try{
                Event e = new Event(
                            entryPair.getId(),
                            entryPair.getTimeDifference(),
                            entryPair.getHost(),
                            entryPair.getType(),
                            entryPair.isAlert()
                        );
                log.debug("Processed event : " + entryPair);
                eventRepository.save(e);
            }
            catch (Exception e){
                log.error("Cannot save event " + entryPair.toString(), e);
            }
        });
    }

    public Map<String, EntryPair> processJson(String filePath){

        //Get Entries
        ObjectMapper mapper = new ObjectMapper();
        log.info("Reading file - " + filePath);
        //TypeReference <Entry> typeReference = new TypeReference<Entry>(){};
        List<Entry> entries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                log.debug(line);
                entries.add(mapper.readValue(line, Entry.class));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Pair them

        Map<String, EntryPair> entryPairMap = new HashMap();

        entries.stream().forEach(entry -> {
            log.debug("Processing entry " + entry);
            boolean isFinished = entry.getState().equalsIgnoreCase("finished");
            if(entryPairMap.containsKey(entry.getId())){
                if(isFinished){
                    entryPairMap.get(entry.getId()).setEntryFinished(entry);
                } else {
                    entryPairMap.get(entry.getId()).setEntryStarted(entry);
                }
            } else {
                EntryPair entryPair = new EntryPair();
                if(isFinished){
                    entryPair.setEntryFinished(entry);
                } else {
                    entryPair.setEntryStarted(entry);
                }
                entryPairMap.put(entry.getId(), entryPair);
            }
        });

        return entryPairMap;
    }

}

