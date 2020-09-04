package br.com.clickbus.challenge.contoller;


import br.com.clickbus.challenge.controller.PlaceController;
import br.com.clickbus.challenge.dto.PlaceDTO;
import br.com.clickbus.challenge.entity.Place;
import br.com.clickbus.challenge.service.PlaceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.atLeastOnce;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
//@WebMvcTest(PlaceController.class)
class PlaceControllerTest {

    //@Autowired
    private MockMvc mockMvc;

    @Mock
    private PlaceService service;

    @InjectMocks
    private PlaceController placeController;

    private ObjectMapper objectMapper;

    private Place place;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        place = Place.of("Butanta", "bt", "Sao Paulo", "SP");
        mockMvc = MockMvcBuilders
                .standaloneSetup(placeController)
                //.addFilters(new CORSFilter())
                .build();
    }

    @Test
     void whenFindAllPlacesThenReturnASimpleItem() throws Exception {

        when(service.findAll()).thenReturn(Collections.singletonList(place));

        mockMvc.perform(get("/places")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Butanta")))
                .andExpect(jsonPath("$[0].slug", is("bt")))
                .andExpect(jsonPath("$[0].city", is("Sao Paulo")))
                .andExpect(jsonPath("$[0].state", is("SP")))
                .andReturn().getResponse();


        verify(service, atLeastOnce()).findAll();

    }

    @Test
     void whenFindByIdThenReturnOk() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(place));

        mockMvc.perform(get("/places/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is("Butanta")))
                .andExpect(jsonPath("$.slug", is("bt")))
                .andExpect(jsonPath("$.city", is("Sao Paulo")))
                .andExpect(jsonPath("$.state", is("SP")))
                .andReturn().getResponse();

        verify(service, atLeastOnce()).findById(anyLong());
    }

    @Test
     void whenFindByIdThenReturnNotFound() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/places/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn().getResponse();

        verify(service, atLeastOnce()).findById(anyLong());
    }

    @Test
    void whenFindByNameThenReturnOk() throws Exception {
        when(service.findByName("Butanta")).thenReturn(Arrays.asList(place));

        mockMvc.perform(get("/places/?name={name}", "Butanta")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Butanta")))
                .andExpect(jsonPath("$[0].slug", is("bt")))
                .andExpect(jsonPath("$[0].city", is("Sao Paulo")))
                .andExpect(jsonPath("$[0].state", is("SP")))
                .andReturn().getResponse();


        verify(service, atLeastOnce()).findByName(anyString());
    }

    @Test
    void whenFindByNameThenReturnNotFound() throws Exception {

        when(service.findByName("Cotia")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/places/?name={name}", "Cotia")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn().getResponse();


        verify(service, atLeastOnce()).findByName(anyString());
    }

    @Test
    void whenSaveThenReturnCreated() throws Exception {
        when(service.save(any(Place.class))).thenReturn(place);

        System.out.println(objectMapper.writeValueAsString(place));

        mockMvc.perform(post("/places")
                .content(objectMapper.writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse();
    }

    @Test
    void whenSaveInvalidThenReturnBadRequest() throws Exception {
        Place place = Place.of(null, "bt", "Sao Paulo", "SP");

        mockMvc.perform(post("/places")
                .content(objectMapper.writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse();
    }

    @Test
     void whenEdiWithPlaceInvalidThenReturnBadRequest() throws Exception {
        Place place = Place.of(null, "bt", "Sao Paulo", "SP");

        mockMvc.perform(put("/places/{id}", "1")
                .content(objectMapper.writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse();
    }

    @Test
     void whenEdiWithPlaceThenReturnOk() throws Exception {
        when(service.findById(1L)).thenReturn(Optional.of(place));

        Place place = Place.of("Butanta", "bt", "Sao Paulo", "SP");
        when(service.alter(any(Place.class), any(PlaceDTO.class))).thenReturn(place);

        mockMvc.perform(put("/places/{id}", "1")
                .content(objectMapper.writeValueAsString(place))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse();
    }
}
