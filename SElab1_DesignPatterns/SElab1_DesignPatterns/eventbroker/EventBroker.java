package eventbroker;

import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

final public class EventBroker {

    protected Set<EventListener> listeners = new HashSet<EventListener>();

    public void addEventListener(EventListener s) {
        listeners.add(s);
    }

    public void removeEventListener(EventListener s) {
        listeners.remove(s);
    }

    void addEvent(EventPublisher source, Event e) {
        process(source, e);
    }

    private void process(EventPublisher source, Event e) {
        for (EventListener l : listeners) {
            if (l != source) {
                l.handleEvent(e); // prevent loops !
            }
        }
    }
}


