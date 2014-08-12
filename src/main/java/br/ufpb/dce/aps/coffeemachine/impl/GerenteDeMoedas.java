package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;

import br.ufpb.dce.aps.coffeemachine.CashBox;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;


public class GerenteDeMoedas {
	Coin[] coins;
	CashBox cashBox;
	private final int drink = 35;
	ComponentsFactory factory;


	public GerenteDeMoedas(){
		this.coins = new Coin[50];
	}
	
	public void devolverMoeda(){
		Coin[] c = Coin.reverse();
		for (int i = 0; i < c.length; i++) {
			for (int x = 0; x < this.coins.length; x++) {
				if (c[i].equals(this.coins[x])) {
					factory.getCashBox().release(this.coins[x]);
					coins[x] = null;
				}
			}
		}
		clearList();
		factory.getDisplay().info("Insert coins and select a drink!");
	}
	
	public void clearList(){
		for (int c = 0; c < this.coins.length; c++) {
			coins[c] = null;
		}
	}
	
	public ArrayList <Coin> moedaReversa(int valor){
		ArrayList<Coin> listAuxiliar = new ArrayList<Coin>();
		for (int c = 0; c < Coin.reverse().length; c++){
			while (Coin.reverse()[c].getValue() <= valor){
				listAuxiliar.add(Coin.reverse()[c]);
				valor = valor - Coin.reverse()[c].getValue();
			}
		}
		return listAuxiliar;
	}
	
	public ArrayList <Coin> liberarMoeda(int valor){
		ArrayList<Coin> listAuxiliar = new ArrayList<Coin>();
		for (int i = 0; i < Coin.reverse().length; i++){
			while (Coin.reverse()[i].getValue() <= valor){
				cashBox.release(Coin.reverse()[i]);
				valor = valor - Coin.reverse()[i].getValue();
			}
		}
		return listAuxiliar;
	}
	
	public int calcTroco(){
		int contCoins = 0;
		for(Coin c : Coin.reverse()){
			for(Coin aux : coins){
				if(aux == c){
					contCoins = contCoins + aux.getValue();
				}
			}
		}
		return contCoins - drink;
	}
	
	public boolean semTroco(int troco){
		for(Coin c : Coin.reverse()){
			if(c.getValue() <= troco && cashBox.count(c) > 0){
				troco -= c.getValue();
			}
		}
		
		return troco == 0;
	}
	
	public int[] planCoins(int change) {
		int[] changePlan = new int[6];
		int i = 0;
		for(Coin r : Coin.reverse()){
			if(r.getValue() <= change && cashBox.count(r) > 0){
				while(r.getValue() <= change){
					change -= r.getValue();
					changePlan[i]++;
				}
			}
			i++;
		}
		
		if(change != 0){
			throw new CoffeeMachineException("Não tem troco");
		}
		
		return changePlan;
	}
}
