package org.revo.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.*
import javax.validation.constraints.Min

/**
 * Created by ashraf on 12/3/2015.
 */
@Entity
class Subject {
    @Id
    @GeneratedValue
    Long id
    @Column(length = 20, unique = true)
    @NotEmpty
    String name
    @ManyToMany
    Set<Subject> required = new HashSet<>()
    @Column(length = 2)
    @Min(value = 0L)
    int hour = 3
    @Column(length = 3)
    int maxGrade = 100
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    Admin admin
    @Transient
    boolean selected = false
}
