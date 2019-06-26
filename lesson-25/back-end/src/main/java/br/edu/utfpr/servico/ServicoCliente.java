package br.edu.utfpr.servico;

import br.edu.utfpr.dto.ClienteDTO;
import br.edu.utfpr.dto.PaisDTO;
import br.edu.utfpr.excecao.NomeClienteMenor5CaracteresException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class ServicoCliente {

    private List<ClienteDTO> clientes;
    private List<PaisDTO> paises;

    public ServicoCliente() {

        paises = Stream.of(
                PaisDTO.builder().id(1).nome("Brasil").sigla("BR").codigoTelefone(55).build(),
                PaisDTO.builder().id(2).nome("Estados Unidos da América").sigla("EUA").codigoTelefone(33).build(),
                PaisDTO.builder().id(3).nome("Reino Unido").sigla("RU").codigoTelefone(44).build()
        ).collect((Collectors.toList()));


        clientes = Stream.of(
                ClienteDTO.builder().id(1).nome("Gabriel Romero").idade(21).limiteCredito(12.6).telefone("99813-5181").pais(paises.get(0)).build(),
                ClienteDTO.builder().id(2).nome("Ralf").idade(37).limiteCredito(2500.00).telefone("99813-5181").pais(paises.get(1)).build(),
                ClienteDTO.builder().id(3).nome("Mo Salah").idade(27).limiteCredito(44000.0).telefone("98754-2020").pais(paises.get(2)).build()
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

    @PostMapping("/servico/cliente")
    public ResponseEntity<ClienteDTO> criar (@RequestBody ClienteDTO cliente) {

        cliente.setId(clientes.size() + 1);
        clientes.add(cliente);
        return ResponseEntity.status(201).body(cliente);
    }

    @PutMapping ("/servico/cliente/{id}")
    public ResponseEntity<ClienteDTO> alterar (@PathVariable int id, @RequestBody ClienteDTO cliente) {

        Optional<ClienteDTO> clienteExistente = clientes.stream().filter(c -> c.getId() == id).findAny();
        clienteExistente.ifPresent(c -> {
            try {
                c.setNome(cliente.getNome());
            } catch (NomeClienteMenor5CaracteresException e) {
                e.printStackTrace();
            }
            c.setIdade(cliente.getIdade());
            c.setLimiteCredito(cliente.getLimiteCredito());
            c.setTelefone(cliente.getTelefone());
            c.setPais(paises.stream().filter(p -> p.getId() == cliente.getPais().getId()).findAny().get());
        });

        return ResponseEntity.of(clienteExistente);
    }

    @GetMapping ("/servico/cliente/{id}")
    public ResponseEntity<ClienteDTO> listById(@PathVariable int id) {
        
        Optional<ClienteDTO> clienteEncontrado = clientes.stream().filter(c -> c.getId() == id).findAny();
        return ResponseEntity.of(clienteEncontrado);
    }
}
