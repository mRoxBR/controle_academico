package br.ufop.ControleAcademico.models.bean;

import java.io.Serializable;
import java.util.Objects;

public class Disciplina implements Serializable {
    private String identificador;
    private String nome;
    private int semestre;
    private int faltas;
    private int limite_faltas;
    private double meta;
    private String situacao;

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }

    public int getLimiteFaltas() {
        return limite_faltas;
    }

    public void setLimiteFaltas(int limite_faltas) {
        this.limite_faltas = limite_faltas;
    }

    public double getMeta() {
        return meta;
    }

    public void setMeta(double meta) {
        this.meta = meta;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "identificador='" + identificador + '\'' +
                ", nome='" + nome + '\'' +
                ", semestre=" + semestre +
                ", faltas=" + faltas +
                ", limite_faltas=" + limite_faltas +
                ", meta=" + meta +
                ", situacao='" + situacao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o;
        return semestre == that.semestre &&
                faltas == that.faltas &&
                limite_faltas == that.limite_faltas &&
                Double.compare(that.meta, meta) == 0 &&
                identificador.equals(that.identificador) &&
                nome.equals(that.nome) &&
                situacao.equals(that.situacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador, nome, semestre, faltas, limite_faltas, meta, situacao);
    }
}
