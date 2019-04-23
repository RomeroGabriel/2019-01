package br.edu.utfpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class TemplateDAO {

	final boolean incluir(Object obj) {
		String conexao = getConexao();
		try ( Connection conn = DriverManager.getConnection(conexao)){
			String sql = getInsertSql();
			//PreparedStatement statement = conn.prepareStatement(sql);
			PreparedStatement statement = getIncluirPreparedStatement(sql, conn);
			int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            }
		} catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}
	
	abstract String getConexao();
	abstract String getInsertSql();
	abstract PreparedStatement getIncluirPreparedStatement(String sql, Connection conn) throws SQLException;
}
