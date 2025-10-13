package atividade.estagio.mensageria_docker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "clientes")
public class Cliente {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length=200, unique = true)
    private String nome;

    @Column(name = "email", length=200, unique = true)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<MetodoPagamento> metodosPagamento = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Recarga> recargas = new ArrayList<>();

    public Cliente(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public Cliente(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MetodoPagamento> getMetodosPagamento() {
        return metodosPagamento;
    }

    public void setMetodosPagamento(List<MetodoPagamento> metodosPagamento) {
        this.metodosPagamento = metodosPagamento;
    }

    public List<Recarga> getRecargas() {
        return recargas;
    }

    public void setRecargas(List<Recarga> recargas) {
        this.recargas = recargas;
    }
}
