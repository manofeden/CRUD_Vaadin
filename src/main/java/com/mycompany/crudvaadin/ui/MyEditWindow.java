package com.mycompany.crudvaadin.ui;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import com.mycompany.crudvaadin.model.Client;
import com.mycompany.crudvaadin.repository.ClientRepository;
import com.mycompany.crudvaadin.service.ClientService;
import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class MyEditWindow extends Window {

	private static final long serialVersionUID = 1L;

	private final ClientRepository repository;
	private final ClientService service;
	private Client client;

	/* Fields to edit properties in Client entity */
	TextField snils = new TextField("Снилс");
	TextField fio = new TextField("ФИО");
	DateField dat = new DateField("Дата Рождения");

	/* Action buttons */
	Button save = new Button("Сохранить", VaadinIcons.CHECK);
	Button cancel = new Button("Отменить");
	Button delete = new Button("Удалить", VaadinIcons.TRASH);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Client> binder = new Binder<>(Client.class);

	@Autowired
	public MyEditWindow(ClientRepository repository, ClientService service) {
		super("Информация о клиенте");
		center();

		this.repository = repository;
		this.service = service;

		VerticalLayout content = new VerticalLayout();
		setContent(content);

		content.addComponents(snils, fio, dat, actions);
		binder = new Binder<>(Client.class);
		binder.forField(snils).bind(Client::getSnils, Client::setSnils);
		binder.forField(fio).bind(Client::getFio, Client::setFio);
		binder.forField(dat).withConverter(new SqlDateToLocalDateConverter()).bind(Client::getBirthday, Client::setBirthday);

		dat.setDateFormat("dd.MM.yyyy");

		// Configure and style components
		content.setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		save.addClickListener(e -> sav(client));
		delete.addClickListener(e -> del(client));
		cancel.addClickListener(e -> cancel());
	}

	public final void cancel() {
		this.close();
	}

	public final void editClient(Client c) {
		System.out.println(c);
		if (c == null) {
			return;
		}

		final boolean persisted = c.getId_client() != null;
		if (persisted) {
			client = repository.findById(c.getId_client()).get();
		} else {
			client = c;
		}
		cancel.setVisible(persisted);
		binder.setBean(client);

		if (!this.isAttached()) {
			UI.getCurrent().addWindow(this);
		}

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in snils field automatically
		snils.selectAll();
	}

	public final void sav(Client c) {
		if (c == null) {
			this.close();
			return;
		}

		if (c.getId_client() == null) {
			int rows = service.addClient(c);
			System.out.println(rows + " row(s) inserted.");
		} else {
			int rows = service.updClient(c);
			System.out.println(rows + " row(s) updated.");
		}

		this.close();
	}

	public final void del(Client c) {
		int rows = service.deleteClient(c);
		System.out.println(rows + " row(s) deleted.");
		this.close();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

	private class SqlDateToLocalDateConverter implements Converter<LocalDate, java.sql.Date> {
		@Override
		public Result<java.sql.Date> convertToModel(LocalDate value, ValueContext context) {
			if (value == null) {
				return Result.ok(null);
			}
			return Result.ok(java.sql.Date.valueOf(value));
		}

		@Override
		public LocalDate convertToPresentation(java.sql.Date value, ValueContext context) {
			if (value == null)
				return null;
			return value.toLocalDate();
		}
	}

}
