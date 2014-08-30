package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachine;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class MyCoffeeMachine implements CoffeeMachine {

	private ComponentsFactory factory;
	private GerenteDeMoedas gerenteDeMoedas;
	private GerenteDeMaquina gerenteDeMaquina;

	public void insertCoin(Coin dime) {
		gerenteDeMoedas.inserirMoeda(factory, dime);
	}

	public void cancel() throws CoffeeMachineException {
		gerenteDeMoedas.cancelar(factory);
		
	}
		
	public void select(Drink drink) {
		gerenteDeMaquina.iniciarPedido(factory, gerenteDeMoedas, drink);
	}

	public void setFactory(ComponentsFactory factory) {
		this.factory = factory;
		gerenteDeMoedas = new GerenteDeMoedas();
		gerenteDeMaquina = new GerenteDeMaquina();
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public void readBadge(int badgeCode) {
		factory.getDisplay().info(Messages.BADGE_READ);
		gerenteDeMoedas.setModo("Cracha");
	}
	
}
