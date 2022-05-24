package com.globant.clientrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globant.clientrest.entity.Client;
import com.globant.clientrest.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestPropertySource(locations = "classpath:db-test.properties")
@WebMvcTest(ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientServiceMock;

    @Autowired
    private ObjectMapper objectmapper;

    @Test
    void getAllClientsOk() throws Exception {
        final Client client1 = Client.builder().id(1L).nombre("Nombre Mock 1").apellido("Apellido Mock 1").build();
        final Client client2 = Client.builder().id(2L).nombre("Nombre Mock 2").apellido("Apellido Mock 2").build();
        final List<Client> data = Arrays.asList(client1, client2);
        Mockito.when(clientServiceMock.findAll()).thenReturn(data);
        mockMvc.perform(get("/clients")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(data.size())))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Nombre Mock 1")))
                .andExpect(jsonPath("$[0].apellido", is("Apellido Mock 1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("Nombre Mock 2")))
                .andExpect(jsonPath("$[1].apellido", is("Apellido Mock 2")));
        verify(clientServiceMock, times(1)).findAll();
    }

    @Test
    void getClientByIdOk() throws Exception {
        final Client client1 = Client.builder().id(1L).nombre("Nombre Mock 1").apellido("Apellido Mock 1").build();
        Mockito.when(clientServiceMock.findById(anyLong())).thenReturn(client1);
        mockMvc.perform(get("/clients/{id}", 1L)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Nombre Mock 1")))
                .andExpect(jsonPath("$.apellido", is("Apellido Mock 1")));
        verify(clientServiceMock, times(1)).findById(anyLong());
    }

    @Test
    void getClientByIdNotFound() throws Exception {
        Mockito.when(clientServiceMock.findById(anyLong())).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(get("/clients/{id}", 1L)).andDo(print())
                .andExpect(status().isNotFound());
        verify(clientServiceMock, times(1)).findById(anyLong());
    }

    @Test
    void createClientOk() throws Exception {
        final Client client1 = Client.builder()
                .nombre("Nombre Mock 1")
                .apellido("Apellido Mock 1")
                .razonSocial("NSD Colombia SAS")
                .identificador("123")
                .correoElectronico("correo@valido.com")
                .direccion("Calle falta # 1 - 23")
                .telefono("123456")
                .build();
        Mockito.when(clientServiceMock.save(any(Client.class))).thenReturn(client1);
        mockMvc.perform(post("/clients").contentType(APPLICATION_JSON).content(objectmapper.writeValueAsString(client1)))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(clientServiceMock, times(1)).save(any(Client.class));
    }

    @Test
    void createClientValidationsFail() throws Exception {
        final Client client1 = Client.builder()
                .nombre("Nombre Mock 1")
                .apellido("Apellido Mock 1")
                .razonSocial("NSD Colombia SAS")
                .identificador("123")
                .correoElectronico("correoNovalido.com")
                .direccion("Calle falta # 1 - 23")
                .telefono("123456")
                .build();
        Mockito.when(clientServiceMock.save(any(Client.class))).thenReturn(client1);
        mockMvc.perform(post("/clients").contentType(APPLICATION_JSON).content(objectmapper.writeValueAsString(client1)))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verify(clientServiceMock, times(0)).save(any(Client.class));
    }

    @Test
    void updateClientOk() throws Exception {
        final Client client1 = Client.builder()
                .nombre("Nombre Mock 1")
                .apellido("Apellido Mock 1")
                .razonSocial("NSD Colombia SAS")
                .identificador("123")
                .correoElectronico("correo@valido.com")
                .direccion("Calle falta # 1 - 23")
                .telefono("123456")
                .build();
        Mockito.when(clientServiceMock.update(any(Client.class))).thenReturn(client1);
        mockMvc.perform(put("/clients/{id}", 1L).contentType(APPLICATION_JSON).content(objectmapper.writeValueAsString(client1)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(clientServiceMock, times(1)).update(any(Client.class));
    }

    @Test
    void updateClientNotFound() throws Exception {
        final Client client1 = Client.builder()
                .nombre("Nombre Mock 1")
                .apellido("Apellido Mock 1")
                .razonSocial("NSD Colombia SAS")
                .identificador("123")
                .correoElectronico("correo@valido.com")
                .direccion("Calle falta # 1 - 23")
                .telefono("123456")
                .build();
        Mockito.when(clientServiceMock.update(any(Client.class))).thenThrow(EntityNotFoundException.class);
        mockMvc.perform(put("/clients/{id}", 1L).contentType(APPLICATION_JSON).content(objectmapper.writeValueAsString(client1)))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(clientServiceMock, times(1)).update(any(Client.class));
    }

    @Test
    void deleteClientOk() throws Exception {
        mockMvc.perform(delete("/clients/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());
        verify(clientServiceMock, times(1)).delete(anyLong());
    }

    @Test
    void deleteClientNotFound() throws Exception {
        doThrow(EntityNotFoundException.class).when(clientServiceMock).delete(anyLong());
        mockMvc.perform(delete("/clients/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(clientServiceMock, times(1)).delete(anyLong());
    }

    @Test
    void internalServerError() throws Exception {
        doThrow(RuntimeException.class).when(clientServiceMock).delete(anyLong());
        mockMvc.perform(delete("/clients/{id}", 1L))
                .andDo(print())
                .andExpect(status().isInternalServerError());
        verify(clientServiceMock, times(1)).delete(anyLong());
    }
}