package ru.az.secu.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@EqualsAndHashCode
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="created")
    @CreatedDate
    private LocalDateTime created;
    @Column(name="updated")
    @LastModifiedDate
    private LocalDateTime updated;

    @PrePersist
    private void created(){
        this.created=LocalDateTime.now();
    }

    @PreUpdate
    private void updated(){
        this.updated=LocalDateTime.now();
    }

}
