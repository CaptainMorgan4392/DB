package ru.nsu.kosarev.db.building.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cinema {

    @Id
    private Integer id;

    private Integer diagonal;

    private String address;

}
