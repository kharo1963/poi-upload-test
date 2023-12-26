package poiAPI.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "poi_ownership")
public class Ownership {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "person_id")
    private Integer personId;

    @Column(name = "house_id")
    private Integer houseId;

    @Column(name = "percent")
    private Integer percent;

}
