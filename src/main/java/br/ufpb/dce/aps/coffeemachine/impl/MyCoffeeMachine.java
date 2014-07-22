package br.ufpb.dce.aps.coffeemachine.impl;

import static org.mockito.Matchers.anyDouble;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.MockComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine {

	private int centavos = 0;
	private int dolar = 0;
	private ComponentsFactory factory;
	private Coin dime = null;

	public void init() {
		factory = new MockComponentsFactory();
	}
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
		
	}

	public void insertCoin(Coin dime) throws CoffeeMachineException {
		try{
			this.dime = dime;
			dolar += dime.getValue() / 100;
			centavos += dime.getValue() % 100;
			factory.getDisplay().info("Total: US$ " + dolar + "." + centavos); //"Total: US$ 0.10"
		}
		catch(NullPointerException e){
			throw new CoffeeMachineException("A moeda inserida não é válida para esta máquina!");
		}
	}

	public void cancel() {
		if(dime != null){
			factory.getDisplay().warn("Cancelling drink. Please, get your coins.");
			if(dime == Coin.halfDollar){
				factory.getCashBox().release(Coin.halfDollar);
			}
			else if(dime == Coin.nickel){
				factory.getCashBox().release(Coin.nickel);
				factory.getCashBox().release(Coin.penny);
			}
			else if(dime == Coin.quarter){
				factory.getCashBox().release(Coin.quarter);
				factory.getCashBox().release(Coin.quarter);
			}
			factory.getDisplay().info("Insert coins and select a drink!");
		}
		
		else{
			throw new CoffeeMachineException("Nenhuma Moeda Inserida na Máquina!");
		}

	}

	public void select(Drink drink) {
		if(factory.getCupDispenser().contains(1)){
			factory.getWaterDispenser().contains(anyDouble());
			factory.getCoffeePowderDispenser().contains(anyDouble());
			factory.getDisplay().info("Mixing ingredients.");
			factory.getCoffeePowderDispenser().release(anyDouble());
			factory.getWaterDispenser().release(anyDouble());
			factory.getDisplay().info("Releasing drink.");
			factory.getCupDispenser().release(1);
			factory.getDrinkDispenser().release(anyDouble());
		}
		if(factory.getSugarDispenser().contains(2)){
			
		}
		factory.getDisplay().info("Please, take your drink.");
		factory.getDisplay().info("Insert coins and select a drink!");
	}
}
