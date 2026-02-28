package dev.gui.desafio_nubank.modules.contatos.repository;

import dev.gui.desafio_nubank.modules.contatos.entity.Contatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatosRepository extends JpaRepository<Contatos, Long> {
    List<Contatos> findByClientesId(Long clienteId);
}
