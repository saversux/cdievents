package de.bernhart;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import de.bernhart.events.TestEvent;
import de.bernhart.events.TestEvent2;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@CDIUI("")
public class EventTestUI extends UI {
    @Inject
    CDIViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);

        // Navigate to start view
        navigator.navigateTo("test");
    }

    public void test(@Observes TestEvent event) {
        System.out.println(event.getPayload());
        Notification.show("Event 1 triggered!");
    }

    public void test2(@Observes TestEvent2 event) {
        System.err.println(event.getPayload());
        Notification.show("Event 2 triggered!");
    }
}
