package sim.core;

import java.util.PriorityQueue;

/* Klasa reprezentująca kalendarz zdarzeń w systemie */
public class SimCalendar {
    private final PriorityQueue<SimEvent> simEvents = new PriorityQueue<>(new SimEventComparator());

    /* Dodaje nowe wydarzenie do kalendarza */
    public void addEvent(SimEvent event) {
        simEvents.add(event);
    }

    /* Zwraca następne zaplanowane wydarzenie w kalendarzu */
    public SimEvent peekEvent() {
        return simEvents.peek();
    }

    /* Zwraca następne zaplanowane wydarzenie, a także usuwa je z kalendarza */
    public SimEvent pollEvent() {
        return simEvents.poll();
    }

    /* Usuwa zaplanowane wydarzenie z kalendarza */
    public void remove(SimEvent event) {
        simEvents.remove(event);
    }

    public boolean isAnyEvent() {
        return simEvents.size()>0;
    }
}
