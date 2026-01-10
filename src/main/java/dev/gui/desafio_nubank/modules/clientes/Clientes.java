package dev.gui.desafio_nubank.modules.clientes;


import dev.gui.desafio_nubank.core.BaseEntity;
import dev.gui.desafio_nubank.modules.contatos.Contatos;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_clientes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Clientes extends BaseEntity {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "clientes", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contatos> contatos = new ArrayList<Contatos>();

}
