package com.mycompany.crudvaadin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mycompany.crudvaadin.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

	List<Client> findClientBySnils(String snils);

	@Query("SELECT c FROM Client c ORDER BY c.snils ASC")
	List<Client> findAllOrderBySnils();

}
