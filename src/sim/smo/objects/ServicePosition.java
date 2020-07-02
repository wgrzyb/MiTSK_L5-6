package sim.smo.objects;

import sim.monitors.MonitoredVar;
import sim.random.SimGenerator;

/* Klasa reprezentująca stanowisko - pozycję obsługi */
public class ServicePosition {
    static int n_service_positions = 0;
    public final int id; /* unikalny identyfikator stanowiska */
    private final double t_service_min; /* minimalny czas obsługi przez stanowisko */
    private final double t_service_max; /* maksymalny czas obsługi przez stanowisko */
    private Client client; /* obsługiwany przez stanowisko klient; jeśli Null to stanowisko jest wolne */
    public MonitoredVar serviceTime = new MonitoredVar(); /* monitorowana zmienna reprezentująca czasy obsługi klientów */

    public ServicePosition(double t_service_min, double t_service_max){
        this.id = ++n_service_positions;
        this.t_service_min = t_service_min;
        this.t_service_max = t_service_max;
    }

    /* Zwraca informację, czy stanowisko jest zajęte */
    public boolean isBusy() {
        return client != null;
    }

    /* Zwraca informację, czy stanowisko jest wolne */
    public boolean isFree() {
        return client == null;
    }

    /* Wygenerowanie czasu obsługi */
    public double generateServiceTime(){
        SimGenerator sg = new SimGenerator();
        return sg.uniform(t_service_min, t_service_max); /* wygenerowanie liczby o rozkładzie jednostajnym (t_service_min, t_service_max) - czas obsługi przez stanowisko */
    }

    /* Rozpoczęcie obsługi klienta */
    public void startService(Client client) {
        this.client = client;
    }

    /* Zakończenie obsługi klienta */
    public void endService() {
        this.client = null;
    }

    /* Getter dla client */
    public Client getClient() {
        return this.client;
    }
}
