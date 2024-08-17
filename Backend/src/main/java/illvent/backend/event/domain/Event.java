package illvent.backend.event.domain;

import illvent.backend.wish.domain.Wish;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "Event")
public class Event {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no")
    private Long no;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false, length = 2047)
    private String description;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private Long price;

    @OneToMany(mappedBy = "event")
    private List<Wish> wishes = new ArrayList<>();
}
