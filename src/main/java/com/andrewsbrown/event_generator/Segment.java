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
class Segment<T> {

    T begin;
    T end;

    public Segment(T begin, T end) {
        this.begin = begin;
        this.end = end;
    }

    public boolean isPoint() {
        return begin.equals(end);
    }
}
