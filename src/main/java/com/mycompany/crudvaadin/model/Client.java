package com.mycompany.crudvaadin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class Client {

	@Id
	@GeneratedValue
	private Integer id_client;

	private String snils;
	private String fio;
	private java.sql.Date birthday;

	public Client() {
	};

	public Client(int id_client, String snils, String fio, java.sql.Date birthday) {
		this.id_client = id_client;
		this.snils = snils;
		this.fio = fio;

		if (birthday == null) {
			this.birthday = null;
		} else {
			this.birthday = new java.sql.Date(birthday.getTime());
		}
	}

	public Integer getId_client() {
		return id_client;
	}

	public void setId_client(int id_client) {
		this.id_client = id_client;
	}

	public String getSnils() {
		return snils;
	}

	public void setSnils(String snils) {
		this.snils = snils;
	}

	public String getFio() {
		return fio;
	}

	public void setFio(String fio) {
		this.fio = fio;
	}

	public java.sql.Date getBirthday() {
		if (birthday == null)
			return null;
		return new java.sql.Date(birthday.getTime());
	}

	public void setBirthday(java.sql.Date birthday) {
		if (birthday == null) {
			this.birthday = null;
		} else {
			this.birthday = new java.sql.Date(birthday.getTime());
		}
	}

	@Override
	public String toString() {
		return "Client{" + "id_client=" + id_client + ", snils=" + snils + ", fio=" + fio + ", birthday=" + birthday + '}';
	}
}
