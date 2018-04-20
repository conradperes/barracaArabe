package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

import barracaArabe.BarracaArabe;
import connection.ConnectionFactory;

public class ArabeDAO {
	
	public int insereProducao(BarracaArabe producao) throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		Connection con = connectionFactory.getConnection();

        // cria um preparedStatement
        String sql = "insert into arabe2" +
                " (comida, tam_comida , qtd_comida , bebida , sabor, tam_bebida, qtd_bebida, total, cpf, data)" +
                " values (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);

        // preenche os valores
        stmt.setString(1, producao.getComida());
        stmt.setString(2, producao.getTam_comida());
        stmt.setInt(3, producao.getQtd_comida());
        stmt.setString(4, producao.getBebida());
        stmt.setString(5, producao.getSabor());
        stmt.setString(6, producao.getTam_bebida());
        stmt.setInt(7, producao.getQtd_bebida());
        stmt.setDouble(8, producao.getTotal());
        stmt.setString(9, producao.getCpf());
        stmt.setDate(10, new java.sql.Date(
                Calendar.getInstance().getTimeInMillis()));
        // executa
        int qtdeRegistros = stmt.executeUpdate();
        stmt.close();
        
        connectionFactory.closeConnection();

        System.out.println("Gravado!");
        
		return qtdeRegistros;
	}

}
