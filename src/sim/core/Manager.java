package sim.core;

public class Manager {
	private double startSimTime = 0.0;
	private double stopSimTime = Double.MAX_VALUE;
	private double currentSimTime = startSimTime;
	private static Manager simMgr; // Singleton
	private boolean simulationStarted = false;
	private final SimCalendar simCalendar = new SimCalendar();
	
	public static Manager getInstance(double startSimTime) {
		if (simMgr == null) {
			simMgr = new Manager(startSimTime);
		}
		return simMgr;
	}
	
	private Manager(double startSimTime) {
		if (startSimTime>0.0) {
			this.startSimTime = startSimTime;
		}
	}

	/* Zaplanowanie nowego wydarzenia */
	public void registerEvent(SimEvent event) {
		simCalendar.addEvent(event);
	}

	/* Usunięcie zaplanowanego wydarzenia */
	public void unregisterEvent(SimEvent event) {
		simCalendar.remove(event);
	}

	/* Zwrócenie obecnego czasu symulacji */
	public final double simTime() {
		return currentSimTime;
	}

	/* Zatrzymanie symulacji */
	public final void stopSimulation() {
		simulationStarted = false;
	}

	/* Rozpoczęcie symulacji */
	public final void startSimulation() throws Exception {
		simulationStarted = true;
		// WYKONANE NA POPRZEDNICH ZAJĘCIACH
		System.out.printf("%.2f\t Symulacja rozpoczęta.\n",simTime());
		while(this.simulationStarted && simCalendar.isAnyEvent() && this.currentSimTime < this.stopSimTime){
			runStebByStep();
			nextEvent();
		}
		stopSimulation();
		System.out.printf("%.2f\t Symulacja zakończona.\n",simTime());
	}

	/* Ustawienie czasu końca symulacji */
	public void setEndSimTime(double endSimTime) {
		this.stopSimTime = endSimTime;
	}
	
	private void runStebByStep() throws Exception {
		simCalendar.pollEvent().stateChange();
	}

	private void nextEvent() {
		this.currentSimTime = simCalendar.peekEvent().getRunTime();
	}
}
