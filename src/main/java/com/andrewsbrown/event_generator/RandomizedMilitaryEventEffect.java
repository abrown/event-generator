/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.andrewsbrown.event_generator;

import java.util.Random;

/**
 *
 * @author andrew
 */
enum RandomizedMilitaryEventEffect implements EventEffect{
    LOW(new Segment<>(0, 3), new Segment<>(0, 0), new Segment<>(0, 1)), 
    MODERATE(new Segment<>(2, 5), new Segment<>(0, 1), new Segment<>(0, 1)), 
    SEVERE(new Segment<>(2, 7), new Segment<>(1, 3), new Segment<>(0, 2));

    private RandomizedMilitaryEventEffect(Segment<Integer> wia, Segment<Integer> kia, Segment<Integer> vix) {
        this.wia = wia;
        this.kia = kia;
        this.vix = vix;
    }
    
    private final Segment<Integer> wia;
    private final Segment<Integer> kia;
    private final Segment<Integer> vix;

    @Override
    public String toString(){
       return randomize(kia) + " KIA, " + randomize(wia) + " WIA, " + randomize(vix) + " damaged VIX";
    }
    
    private int randomize(Segment<Integer> segment) {
        Random rand = new Random();
        return rand.nextInt((segment.end - segment.begin) + 1) + segment.begin;
    }
}
