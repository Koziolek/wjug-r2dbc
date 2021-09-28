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
@Table(name = "bookings")
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookid")
    private int bookId;

    @Column(name = "starttime")
    private LocalDateTime startTime;

    private int slots;

    @JoinColumn(name = "facid", referencedColumnName = "facid")
    @OneToOne
    private Facility facility;

    @JoinColumn(name = "memid", referencedColumnName = "memid")
    @OneToOne
    private Member member;
}
