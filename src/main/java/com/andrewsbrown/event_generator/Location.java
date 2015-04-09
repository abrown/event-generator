/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andrewsbrown.event_generator;

/**
 *
 * @author andrew
 */
class Location {
    final int x;
    final int y;
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o){
        if(o == null || !(o instanceof Location))
            return false;
        Location l = (Location) o;
        return l.x == this.x && l.y == this.y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.x;
        hash = 19 * hash + this.y;
        return hash;
    }
    
    @Override
    public String toString(){
        return "GS " + String.format("%04d", this.x) + " " + String.format("%04d", this.y);
    }
}
