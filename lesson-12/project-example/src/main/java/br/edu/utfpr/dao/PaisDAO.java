package br.edu.utfpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import br.edu.utfpr.dto.PaisDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.java.Log;

@Log
public class PaisDAO extends TemplateDAO{

    // Responsável por criar a tabela País no banco
    public PaisDAO() {
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database;create=true")) {

            log.info("Criando tabela pais ...");
            conn.createStatement().executeUpdate(
                    "CREATE TABLE pais ("
                    + "id int NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT id_pais_pk PRIMARY KEY,"
                    + "nome varchar(255),"
                    + "sigla varchar(3),"
                    + "codigoTelefone int)");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    String getConexao() {
    	return "jdbc:derby:memory:database";
    }
    
    String getInsertSql() {
    	return "INSERT INTO pais (nome, sigla, codigoTelefone) VALUES (?, ?, ?)";
    }
    
    PreparedStatement getIncluirPreparedStatement(String sql, Object obj, PreparedStatement statement) throws SQLException {
    	PaisDTO pais = (PaisDTO) obj;
    	statement.setString(1, pais.getNome());
      	statement.setString(2, pais.getSigla());
      	statement.setInt(3, pais.getCodigoTelefone());
    	return statement;
    }
    
    String getListarTodosSql() {
    	return "SELECT * FROM pais";
    }
    
    List<Object> setObject(ResultSet obj) throws SQLException{
    	List<Object> helper = new ArrayList<>();
    	while(obj.next()) {
    		helper.add(
                    PaisDTO.builder()
                            .codigoTelefone(obj.getInt("codigoTelefone"))
                            .id(obj.getInt("id"))
                            .nome(obj.getString("nome"))
                            .sigla(obj.getString("sigla"))
                            .build()
            );
    	}
    	return helper;
    }   

    String getExcluirSql() {
    	return "DELETE FROM pais WHERE id=?";
    }

    public boolean alterar(PaisDTO pais) {
        try ( Connection conn = DriverManager.getConnection("jdbc:derby:memory:database")) {

            String sql = "UPDATE pais SET nome=?, sigla=?, codigoTelefone=? WHERE id=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, pais.getNome());
            statement.setString(2, pais.getSigla());
            statement.setInt(3, pais.getCodigoTelefone());
            statement.setInt(4, pais.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) 
                return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    //public PaisDTO listarPorId (int id) {
    //    return this.listarTodos().stream().filter(p -> p.getId() == id).findAny().orElseThrow(RuntimeException::new);
    //}
}
