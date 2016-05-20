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
			String sql = "select distinct fermata.nome, linea.nome, coordX, coordY from connessione, fermata, linea where id_stazP=id_fermata and linea.id_linea=connessione.id_linea";
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(sql);
			while(res.next()){
				Fermata f = new Fermata(res.getString("fermata.nome"), res.getDouble("coordX"), res.getDouble("coordY"));
				f.setLinea(res.getString("linea.nome"));
				fermate.add(f);
			}
			res.close();
			conn.close();
		} catch(SQLException e){
			e.printStackTrace();
			System.out.println("Errore 2");
		}		
		return fermate;
	}
	
	public void getVicini(Fermata fermata, List<Fermata> fermate){
		Connection conn = DBConnection.getConnection();
		String sql = "select velocita, fermata.nome, linea.nome from connessione, linea, fermata, (fermata AS F) where connessione.id_linea=linea.id_linea and F.id_fermata=connessione.id_stazP and fermata.id_fermata=connessione.id_stazA and F.nome=?";
		PreparedStatement st;
		try{	
			st = conn.prepareStatement(sql);
			st.setString(1, fermata.getNome());
			ResultSet res = st.executeQuery();
			while(res.next()){
				for(Fermata f:fermate){
					if(f.getNome().compareTo(res.getString("fermata.nome"))==0 && f.getLinea().compareTo(res.getString("linea.nome"))==0){
						fermata.addArco(f, res.getDouble("velocita"));
						break;
					}
				}
			}
			conn.close();
		} catch(SQLException e){
			System.out.println("Errore 1");
		}		
	}
	
	public void addSeStesso(Fermata fermata, List<Fermata> fermate){
		Connection conn = DBConnection.getConnection();
		String sql = "select distinct fermata.nome, linea.nome, linea.intervallo from connessione, fermata, linea where id_stazP=id_fermata and linea.id_linea=connessione.id_linea and fermata.nome=?";
		PreparedStatement st;
		try{	
			st = conn.prepareStatement(sql);
			st.setString(1, fermata.getNome());
			ResultSet res = st.executeQuery();
			while(res.next()){
				for(Fermata f:fermate){
					if(f.getNome().compareTo(res.getString("fermata.nome"))==0 && f.getLinea().compareTo(res.getString("linea.nome"))==0 && f.getLinea().compareTo(fermata.getLinea())!=0){
						fermata.addArco(f, 0);
						break;
					}
				}
			}
			conn.close();
		} catch(SQLException e){
			System.out.println("Errore 1");
		}		
	}
	
}
