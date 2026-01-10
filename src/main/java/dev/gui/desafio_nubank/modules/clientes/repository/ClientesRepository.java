package dev.gui.desafio_nubank.modules.clientes.repository;

import dev.gui.desafio_nubank.modules.clientes.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientesRepository extends JpaRepository<Clientes, Long> {
}
