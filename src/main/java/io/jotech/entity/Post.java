package io.jotech.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="POSTS")
@ToString(exclude = {"user"})
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"comments"})
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column(unique = true)
    @NotEmpty
    @NotNull
    private String title;
    @Lob
    @NotEmpty
    @NotNull
    private String body;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("userId")
    @Setter(AccessLevel.NONE)
    private User user;

//    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<Comment> comments;

    @JsonProperty("userId")
    public void setUserById(Long userId) {
        user = User.fromId(userId);
    }

    public static Post fromId(Long postId) {
        Post post = new Post();
        post.id = postId;
        return post;
    }


}
