package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;

public class MyCoffeeMachine implements CoffeeMachine {

	private int centavos;
	private int dolar;
	private ComponentsFactory factory;

	public MyCoffeeMachine(ComponentsFactory factory) {
		this.factory = factory;
		factory.getDisplay().info("Insert coins and select a drink!");
		
	}

	public void insertCoin(Coin dime) {
		centavos = dime.getValue() % 100;
		dolar = dime.getValue() / 100;
		factory.getDisplay().info("Total: US$ " + dolar+"."+centavos ); //"Total: US$ 0.10"
		}
}


/*Quando uma moeda é inserida, o display precisa ser invocado para exibir o valor total inserido.
Dica: trate os valores como centavos (inteiros) em vez de dólares (ponto flutuante). Para pegar 
os valores dos centavos use "total % 100" e para pegar os valores dos dólares use "total / 100".*/