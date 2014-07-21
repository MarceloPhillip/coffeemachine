package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
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

	public void insertCoin(Coin dime) {
		dolar += dime.getValue() / 100;
		centavos += dime.getValue() % 100;
		factory.getDisplay().info("Total: US$ " + dolar + "." + centavos); //"Total: US$ 0.10"
	}
}
