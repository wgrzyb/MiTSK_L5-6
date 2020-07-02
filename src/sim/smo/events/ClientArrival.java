package sim.smo.events;

import sim.core.Manager;
import sim.random.SimGenerator;
import sim.smo.ServiceSystem;
import sim.smo.objects.Client;

/* Klasa reprezentująca zdarzenie przybycia klienta */
public class ClientArrival extends ServiceSystemEvent {
    private final double t_impatience_min; /* minimalny czas zniecierpliwienia klientów */
    private final double t_impatience_max; /* maksymalny czas zniecierpliwienia klientów */
    private final double a; /* parametr funkcji wykładniczej wykorzystywany przy generowaniu odstępu czasu pomiędzy pojawieniem się kolejnych klientów */

    public ClientArrival(Manager mgr, double runTime, int priority, ServiceSystem serviceSystem, double t_impatience_min, double t_impatience_max, double a) {
        super(mgr, runTime, priority, serviceSystem);
        this.t_impatience_min = t_impatience_min;
        this.t_impatience_max = t_impatience_max;
        this.a = a;
    }

    @Override
    public void stateChange() {
        Client client = new Client(t_impatience_min, t_impatience_max, simTime()); /* utworzenie nowego klienta */
        double nextRunTime = simTime()+client.t_impatience; /* punkt czasu, w którym klient opuści kolejkę, jeśli do tego czasu nie zostanie obsłużony */
        LeaveQueue leaveQueue = new LeaveQueue(getSimMgr(), nextRunTime ,0, getServiceSystem(), client); /* zaplanowanie zdarzenia opuszczenia kolejki przez klienta z powodu zniecierpliwienia */
        client.setLeaveQueue(leaveQueue);

        // Dodanie utworzonego klienta do kolejki oraz listy wygenerowanych klientów
        getServiceSystem().queue.add(client); /* dodanie klienta do kolejki */
        getServiceSystem().queueLength.setValue(getServiceSystem().queue.size(), simTime()); /* ustawienie wartości dla monitorowanej zmiennej queueLength */
        getServiceSystem().clients.add(client); /* dodanie klienta do listy klientów */
        System.out.printf("%.2f\t Pojawił się nowy klient. Klient "+client.id+" dołączył do kolejki.\n",simTime());
        planNext(); /* zaplanowanie kolejnych zdarzeń */
    }

    /* Zaplanowanie kolejnych wydarzeń */
    private void planNext(){
        /* Zaplanowanie zdarzenia kolejnego przybycia klienta */
        SimGenerator sg = new SimGenerator(); /* utworzenie nowego generatora bez podania ziarna */
        double interval = sg.exponential(this.a); /* wygenerowanie nowej liczby o rozkładzie wykładniczym (a) - odstęp czasiu między pojawieniem się kolejnego klienta */
        double nextRunTime = getRunTime()+interval; /* punkt czasu, w którym pojawi się kolejny klient */
        new ClientArrival(getSimMgr(), nextRunTime, getPriority(), getServiceSystem(), t_impatience_min, t_impatience_max, a);
        /* Zaplanowanie zdarzenia rozpoczęcia obsługi klientów */
        if(getServiceSystem().isAnyServicePositionFree() && getServiceSystem().isAnyClientInQueue()) {
            new StartService(getSimMgr(), getRunTime(),1, getServiceSystem());
        }
    }
}
