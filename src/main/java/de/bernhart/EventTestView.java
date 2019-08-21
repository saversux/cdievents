package de.bernhart;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import de.bernhart.events.TestEvent;
import de.bernhart.events.TestEvent2;

import javax.inject.Inject;

@CDIView("test")
public class EventTestView extends VerticalLayout implements View {

    @Inject
    javax.enterprise.event.Event<TestEvent> testEventEvent;
    @Inject
    javax.enterprise.event.Event<TestEvent2> testEventEvent2;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.addComponent(new Label("cdi works !!1"));

        HorizontalLayout vert = new HorizontalLayout();
        Button eventButton = new Button("Fire!");
        Button eventButton2 = new Button("Fire error!");

        eventButton.addClickListener(e -> {
            testEventEvent.fire(new TestEvent("holy fuuuuck."));
        });
        eventButton2.addClickListener(e -> {
            testEventEvent2.fire(new TestEvent2("holy fuuuuck."));
        });

        vert.addComponents(eventButton, eventButton2);
        this.addComponent(vert);
    }
}
