package adapter;

public interface EventConsumer {
    public void handleEmergency(String location, String type);
}
