package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.*;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;

public class MyCoffeeMachine implements CoffeeMachine {

	private int centavos = 0;
	private int dolar = 0;
	private ComponentsFactory factory;
	private List<Coin> dime = new ArrayList<Coin>(); 
	

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
		
	}

	public void insertCoin(Coin dime) throws CoffeeMachineException {
		this.dime.add(dime);
		try{
			dolar += dime.getValue() / 100;
			centavos += dime.getValue() % 100;
			factory.getDisplay().info("Total: US$ " + dolar + "." + centavos); //"Total: US$ 0.10"
		}
		catch(NullPointerException e){
			throw new CoffeeMachineException("A moeda inserida não é válida para esta máquina!");
		}
	}

	public void cancel() {
		if(dolar == 0 && centavos == 0){
			throw new CoffeeMachineException("Nenhuma Moeda Inserida na Máquina!");
		}
		
		factory.getDisplay().warn("Cancelling drink. Please, get your coins.");
		
		for(Coin ord : Coin.reverse()){
			for(Coin c : dime){
				if(ord == c)
					factory.getCashBox().release(c);
				
					
			}
		}
		factory.getDisplay().info("Insert coins and select a drink!");
	}

	public void select(Drink drink) {
		
		factory.getCupDispenser().contains(1);
		factory.getWaterDispenser().contains(0.1);
		
		if(!factory.getCoffeePowderDispenser().contains(0.1)){
			factory.getDisplay().warn("Out of Coffee Powder");
			factory.getCashBox().release(Coin.quarter);
			factory.getCashBox().release(Coin.dime);
			factory.getDisplay().info("Insert coins and select a drink!");
		}
		
		else{
			if(drink == Drink.BLACK_SUGAR){
				if(!factory.getSugarDispenser().contains(0.1)){
					factory.getDisplay().warn("Out of Sugar");
					factory.getCashBox().release(Coin.halfDollar);
					factory.getDisplay().info("Insert coins and select a drink!");
					return;
				}
			}
	
			factory.getDisplay().info("Mixing ingredients.");
			factory.getCoffeePowderDispenser().release(0.1);
			factory.getWaterDispenser().release(0.1);
			
			if(drink == Drink.BLACK_SUGAR){
				factory.getSugarDispenser().release(0.1);
			}
			
			factory.getDisplay().info("Releasing drink.");
			factory.getCupDispenser().release(1);
			factory.getDrinkDispenser().release(0.1);
			
			
			factory.getDisplay().info("Please, take your drink.");
			factory.getDisplay().info("Insert coins and select a drink!");
			dime.clear();
		}
	}
}
