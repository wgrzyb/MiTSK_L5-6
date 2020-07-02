package sim.smo.events;

import sim.core.Manager;
import sim.smo.ServiceSystem;
import sim.smo.objects.Client;
import sim.smo.objects.EClientStates;
import sim.smo.objects.ServicePosition;

/* Klasa reprezentująca zdarzenie rozpoczęcia obsługi klientów */
public class StartService extends ServiceSystemEvent {

    public StartService(Manager mgr, double runTime, int priority, ServiceSystem serviceSystem) {
        super(mgr, runTime, priority, serviceSystem);
    }

    @Override
    public void stateChange() throws Exception {
        while(getServiceSystem().isAnyServicePositionFree() && getServiceSystem().isAnyClientInQueue()){
            ServicePosition servicePosition = getServiceSystem().getFreeServicePosition(); /* pobranie wolnego stanowiska */
            Client client = getServiceSystem().getClientFromQueue(); /* pobranie (pierwszego) klienta czekającego w kolejce */
            getSimMgr().unregisterEvent(client.getLeaveQueue()); /* usunięcie zaplanowanego zdarzenia opuszczenia kolejki przez klienta z powodu zniecierpliwienia */
            getServiceSystem().queue.remove(client); /* usunięcie klienta z kolejki */
            getServiceSystem().queueLength.setValue(getServiceSystem().queue.size(), simTime()); /* ustawienie wartości dla monitorowanej zmiennej queueLength */
            servicePosition.startService(client); /* rozpoczęcie obsługi */
            getServiceSystem().waitTime.setValue(simTime()-client.create_time, simTime()); /* ustawienie wartości dla monitorowanej zmiennej waitTime */
            client.setState(EClientStates.InServicePosition); /* ustawienie statusu klientowi */
            System.out.printf("%.2f\t Rozpoczęto obsługę klienta. Stanowisko "+servicePosition.id+" rozpoczęło obsługę klienta "+client.id+".\n",simTime());
            planEndServiceEvent(servicePosition);
        }
    }

    /* Zaplanowanie zdarzenia zakończenia obsługi */
    private void planEndServiceEvent(ServicePosition servicePosition){
        double t_service = servicePosition.generateServiceTime(); /* wygenerowany czas obsługi klienta przez stanowisko */
        servicePosition.serviceTime.setValue(t_service, simTime()); /* ustawienie wartości dla monitorowanej zmiennej serviceTime */
        double nextRunTime = getRunTime()+t_service; /* punkt czasu, w którym stanowisko zakończy obsługiwać klienta */
        new EndService(getSimMgr(), nextRunTime,2, getServiceSystem(), servicePosition); /* zaplanowanie zdarzenia końca obsługi klienta przez stanowisko */
    }
}