package br.com.comerce.model;

public class Usuario {
  
  //#region Atributos
  private String nome;
  
  private Integer id;

  private String email;

  private String senha;
  //#endregion
  
  //#region Getters e Setters
  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }
  //#endregion
  

}
