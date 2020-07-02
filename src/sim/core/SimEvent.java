package sim.core;

/* Klasa reprezentująca zdarzenie w systemie */
public abstract class SimEvent {
    private Manager simMgr;
    private final double runTime; /* punkt czasu, w którym zdarzenie powinno zostać wykonane */
    private final int priority; /* priorytet zdarzenia, im większy tym zdarzenie bardziej priorytetowe */

    public SimEvent(Manager mgr, double runTime, int priority) {
        this.runTime = runTime; //bardzo ważne, aby ustawić runTime przed zarejestrowaniem eventu w Managerze (dodaniem zdarzenia do kalendarza), w przeciwnym wypadku zdarzenie zostanie dodane w niewłaściwy sposób do QueuePriority - runTime wzięty do porównywania zdarzeń będzie wynosić 0 zamiast simTime().
        this.priority = priority;

        if (mgr!=null) {
            simMgr = mgr;
            // 	rejestracja w Managerze symulacji
            simMgr.registerEvent(this);
        }
    }

    /* Zwrócenie obecnego czasu symulacji */
    public final double simTime() {
        if (simMgr!=null)
            return simMgr.simTime();
        else
            return 0.0;
    }

    /* Funkcja zmiany stanu */
    public abstract void stateChange() throws Exception;

    /* Getter dla simMgr */
    public Manager getSimMgr() {
        return this.simMgr;
    }

    /* Getter dla runTime */
    public double getRunTime() {
        return this.runTime;
    }

    /* Getter dla priority */
    public int getPriority() {
        return priority;
    }
}
