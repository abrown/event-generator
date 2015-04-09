/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andrewsbrown.event_generator;

import static java.lang.Math.round;
import static java.lang.StrictMath.cos;
import static java.lang.StrictMath.sin;
import static java.lang.StrictMath.sqrt;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 *
 * @author andrew
 */
class EventGenerator {

    static final Logger logger = Logger.getLogger(EventGenerator.class.toString());
    private EventType type;
    private Segment<Location> along;
    private int randomizationRadiusMeters = 500;
    private EventEffect[] effects;
    private Segment<LocalDateTime> duration;
    private TemporalAmount randomizationRange = Duration.ofMinutes(60);
    private TemporalAmount interval = Period.ofDays(1);

    public EventGenerator(LocalDateTime fromDate, LocalDateTime toDate) {
        this.duration = new Segment<>(fromDate, toDate);
    }

    EventGenerator onInterval(TemporalAmount interval) {
        this.interval = interval;
        return this;
    }

    EventGenerator withTimeRange(TemporalAmount range) {
        this.randomizationRange = range;
        return this;
    }

    EventGenerator ofType(EventType eventType) {
        this.type = eventType;
        return this;
    }

    EventGenerator along(Location location) {
        this.along = new Segment<>(location, location);
        return this;
    }

    EventGenerator along(Location startLocation, Location endLocation) {
        this.along = new Segment<>(startLocation, endLocation);
        return this;
    }

    EventGenerator withLocationRadius(int metersRadius) {
        this.randomizationRadiusMeters = metersRadius;
        return this;
    }

    EventGenerator withEffect(EventEffect... eventEffects) {
        this.effects = eventEffects;
        return this;
    }

    @Override
    public String toString() {
        List<Event> events = this.generate();
        return Arrays.toString(events.toArray());
    }

    public List<Event> generate() {
        List<Event> events = new ArrayList<>();

        // add event on interval
        LocalDateTime current = duration.begin;
        while (current.isBefore(duration.end)) {
            LocalDateTime randomizedTime = randomize(current, randomizationRange);
            events.add(new Event(randomizedTime));
            current = current.plus(interval);
        }

        // add locations
        if (along != null) {
            if (along.isPoint()) {
                setSameLocation(events, along.begin, randomizationRadiusMeters);
            } else {
                setAlongLine(events, along, randomizationRadiusMeters);
            }
        }

        // add types
        setSameType(events, type);

        // add effects
        setSameEffect(events, randomPick(effects));

        // add effects
        return events;
    }

    private void setSameLocation(List<Event> events, Location point, int radius) {
        for (Event event : events) {
            event.location = randomize(point, radius);
        }
    }

    private Location randomize(Location point, int radius) {
        double w = radius * sqrt(new Random().nextDouble());
        double t = 2 * Math.PI * new Random().nextDouble();
        double x = w * cos(t);
        double y = w * sin(t);
        return new Location(point.x + (int) round(x), point.y + (int) round(y));
    }

    private void setAlongLine(List<Event> events, Segment<Location> along, int radius) {
        for (int i = 0, end = events.size(); i < end; i++) {
            double t = i / end;
            int x = (int) round(along.begin.x * (1 - t) + along.end.x * t);
            int y = (int) round(along.begin.y * (1 - t) + along.end.y * t);
            events.get(i).location = randomize(new Location(x, y), radius);
        }
    }

    private void setSameType(List<Event> events, EventType type) {
        for (Event event : events) {
            event.type = type;
        }
    }

    private void setSameEffect(List<Event> events, EventEffect effect) {
        for (Event event : events) {
            event.effect = effect;
        }
    }

    private EventEffect randomPick(EventEffect[] effects) {
        if (effects.length == 0) {
            throw new IllegalArgumentException("No effects to pick.");
        }
        if (effects.length == 1) {
            return effects[0];
        }
        // otherwise
        return effects[new Random().nextInt(effects.length)];
    }

    private LocalDateTime randomize(LocalDateTime current, TemporalAmount range) {
        int _range = (int) range.get(ChronoUnit.SECONDS);
        int seconds = new Random().nextInt(_range) - _range;
        return LocalDateTime.from(current).plusSeconds(seconds);
    }
}
