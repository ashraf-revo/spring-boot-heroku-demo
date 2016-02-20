package org.revo.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.validator.constraints.NotEmpty

import javax.persistence.*
import javax.validation.constraints.Min

/**
 * Created by ashraf on 12/3/2015.
 */
@Entity
class Term {
    @Id
    @GeneratedValue
    Long id
    @Column(length = 40, unique = true)
    @NotEmpty
    String name
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "term")
    @JsonIgnore
    Set<PT> pt = new HashSet<>()
    boolean enabled = true
    @Column(length = 2)
    int maxHour
    @Column(length = 2)
    int defaultHour
    @Column(length = 2)
    @Min(value = 0L)
    int minHour
    @Temporal(TemporalType.DATE)
    Date CreatedDate = new Date();
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    Admin admin
}
