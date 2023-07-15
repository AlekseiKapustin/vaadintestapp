package ru.alekseikapustin.exampleapp.ui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alekseikapustin.exampleapp.model.ValueStore;
import ru.alekseikapustin.exampleapp.model.ValueStoreRepository;


@Route
public class MainView extends VerticalLayout implements KeyNotifier {

    private final ValueStoreRepository repository;

    private ValueStore valueStore = new ValueStore(0);
    TextField textField =  new TextField();
    Button button = new Button("Increment");

    Binder<ValueStore> binder = new Binder<>(ValueStore.class);

    @Autowired
    public MainView(ValueStoreRepository repository) {
        this.repository = repository;

        add(textField, button);

        binder.forField(textField)
                .withConverter(new StringToIntegerConverter("Not a number"))
                .bind(ValueStore::getValue, ValueStore::setValue);

        binder.setBean(valueStore);
        button.addClickListener(e -> {
            if (binder.validate().isOk()) {
                this.valueStore.setValue(this.valueStore.getValue() + 1);
                save();
            }
        });
        addKeyPressListener(Key.ENTER,keyPressEvent -> {
            if (binder.validate().isOk()) {
                save();
            }
        });
        textField.addValueChangeListener(e -> {
            if (e.isFromClient()) {
                if (binder.validate().isOk()) {
                    save();
                }
            }
        });
    }

    void save() {
       // System.out.println(this.valueStore.getValue());
        repository.save(valueStore);
        binder.refreshFields();

    }
}
