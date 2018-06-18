package com.mycompany.crudvaadin.ui;

import org.springframework.util.StringUtils;

import com.mycompany.crudvaadin.model.Client;
import com.mycompany.crudvaadin.repository.ClientRepository;
import com.mycompany.crudvaadin.service.ClientService;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class VaadinUI extends UI {

	private static final long serialVersionUID = 1L;

	private final ClientRepository repo;
	private final ClientService service;
	private MyEditWindow myEditWindow;
	final TextField filter;
	private final Button addNewBtn;

	final Grid<Client> grid;

	public VaadinUI(ClientRepository repo, ClientService service) {
		this.repo = repo;
		this.service = service;
		this.grid = new Grid<>(Client.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("Добавить клиента");
	}

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(actions, grid);
		setContent(mainLayout);

		// grid.setHeight(800, Unit.PIXELS);
		grid.setWidth(800, Unit.PIXELS);
		grid.setColumns("id_client", "snils", "fio", "birthday");
		grid.getColumn("id_client").setCaption("ID");
		grid.getColumn("snils").setCaption("СНИЛС");
		grid.getColumn("fio").setCaption("ФИО");
		grid.getColumn("birthday").setCaption("Дата Рождения");

		filter.setPlaceholder("Поиск по СНИЛС");

		// Replace listing with filtered content when user changes filter
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listClients(e.getValue()));

		myEditWindow = new MyEditWindow(repo, service);

		grid.asSingleSelect().addValueChangeListener(e -> {
			myEditWindow.editClient(e.getValue());
		});

		addNewBtn.addClickListener(e -> myEditWindow.editClient(new Client()));

		// Listen changes made by the editor, refresh data from backend
		myEditWindow.setChangeHandler(() -> {
			listClients(filter.getValue());
		});

		// Initialize listing
		listClients(null);
	}

	private void listClients(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(repo.findAllOrderBySnils());
		} else {
			grid.setItems(repo.findClientBySnils(filterText));
		}
	}

}
