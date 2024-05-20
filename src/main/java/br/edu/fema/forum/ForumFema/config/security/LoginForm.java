package br.edu.fema.forum.ForumFema.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Objects;

public class LoginForm {

    private String email;
    private String senha;

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

    public LoginForm(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public LoginForm() {
    }

    public UsernamePasswordAuthenticationToken converter(){
        return new UsernamePasswordAuthenticationToken(email, senha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginForm loginForm = (LoginForm) o;
        return email.equals(loginForm.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
