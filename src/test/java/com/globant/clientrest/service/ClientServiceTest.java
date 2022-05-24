package com.globant.clientrest.service;

import com.globant.clientrest.entity.Client;
import com.globant.clientrest.repository.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest()
@TestPropertySource(locations = "classpath:db-test.properties")
class ClientServiceTest {

    @MockBean
    private ClientRepository clientRepositoryMock;

    @Autowired
    private ClientService clientService;

    @Test
    void findAll() {
        final Client client1 = Client.builder().nombre("Nombre Mock 1").apellido("Apellido Mock 1").build();
        final Client client2 = Client.builder().nombre("Nombre Mock 2").apellido("Apellido Mock 2").build();
        final List<Client> data = Arrays.asList(client1, client2);
        Mockito.when(clientRepositoryMock.findAll()).thenReturn(data);

        final Collection<Client> dataClient =  clientService.findAll();
        assertThat(dataClient.size()).isEqualTo(data.size());
        verify(clientRepositoryMock, times(1)).findAll();
    }

    @Test
    void findById() {
        final Client client = Client.builder().id(1L).nombre("Nombre Mock 1").apellido("Apellido Mock 1").build();
        Mockito.when(clientRepositoryMock.findById(anyLong())).thenReturn(Optional.of(client));
        final Client dataClient =  clientService.findById(1L);
        assertThat(dataClient.getId()).isEqualTo(client.getId());
        verify(clientRepositoryMock, times(1)).findById(anyLong());
    }

    @Test()
    void findById_NotFound() {
        Mockito.when(clientRepositoryMock.findById(anyLong())).thenThrow(EntityNotFoundException.class);
        Assertions.assertThrows(EntityNotFoundException.class, () -> clientService.findById(1L));
        verify(clientRepositoryMock, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        final Client client = Client.builder().id(1L).nombre("Nombre Mock 1").apellido("Apellido Mock 1").build();
        Mockito.when(clientRepositoryMock.save(any(Client.class))).thenReturn(client);
        final Client dataClient =  clientService.save(client);
        assertThat(dataClient.getId()).isEqualTo(client.getId());
        verify(clientRepositoryMock, times(1)).save(any(Client.class));
    }

    @Test
    void update() {
        final Client client = Client.builder().id(1L).nombre("Nombre Mock 1").apellido("Apellido Mock 1").build();
        Mockito.when(clientRepositoryMock.findById(anyLong())).thenReturn(Optional.of(client));
        Mockito.when(clientRepositoryMock.save(any(Client.class))).thenReturn(client);
        final Client dataClient =  clientService.update(client);
        assertThat(dataClient.getId()).isEqualTo(client.getId());
        verify(clientRepositoryMock, times(1)).findById(anyLong());
        verify(clientRepositoryMock, times(1)).save(any(Client.class));
    }

    @Test
    void delete() {
        final Client client = Client.builder().id(1L).nombre("Nombre Mock 1").apellido("Apellido Mock 1").build();
        Mockito.when(clientRepositoryMock.findById(anyLong())).thenReturn(Optional.of(client));
        clientService.delete(1L);
        verify(clientRepositoryMock, times(1)).findById(anyLong());
        verify(clientRepositoryMock, times(1)).delete(any(Client.class));
    }
}