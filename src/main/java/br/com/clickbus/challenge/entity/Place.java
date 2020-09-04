package br.com.clickbus.challenge.entity;


import br.com.clickbus.challenge.dto.PlaceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String slug;

    @NotNull
    private String city;

    @NotNull
    private String state;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Place(String name, String slug, String city, String state) {
        this.name = name;
        this.slug = slug;
        this.city = city;
        this.state = state;
        this.createdAt = LocalDateTime.now();
    }

    public static Place of(String name, String slug, String city, String state) {
        return new Place(name, slug, city, state);
    }

    public PlaceDTO convertToDTO() {
        return PlaceDTO.of(this.name, this.slug, this.city, this.state);
    }
}
