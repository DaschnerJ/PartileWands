package io.github.daschnerj.particleWands.commands;

import java.util.Arrays;
import java.util.List;

import io.github.daschnerj.particleWands.listeners.WandListener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandCommand implements CommandExecutor
{
	String prefix = ChatColor.WHITE + "[" + ChatColor.AQUA + "Particle" + ChatColor.DARK_PURPLE + "Wands" + ChatColor.WHITE + "] ";
	String topBar = ChatColor.GOLD + "=============[" + ChatColor.WHITE + "ParticleWands" + ChatColor.GOLD + "]=============";
	
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) 
	{

		//Check if it is a player.
	       if(sender instanceof Player)
	       {
	    	   //Get Player
	    		Player p = (Player) sender;
	    		//Check if the alias is /cp
	    		if (alias.equalsIgnoreCase("wand")) 
	    		{
	    			//Check if it is just /cp
	    			if(args.length == 0)
	    			{
	    				p.sendMessage(topBar);
	    				p.sendMessage(ChatColor.GREEN + "/wand [color] " + ChatColor.RED + "to set the color of the wand.");
	    				p.sendMessage(ChatColor.GREEN + "/wand list " + ChatColor.RED + "to see the list of available colors.");
	    				return true;
	    			}
	    			else if(args.length == 1)
	    			{
	    				if(args[0].equalsIgnoreCase("list"))
	    				{
	    					String wandColors = null;
	    					for(String s : WandListener.sColor)
	    					{
	    						if(wandColors == null)
	    						{
	    							wandColors = ChatColor.AQUA + s;
	    						}
	    						else
	    						{
	    							wandColors = wandColors + ChatColor.GREEN + ", " + ChatColor.AQUA + s;
	    						}
	    					}
	    					p.sendMessage(ChatColor.RED + "Available Particle Wand colors are: ");
	    					p.sendMessage(wandColors + ChatColor.GREEN + ".");
	    					return true;
	    				}
	    				else if(p.getInventory().getItemInHand().getType().equals(Material.BLAZE_ROD))
	    				{
	    					int length = WandListener.sColor.length;
	    					for(int i = 0; i < length; i++)
	    					{
	    						if(args[0].equalsIgnoreCase(WandListener.sColor[i]))
	    						{
	    							if(p.hasPermission("wand." + WandListener.sColor[i]) || p.hasPermission("wand.*"))
	    							{
	    								ItemStack itemstack = p.getInventory().getItemInHand();
	    								ItemMeta itemmeta = itemstack.getItemMeta();
	    								List<String> list = Arrays.asList("Particle Wand:" + WandListener.sColor[i]);
	    								itemmeta.setLore(list);
	    								itemstack.setItemMeta(itemmeta);
	    							}
	    							else
	    							{
	    								p.sendMessage(prefix + ChatColor.RED + "You do not have this permission!");
	    							}
	    						}
	    					}
	    				}
	    				else
	    				{
	    					p.sendMessage(prefix + ChatColor.RED + "Make sure you are holding a blaze rod!");
	    					return true;
	    				}
	    			}
	    			else
	    			{
	    				return true;
	    			}
	    		}
	    		else
	    		{
	    			return true;
	    		}
	       }
	       else
	       {
	    	   return true;
	       }
		return true;
	}
}
