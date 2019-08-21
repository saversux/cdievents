package de.bernhart;

import com.vaadin.ui.UI;
import de.bernhart.events.TestEvent;
import de.bernhart.events.TestEvent2;
import de.bernhart.qualifier.FromEventHandler;
import de.bernhart.qualifier.ToEventHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApplicationScoped
public class EventHandler implements Serializable {
    @Inject
    @FromEventHandler
    javax.enterprise.event.Event<TestEvent> testEventEvent;

    @Inject
    @FromEventHandler
    javax.enterprise.event.Event<TestEvent2> testEventEvent2;

    private Collection<UI> uis = new HashSet<UI>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public synchronized void register(UI listener) {
        uis.add(listener);
    }

    public synchronized void unregister(UI listener) {
        uis.remove(listener);
    }

    private synchronized void observe(@Observes @ToEventHandler TestEvent event) {
        for (UI ui : uis) {
            executorService.execute(() -> ui.access(() -> testEventEvent.fire(event)));
        }
    }

    private synchronized void observe(@Observes @ToEventHandler TestEvent2 event) {
        for (UI ui : uis) {
            executorService.execute(() -> ui.access(() -> testEventEvent2.fire(event)));
        }
    }
}
