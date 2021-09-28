package pl.koziolekweb.wrjug.jpa.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "facilities")
@Getter
@Setter
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "facid")
    private int facId;

    @Column(length = 100)
    private String name;

    @Column(name = "membercost")
    private BigDecimal memberCost;
    @Column(name = "guestcost")
    private BigDecimal guestCost;
    @Column(name = "initialoutlay")
    private BigDecimal initialOutlay;
    @Column(name = "monthlymaintenance")
    private BigDecimal monthlyMaintenance;
}
