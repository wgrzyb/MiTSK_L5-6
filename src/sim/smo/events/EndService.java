package sim.smo.events;

import sim.core.Manager;
import sim.smo.ServiceSystem;
import sim.smo.objects.EClientStates;
import sim.smo.objects.ServicePosition;

/* Klasa reprezentująca zdarzenie zakończenia obsługi klienta przez stanowisko obsługi */
public class EndService extends ServiceSystemEvent{
    private final ServicePosition servicePosition;

    public EndService(Manager mgr, double runTime, int priority, ServiceSystem serviceSystem, ServicePosition servicePosition) {
        super(mgr, runTime, priority, serviceSystem);
        this.servicePosition = servicePosition;
    }

    @Override
    public void stateChange() {
        if(servicePosition.isBusy()) {
            servicePosition.getClient().setState(EClientStates.Served); /* ustawienie statusu klientowi */
            System.out.printf("%.2f\t Zakończono obsługę klienta. Stanowisko " + servicePosition.id + " zakończyło obsługę klienta " + servicePosition.getClient().id + ".\n",simTime());
            servicePosition.endService();
        }
        planStartServiceEvent();
    }

    /* Zaplanowanie zdarzenia rozpoczęcia obsługi klientów */
    private void planStartServiceEvent(){
        /* Zaplanowanie zdarzenia rozpoczęcia obsługi klientów */
        if(getServiceSystem().isAnyServicePositionFree() && getServiceSystem().isAnyClientInQueue()) {
            new StartService(getSimMgr(), getRunTime(),1, getServiceSystem());
        }
    }
}
