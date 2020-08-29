package br.ufop.ControleAcademico.models.bean;

import java.io.Serializable;
import java.util.Objects;

public class Tarefa implements Serializable {
    private int id;
    private Disciplina disciplina;
    private String descricao;
    private double valor;
    private double nota;
    private String data_entrega;
    private String tipo;
    private double prioridade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getDataEntrega() {
        return data_entrega;
    }

    public void setDataEntrega(String data_entrega) {
        this.data_entrega = data_entrega;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(double prioridade) {
        this.prioridade = prioridade;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", disciplina=" + disciplina +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", nota=" + nota +
                ", data_entrega='" + data_entrega + '\'' +
                ", tipo='" + tipo + '\'' +
                ", prioridade=" + prioridade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return id == tarefa.id &&
                Double.compare(tarefa.valor, valor) == 0 &&
                Double.compare(tarefa.nota, nota) == 0 &&
                Double.compare(tarefa.prioridade, prioridade) == 0 &&
                Objects.equals(disciplina, tarefa.disciplina) &&
                Objects.equals(descricao, tarefa.descricao) &&
                Objects.equals(data_entrega, tarefa.data_entrega) &&
                Objects.equals(tipo, tarefa.tipo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, disciplina, descricao, valor, nota, data_entrega, tipo, prioridade);
    }
}
