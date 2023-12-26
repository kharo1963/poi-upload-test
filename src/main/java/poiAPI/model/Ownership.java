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
    @Column(name = "percent1")
    private Integer percent1;
    @Column(name = "percent2")
    private Integer percent2;
    @Column(name = "percent3")
    private Integer percent3;
    @Column(name = "percent4")
    private Integer percent4;
    @Column(name = "percent5")
    private Integer percent5;
    @Column(name = "percent6")
    private Integer percent6;
    @Column(name = "percent7")
    private Integer percent7;
    @Column(name = "percent8")
    private Integer percent8;
    @Column(name = "percent9")
    private Integer percent9;

    @Column(name = "name1")
    private String name1;
    @Column(name = "name2")
    private String name2;
    @Column(name = "name3")
    private String name3;
    @Column(name = "name4")
    private String name4;
    @Column(name = "name5")
    private String name5;
    @Column(name = "name6")
    private String name6;
    @Column(name = "name7")
    private String name7;
    @Column(name = "name8")
    private String name8;
    @Column(name = "name9")
    private String name9;

    @Column(name = "nick1")
    private String nick1;
    @Column(name = "nick2")
    private String nick2;
    @Column(name = "nick3")
    private String nick3;
    @Column(name = "nick4")
    private String nick4;
    @Column(name = "nick5")
    private String nick5;

}
