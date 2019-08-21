package de.bernhart.events;

public class TestEvent {
    private String payload;

    public TestEvent(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
