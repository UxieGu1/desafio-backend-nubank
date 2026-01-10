package dev.gui.desafio_nubank.entity;

import dev.gui.desafio_nubank.core.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_contatos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contatos extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    Clientes clientes = new Clientes();
}
