package br.com.clickbus.challenge.service;


import br.com.clickbus.challenge.dto.PlaceDTO;
import br.com.clickbus.challenge.entity.Place;
import br.com.clickbus.challenge.repository.PlaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.NotImplementedException;

@Service
@AllArgsConstructor
public class PlaceService {

    private PlaceRepository repository;

    public List<Place> findAll() {
        throw new NotImplementedException("Metodo nao implementado");
    }

    public Optional<Place> findById(@NotNull Long id) {
        throw new NotImplementedException("Metodo nao implementado");
    }

    public Place save(@NotNull Place place) {
        throw new NotImplementedException("Metodo nao implementado");
    }

    public List<Place> findByName(@NotNull String name) {
        throw new NotImplementedException("Metodo nao implementado");
    }

    public Place alter(@NotNull Place place,@NotNull PlaceDTO placeDTO) {
        throw new NotImplementedException("Metodo nao implementado");
    }
}
