package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import static org.mockito.Matchers.anyDouble;

public abstract class Bebidas {
	
	protected Drink drink;
	protected double anyDouble = anyDouble();
	protected ComponentsFactory factory;

	public abstract void release();	
	
	public Drink getDrink(){
		return this.drink;
	}
}
