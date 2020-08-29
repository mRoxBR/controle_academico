package br.ufop.ControleAcademico.enums;

public enum Situacao {
    EM_ANDAMENTO("Em andamento"), ENCERRADA("Encerrada");

    private String descricao;

    Situacao(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return this.descricao;
    }
}
