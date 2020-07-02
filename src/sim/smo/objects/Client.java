package sim.smo.objects;

import sim.random.SimGenerator;
import sim.smo.events.LeaveQueue;

/* Klasa reprezentująca klienta */
public class Client {
    static int n_clients = 0;
    public final int id; /* unikalny identyfikator klienta */
    public double t_impatience; /* czas zniecierpliwienia klienta; maksymalny czas jaki klient może przebywać w kolejce */
    public double create_time; /* czas pojawienia się klienta w systemie */
    EClientStates state; /* stan w jakim znajduje się klient */
    private LeaveQueue leaveQueue;

    public Client(double t_impatience_min, double t_impatience_max, double create_time) {
        this.id = ++n_clients;
        this.t_impatience = generateImpatienceTime(t_impatience_min, t_impatience_max);
        this.create_time = create_time;
        setState(EClientStates.WaitingInQueue);
    }

    /* Metoda wykorzystywana do generowania czasu zniecierpliwienia klientów */
    private double generateImpatienceTime(double t_impatience_min, double t_impatience_max) {
        SimGenerator sg = new SimGenerator();
        return sg.uniform(t_impatience_min,t_impatience_max); /* zwrócenie czasu zniecierpliwienia klienta - liczby o rozkładzie jednostajnym (t_impatience_min, t_impatience_max) */
    }

    /* Zwraca informację, czy klient czeka w kolejce */
    public boolean isWaitingInQueue(){
        return state == EClientStates.WaitingInQueue;
    }

    /* Zwraca informację, czy klient jest obsługiwany */
    public boolean isInServicePosition(){
        return state == EClientStates.InServicePosition;
    }

    /* Zwraca informację, czy klient opuścił kolejkę z powodu zniecierpliwienia */
    public boolean hasLeftBecauseOfImpatience(){
        return state == EClientStates.LeftBecauseOfImpatience;
    }

    /* Zwraca informację, czy klient został obsłużony */
    public boolean isServed(){
        return state == EClientStates.Served;
    }

    /* Getter dla leaveQueue */
    public LeaveQueue getLeaveQueue() {
        return leaveQueue;
    }

    /* Setter dla state */
    public void setState(EClientStates state){
        if(this.state != state) {
            this.state = state;
        } else {
            System.out.println("Klient: "+this.id+" znajduje się już w podanym stanie!");
        }
    }

    /* Setter dla leaveQueue */
    public void setLeaveQueue(LeaveQueue leaveQueue) {
        this.leaveQueue = leaveQueue;
    }
}
