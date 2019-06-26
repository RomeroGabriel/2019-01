package br.edu.utfpr.servico;

import br.edu.utfpr.dto.ClienteDTO;
import br.edu.utfpr.dto.PaisDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ServicoCliente {

    private List<ClienteDTO> clientes;

    public ServicoCliente() {

        PaisDTO BR  = PaisDTO.builder().id(1).nome("Brasil").sigla("BR").codigoTelefone(55).build();
        PaisDTO EUA = PaisDTO.builder().id(2).nome("Estados Unidos da América").sigla("EUA").codigoTelefone(33).build();
        PaisDTO RU  = PaisDTO.builder().id(3).nome("Reino Unido").sigla("RU").codigoTelefone(44).build();

        clientes = Stream.of(
                ClienteDTO.builder().id(1).nome("Gabriel Romero").idade(21).limiteCredito(12.6).telefone("99813-5181").pais(BR).build(),
                ClienteDTO.builder().id(2).nome("Ralf").idade(37).limiteCredito(2500.00).telefone("99813-5181").pais(BR).build(),
                ClienteDTO.builder().id(3).nome("Mo Salah").idade(27).limiteCredito(44000.0).telefone("98754-2020").pais(RU).build()
        ).collect(Collectors.toList());
    }


    @GetMapping("/servico/cliente")
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clientes);
    }

    @DeleteMapping("/servico/cliente/{id}")
    public ResponseEntity excluir (@PathVariable int id) {

        if (clientes.removeIf(c -> c.getId() == id))
            return ResponseEntity.noContent().build();

        else
            return ResponseEntity.notFound().build();
    }
}
