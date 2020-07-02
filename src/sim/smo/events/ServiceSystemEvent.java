package sim.smo.events;

import sim.core.Manager;
import sim.core.SimEvent;
import sim.smo.ServiceSystem;

public abstract class ServiceSystemEvent extends SimEvent {
    private final ServiceSystem serviceSystem;

    public ServiceSystemEvent(Manager mgr, double runTime, int priority, ServiceSystem serviceSystem) {
        super(mgr, runTime, priority);
        this.serviceSystem = serviceSystem;
    }

    public ServiceSystem getServiceSystem(){
        return this.serviceSystem;
    }
}
