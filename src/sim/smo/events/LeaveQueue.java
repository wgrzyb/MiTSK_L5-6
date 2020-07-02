package sim.smo.events;

import sim.core.Manager;
import sim.smo.ServiceSystem;
import sim.smo.objects.Client;
import sim.smo.objects.EClientStates;

/* Klasa reprezentująca zdarzenie opuszczenia kolejki przez klienta z powodu zniecierpliwienia */
public class LeaveQueue extends ServiceSystemEvent {
    private final Client client;

    public LeaveQueue(Manager mgr, double runTime, int priority, ServiceSystem serviceSystem, Client client) {
        super(mgr, runTime, priority, serviceSystem);
        this.client = client;
    }

    @Override
    public void stateChange() {
        getServiceSystem().queue.remove(client); /* opuszczenie kolejki przez klienta */
        getServiceSystem().queueLength.setValue(getServiceSystem().queue.size(), simTime()); /* ustawienie wartości dla monitorowanej zmiennej queueLength */
        client.setState(EClientStates.LeftBecauseOfImpatience); /* ustawienie statusu klientowi */
        System.out.printf("%.2f\t Opuszczenie kolejki przez klienta. Klient "+client.id+" opuścił kolejkę z powodu zniecierpliwienia.\n",simTime());
    }
}
