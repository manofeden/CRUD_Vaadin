package com.mycompany.crudvaadin.service;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.mycompany.crudvaadin.model.Client;

@Component
public class ClientService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public int addClient(Client client) {
		return jdbcTemplate.update("INSERT INTO clients(snils, fio, birthday) VALUES(?, ?, ?)", new Object[] { client.getSnils(), client.getFio(), client.getBirthday() }, new int[] { Types.VARCHAR, Types.VARCHAR, Types.DATE });
	}

	public int updClient(Client client) {
		return jdbcTemplate.update("UPDATE clients SET snils = ?, fio = ?, birthday = ? WHERE id_client=?", new Object[] { client.getSnils(), client.getFio(), client.getBirthday(), client.getId_client() },
				new int[] { Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.INTEGER });
	}

	public int deleteClient(Client client) {
		Object[] params = { client.getId_client() };
		int[] types = { Types.INTEGER };
		return jdbcTemplate.update("DELETE FROM clients WHERE id_client = ?", params, types);

	}

}
