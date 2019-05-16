package eventbroker;

public class EventPublisher {
	protected EventBroker broker;
	public EventPublisher(EventBroker broker){
		this.broker = broker;
	}
    public void publishEvent(Event e) {
        broker.addEvent(this, e);
    }
}
