package eu.britenet.mdc;

import java.util.Map;

public class MyEvent {
    private final String id;
    private final String sender;
    private final Map<String, Object> params;

    public MyEvent(String id, String sender, Map<String, Object> params) {
        this.id = id;
        this.sender = sender;
        this.params = Map.copyOf(params);
    }

    String getId() {
        return id;
    }

    String getSender() {
        return sender;
    }

    Map<String, Object> getParams() {
        return params;
    }
}
