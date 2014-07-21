package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.MockComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine {

	private int centavos = 0;
	private int dolar = 0;
	private ComponentsFactory factory;

	public void init() {
		factory = new MockComponentsFactory();
	}
	
	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
		
	}

	public void insertCoin(Coin dime) throws CoffeeMachineException {
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
		if (dolar == 0 && centavos == 0) {
			throw new CoffeeMachineException("Nenhuma Moeda Inserida na Máquina!");
		}
		else{
			factory.getDisplay().warn("Cancelling drink. Please, get your coins.");
			factory.getCashBox().release(Coin.halfDollar);
			factory.getDisplay().info("Insert coins and select a drink!");
		}
	}
}
