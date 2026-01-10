package dev.gui.desafio_nubank.modules.contatos.repository;

import dev.gui.desafio_nubank.modules.contatos.Contatos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatosRepository extends JpaRepository<Contatos, Long> {
}
