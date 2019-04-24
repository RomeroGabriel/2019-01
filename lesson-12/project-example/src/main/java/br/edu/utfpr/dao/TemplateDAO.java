package br.edu.utfpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class TemplateDAO {
	
	abstract String getConexao();

	final boolean incluir(Object obj) {
		String conexao = getConexao();
		try ( Connection conn = DriverManager.getConnection(conexao) ){
			String sql = getInsertSql();
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement = getIncluirPreparedStatement(sql, obj, statement);	
			
			int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}
	
	abstract String getInsertSql();
	abstract PreparedStatement getIncluirPreparedStatement(String sql, Object obj, PreparedStatement statement) throws SQLException;
	
	final List<Object> listarTodos() {
		
		List<Object> resultado = new ArrayList<>();
		String conexao = getConexao();
		try( Connection conn = DriverManager.getConnection(conexao) ){
			
			String sql = getListarTodosSql();
			
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			
			resultado = setObject(result);
			
			//decidi não fazer desse jeito por considerar mais simples e ideal
			//mandar o result para um método que trate todo o while do que
			//chamar toda vez dentro do while a função para me retornar um objeto "setado"
			//while(result.next()) {
				//Object helper = setObject(obj);
				//resultado.add();
			//}
			
		} catch (Exception e) {
            e.printStackTrace();
        }
		
		return resultado;
	}
	
	abstract String getListarTodosSql();
	abstract List<Object> setObject(ResultSet obj) throws SQLException;
	
	final boolean excluir(int id) {
		String conexao = getConexao();
		try ( Connection conn = DriverManager.getConnection(conexao) ){
			String sql = getExcluirSql();
					
          PreparedStatement statement = conn.prepareStatement(sql);
          statement.setInt(1, id);
			
          int rowsDeleted = statement.executeUpdate();
          if (rowsDeleted > 0) {
              return true;
          }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return true;
	}
	
	abstract String getExcluirSql();
}
