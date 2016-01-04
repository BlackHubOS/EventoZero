package br.com.blackhubos.eventozero.kit;

import org.bukkit.inventory.ItemStack;

import br.com.blackhubos.eventozero.ability.Ability;
import br.com.blackhubos.eventozero.shop.ShopItem;

public class Kit extends ShopItem{
	
	private final ItemStack icon;
	private final ItemStack[] contents;
	private final ItemStack[] armorContents;
	
	private Ability ability;
	
	public Kit(String name, ItemStack icon){
		super(name, icon);
		this.icon = icon;
		this.contents = new ItemStack[36];
		this.armorContents = new ItemStack[4];
	}
	
	public Ability getAbility(){
		return this.ability;
	}
	
	public ItemStack getIcon(){
		return this.icon;
	}
	
	public ItemStack[] getContents(){
		return this.contents;
	}
	
	public ItemStack[] getArmorContents(){
		return this.armorContents;
	}
	
	public Kit setContents(int index, ItemStack is){
		this.contents[index] = is;
		return this;
	}
	
	public Kit setArmorContents(int index, ItemStack is){
		this.armorContents[index] = is;
		return this;
	}
	
	public Kit updateAbility(Ability ability){
		this.ability = ability;
		return this;
	}

}