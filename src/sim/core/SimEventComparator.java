package sim.core;

import java.util.Comparator;

/* Komparator dla klasy SimEvent
   Wydarzenia zaplanowane wcześniej będą wykonywane jako pierwsze
   Jeśli dwa wydarzenia są zaplanoweane w tym samym punkce czasu, wówczas zostanie wykonane to z większym priorytetem
   Jeśli dwa wydarzenia są zaplanoweane w tym samym punkce czasu i mają taki sam priorytet, wówczas zostanie wykonane zdarzenie, które zostało dodane wcześniej do kalendarza
*/
public class SimEventComparator implements Comparator<SimEvent> {
    @Override
    public int compare(SimEvent event1, SimEvent event2) {
        if(event1.getRunTime() < event2.getRunTime() || (event1.getRunTime() == event2.getRunTime() && event1.getPriority() > event2.getPriority())) {
            return -1;
        } else if(event1.getRunTime() == event2.getRunTime() && event1.getPriority() == event2.getPriority()) {
            return 0;
        } else {
            return 1;
        }
    }
}