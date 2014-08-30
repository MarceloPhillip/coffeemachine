package br.ufpb.dce.aps.coffeemachine.impl;

import java.util.ArrayList;
import br.ufpb.dce.aps.coffeemachine.CoffeeMachineException;
import br.ufpb.dce.aps.coffeemachine.Coin;
import br.ufpb.dce.aps.coffeemachine.ComponentsFactory;
import br.ufpb.dce.aps.coffeemachine.Messages;


public class GerenteDeMoedas {
	private Coin[] reverso = Coin.reverse();
	private int total;
	private int inteiro;
	private int centavos;
	private String type = "vazio";
	private ArrayList<Coin> moedas = new ArrayList<Coin>();
	private ArrayList<Coin> trocos = new ArrayList<Coin>();

	public void inserirMoeda(ComponentsFactory factory, Coin coin)throws CoffeeMachineException {
		if(this.type.equalsIgnoreCase("Cracha")){
			factory.getDisplay().warn(Messages.CAN_NOT_INSERT_COINS);
			releaseCoinCracha(factory, coin);
			return;
		}
		else{
			try {
				total += coin.getValue();
				moedas.add(coin);
				inteiro = total/100;
				centavos = total%100;
				factory.getDisplay().info("Total: US$ " + inteiro + "." + centavos);
			}
			catch (NullPointerException e) {
				throw new CoffeeMachineException("moeda invalida");
			}
		}	
	}
	
	public void setModo(String newType) {
		this.type = newType;
	}

	public void cancelar(ComponentsFactory factory) throws CoffeeMachineException {
		if (total == 0) {
			throw new CoffeeMachineException("sem moedas inseridas");
		}
		liberarMoedas(factory, true);

	}

	public void liberarMoedas(ComponentsFactory factory, Boolean confirmacao) {
		if (confirmacao) {
			factory.getDisplay().warn(Messages.CANCEL);
		}
		for (Coin re : this.reverso) {
			for (Coin aux : this.moedas) {
				if (aux == re) {
					factory.getCashBox().release(aux);
				}
			}
		}
		total = 0;
		liberarMoedas();
		factory.getDisplay().info(Messages.INSERT_COINS);
	}

	public boolean calculaTroco(ComponentsFactory factory, double valorDaBebida) {
		double troco = total - valorDaBebida;
		for (Coin moeda : reverso) {
			if (moeda.getValue() <= troco) {
				int count = factory.getCashBox().count(moeda);
				while (moeda.getValue() <= troco &&  count > 0) {
					troco = troco - moeda.getValue();
					trocos.add(moeda);
				}
			}
		}
		return (troco == 0);
	}

	public void liberaTroco(ComponentsFactory factory, double valorDaBebida) {
		for (Coin moeda : reverso) {
			for (Coin moedaDeTroco : trocos) {
				if (moedaDeTroco == moeda) {
					factory.getCashBox().release(moeda);
				}
			}
		}
	}

	public boolean conferirDinheiro(ComponentsFactory factory,
			double valorDaBebida) {
		if (total < valorDaBebida || total == 0) {
			factory.getDisplay().warn(Messages.NO_ENOUGHT_MONEY);
			liberarMoedas(factory, false);
			return false;
		}
		return true;
	}

	public boolean verificarTroco(ComponentsFactory factory,
			double valorDaBebida) {
		if(!calculaTroco(factory, valorDaBebida)){
			factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
			liberarMoedas(factory, false);
			return false;
		}
//		if (total % valorDaBebida != 0 && total > valorDaBebida) {
//			if (!calculaTroco(factory, valorDaBebida)) {
//				factory.getDisplay().warn(Messages.NO_ENOUGHT_CHANGE);
//				liberarMoedas(factory, false);
//				return false;
//			}
//		}
		return true;
	}

	public void liberarMoedas() {
		moedas.clear();
		total = 0;
	}

	public int getTotal() {
		return total;
	}
	
	private void releaseCoinCracha(ComponentsFactory factory, Coin coin) {
				factory.getCashBox().release(coin);
			}
}
