import sim.smo.ServiceSystem;

public class Main {

    public static void main(String[] args) throws Exception {
        /* Określenie parametrów */
        int n_service_positions = 3; /* liczba stanowisk obsługi */
        double startSimTime = 0.0; /* czas rozpoczęcia symulacji */
        double t_service_min = 7.0; /* minimalny czas obsługi przez stanowisko */
        double t_service_max = 10.0; /* maksymalny czas obsługi przez stanowisko */
        double t_impatience_min = 4.0; /* minimalny czas zniecierpliwienia klientów */
        double t_impatience_max = 9.0; /* maksymalny czas zniecierpliwienia klientów */
        double a = Math.E; /* parametr funkcji wykładniczej wykorzystywany przy generowaniu odstępu czasu pomiędzy pojawieniem się kolejnych klientów */
        double endSimTime = 70.0; /* czas trwania symulacji */

        /* Utworzenie obiektu koordynatora symulacji */
        ServiceSystem serviceSystem = ServiceSystem.getInstance(n_service_positions, startSimTime, t_service_min, t_service_max, t_impatience_min, t_impatience_max, a);
        /* Rozpoczęcie syulacji */
        serviceSystem.simulate(endSimTime);
    }
}
