package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//import br.com.factory.ConnectionFactory;
import br.com.model.Cliente;

public class ClienteDao {

	Connection conexao=null;
	PreparedStatement stmt=null;
	String sql=null;
		
	//Construct
	/*public ClienteDao(Connection conexao){
			this.conexao = new ConnectionFactory().getConexao();	
	}*/
	
	//Adiciona
	public void adiciona(Cliente u){
			//conexao = new ConnectionFactory().getConexao();
		
		this.sql = "INSERT INTO contatos(nome, endereco, telefone) values(?,?,?)";
		try {
			conexao.setAutoCommit(false);
			this.stmt = this.conexao.prepareStatement(sql);
			
			stmt.setString(1, u.getNome());
			stmt.setString(2, u.getEndereco());
			stmt.setString(3, u.getTelefone());
			
			
			stmt.execute();
			conexao.commit();
			
			System.out.println("Usuario " + u.getNome() + " incluido com sucesso!");
			
		} catch (SQLException e) {
			//conexao.rollback();
			//throw new RuntimeException(e);
			System.out.println("Problema ao fechar a conexao");
		}
		finally{
			try {
				stmt.close();
				conexao.close();
			} catch (SQLException e) {
				System.out.println("Nao foi possivel liberar os recursos"+ e.getMessage());
			}
			
		}
		
}
}
