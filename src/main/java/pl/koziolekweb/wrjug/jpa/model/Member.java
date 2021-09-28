package pl.koziolekweb.wrjug.jpa.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "members")
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memid")
    private int memId;

    @Column(name = "surname")
    private String surname;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "address")
    private String address;
    @Column(name = "zipcode")
    private int zipCode;
    @Column(length = 20, name = "telephone")
    private String telephone;

    @OneToOne
    @JoinColumn(name = "recommendedby", referencedColumnName = "memid")
    private Member recommendedBy;

    @Column(name = "joindate")
    private LocalDateTime joinDate;
}
