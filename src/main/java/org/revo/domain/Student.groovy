package org.revo.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.*
import javax.validation.constraints.Pattern

/**
 * Created by ashraf on 12/3/2015.
 */
@Entity
class Student {
    @Id
    @GeneratedValue
    Long id
    @Column(length = 40)
    @NotEmpty
    String name
    @Column(length = 40, unique = true)
    @NotEmpty
    @Pattern(regexp = "^[A-Za-z0-9_.-]{5,40}\$", message = "please change your name")
    String email
    @Column(length = 60)
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
    Set<PT> pt = new HashSet<>()
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    Admin admin
}
