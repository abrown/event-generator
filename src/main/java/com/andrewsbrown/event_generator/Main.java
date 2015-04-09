/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andrewsbrown.event_generator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author andrew
 */
public class Main {
    static final Logger logger = Logger.getLogger(Main.class.toString());
    public static void main(String[] args){
        LocalDateTime begin = LocalDateTime.of(2015, 5, 20, 12, 0);
        LocalDateTime end = LocalDateTime.of(2015, 6, 22, 12, 0);
        
        List<Event> events = new ArrayList<>();
        events.addAll(new EventGenerator(begin, end)
                .onInterval(Period.ofDays(2))
                .ofType(MilitaryEventType.IED)
                .along(new Location(1000, 1000))
                .withEffect(RandomizedMilitaryEventEffect.MODERATE)
                .generate());
        events.addAll(new EventGenerator(begin, end)
                .ofType(MilitaryEventType.IDF)
                .along(new Location(1000, 1000))
                .withEffect(RandomizedMilitaryEventEffect.LOW)
                .generate());
        events.addAll(new EventGenerator(begin, end)
                .onInterval(Duration.ofHours(12))
                .ofType(MilitaryEventType.SAF)
                .along(new Location(1000, 1000))
                .withEffect(RandomizedMilitaryEventEffect.LOW)
                .generate());
        
        events.sort(null);
        printEvents(events);
    }

    private static void printEvents(List<Event> events) {
        for(Event event : events){
            System.out.println(event);
        }
    }
}
