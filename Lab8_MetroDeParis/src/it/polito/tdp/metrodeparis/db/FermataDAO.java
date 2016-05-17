package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.metrodeparis.model.*;

public class FermataDAO {

	public List<Fermata> getFermate(){
		List<Fermata> fermate = new ArrayList<>();
		try{			
			Connection conn = DBConnection.getConnection();
			String sql = "select * from fermata";
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(sql);
			while(res.next()){
				fermate.add(new Fermata(res.getString("nome"), res.getDouble("coordX"), res.getDouble("coordY")));
			}
			res.close();
			conn.close();
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("Errore 2");
		}		
		return fermate;
	}
	
	public void getVicini(Fermata fermata){
		Connection conn = DBConnection.getConnection();
		String sql = "select velocita, fermata.nome, fermata.coordX, fermata.coordY from connessione, linea, fermata, (fermata AS F) where connessione.id_linea=linea.id_linea and F.id_fermata=connessione.id_stazP and fermata.id_fermata=connessione.id_stazA and F.nome=?";
		PreparedStatement st;
		try{	
			st = conn.prepareStatement(sql);
			st.setString(1, fermata.getNome());
			ResultSet res = st.executeQuery();
			while(res.next()){
				fermata.addArco(new Fermata(res.getString("nome"), res.getDouble("coordX"), res.getDouble("coordY")), res.getDouble("velocita"));
			}
			conn.close();
		} catch(SQLException e){
			System.out.println("Errore 1");
		}		
	}
	
}
