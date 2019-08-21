package de.bernhart;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
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
@Push
@Theme("mytheme")
@CDIUI("")
public class EventTestUI extends UI {
    @Inject
    CDIViewProvider viewProvider;

    private Label label;
    private Label status;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        label = new Label("EventTest UI");
        status = new Label();
        status.addStyleName("warning");
        Panel panel = new Panel();
        panel.setWidth("220px");
        layout.addComponents(label, status, panel);
        this.setContent(layout);

        Navigator navigator = new Navigator(this, panel);
        navigator.addProvider(viewProvider);

        // Navigate to start view
        navigator.navigateTo("test");

        pushTest();
    }

    private void pushTest() {
        new Thread(() -> {
            int i = 0;
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final int j = i;
                access(() -> label.setValue(String.format("Push test no %s", String.valueOf(j))));

                i++;
            }
        }).start();
    }

    public void test(@Observes TestEvent event) {
        System.out.println(event.getPayload());
        Notification.show("Event 1 triggered!");
        status.setValue("Last Event was: Event1");

    }

    public void test2(@Observes TestEvent2 event) {
        System.err.println(event.getPayload());
        Notification.show("Event 2 triggered!");
        status.setValue("Last Event was: Event2");
    }
}
