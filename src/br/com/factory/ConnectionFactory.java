package br.com.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

		public Connection getConexao(){
			try{
				System.out.println("Conectando com o banco de dados...");
				return DriverManager.getConnection("jdbc:mysql://localhost/android_test01","root","");
			}catch (SQLException e) {
				throw new RuntimeException("Erro ao Conectar ao Banco de dados", e);
			}
		}
}


