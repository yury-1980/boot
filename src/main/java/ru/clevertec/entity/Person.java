package ru.clevertec.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.clevertec.entity.type.Sex;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "person")
@EqualsAndHashCode(of = {"uuid", "passportNumber"})
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private Sex sex;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private Long passportNumber;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(mappedBy = "ownersList", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<House> housesOwner = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "house_id", referencedColumnName = "id")
    private House houseResident = new House();

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "person",fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<HouseHistory> personHistories = new ArrayList<>();
}
