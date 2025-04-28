package com.echo.crypto.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "strategies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Strategy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String code;

    @Column(nullable = false)
    private String language; // "custom", "pine", "js", "python", ...

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "strategy_profile", // имя таблицы-связки
            joinColumns = @JoinColumn(name = "strategy_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private List<Profile> profile;
}
