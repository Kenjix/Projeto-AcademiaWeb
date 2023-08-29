package com.example.academia.model;

import com.example.academia.enuns.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @Column(length = 50)
    private String nome;

    @Column(unique = true, length = 11)
    private String cpf;

    @Column(unique = true, length = 10)
    private String matricula;

    @Column(length = 12)
    private String telefone;

    @Column(length = 11)
    private String celular;

    @Column(nullable = false, length = 50)
    private String email;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNasc;

    private float peso;

    private float altura;

    @Temporal(TemporalType.DATE)
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    private Date dataFim;

    @Column(length = 80)
    private String objetivo;

    @Column
    private boolean ativo;

    @Column(columnDefinition = "TEXT")
    private String foto;

    @Lob
    private String observacoes;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @PrePersist
    private void onCreate() {
        created = new Date();
        updated = new Date();
        ativo = true;
    }

    //atrubutos de instrutor
    @Column
    private int cargaHoraria;

    @Column(length = 20)
    private String turno;

    @Column(length = 50)
    private String especialidade;

    public User(String email, String password, UserRole role, String matricula) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.matricula = matricula;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Map<UserRole, List<SimpleGrantedAuthority>> roleAuthorities = Map.of(
                UserRole.ADMIN, List.of(
                        new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ROLE_INSTRUTOR"),
                        new SimpleGrantedAuthority("ROLE_USER")
                ),
                UserRole.INSTRUTOR, List.of(
                        new SimpleGrantedAuthority("ROLE_INSTRUTOR"),
                        new SimpleGrantedAuthority("ROLE_USER")
                ),
                UserRole.USER, List.of(
                        new SimpleGrantedAuthority("ROLE_USER")
                )
        );
        return roleAuthorities.getOrDefault(this.role, Collections.emptyList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}