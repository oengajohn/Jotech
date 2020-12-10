package io.jotech.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="USERS")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"posts"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    @NotEmpty
    @NotNull
    private String name;
    @Column(unique = true)
    @NotEmpty
    @NotNull
    private String username;
    @Column(unique = true)
    @NotEmpty
    @NotNull
    @Email
    private String email;
    @Embedded
    private Address address;
    @Embedded
    private Company company;
    @Column(unique = true)
    @NotEmpty
    @NotNull
    private String phone;
    @Column
    private String website;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Post> posts;

    public static User fromId(Long userId) {
        User user = new User();
        user.id = userId;
        return user;
    }
}
