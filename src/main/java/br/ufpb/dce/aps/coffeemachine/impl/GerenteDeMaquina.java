package br.ufpb.dce.aps.coffeemachine.impl;

import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Drink;
import br.ufpb.dce.aps.coffeemachine.Messages;

public class GerenteDeMaquina {
	private GerenteDeBebidas gerenteDeBebidas = new GerenteDeBebidas();
	
	public void iniciarPedido(ComponentsFactory factory, GerenteDeMoedas gerenteDeMoedas, Drink drink) {
		
		this.gerenteDeBebidas.iniciarBebida(factory, drink);
		
		if(!gerenteDeMoedas.conferirDinheiro(factory, gerenteDeBebidas.getValor())){
			return;
		}

		if (!gerenteDeBebidas.conferirIngredientes(factory, drink)) {
			gerenteDeMoedas.liberarMoedas(factory, false);
			return;
		}
		if (!gerenteDeBebidas.verificaAcucar(factory)) {
			gerenteDeMoedas.liberarMoedas(factory, false);
			return;
			
		}
		
		if(!gerenteDeMoedas.verificarTroco(factory, gerenteDeBebidas.getValor())){
			return;
		}

		gerenteDeBebidas.misturarIngredientes(factory, drink);
		gerenteDeBebidas.release(factory);
		
		
		if (gerenteDeMoedas.getTotal() >= gerenteDeBebidas.getValor()) {
			gerenteDeMoedas.liberaTroco(factory, gerenteDeBebidas.getValor());
		
		}
		
		factory.getDisplay().info(Messages.INSERT_COINS);
		gerenteDeMoedas.liberarMoedas();
	}

}
