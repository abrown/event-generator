/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andrewsbrown.event_generator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author andrew
 */
class Event implements Comparable<Event>{
    LocalDateTime date;
    EventType type;
    EventEffect effect;
    Location location;
    
    public Event(LocalDateTime date){
        this.date = date;
    }

    public Event(LocalDateTime date, EventType type, EventEffect effect, Location location) {
        this.date = date;
        this.type = type;
        this.effect = effect;
        this.location = location;
    }
    
    @Override
    public String toString(){
        String out = date.format(DateTimeFormatter.ofPattern("ddHHmmMMMuuuu")).toUpperCase() + ": ";
        out += type + " ";
        out += effect + ", ";
        out += "VIC " + location;
        return out;
    }

    @Override
    public int compareTo(Event o) {
        return this.date.compareTo(o.date);
    }
}
