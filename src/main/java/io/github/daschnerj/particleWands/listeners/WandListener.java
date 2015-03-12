package io.github.daschnerj.particleWands.listeners;

import java.util.Arrays;
import java.util.List;

import io.github.daschnerj.particleWands.ParticleWands;
import io.github.daschnerj.particleWands.other.Trails;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WandListener implements Listener 
{
	
	public final ParticleWands plugin;
	
	public static String[] sColor = {"aqua", "black", "blue",
			  		   				 "fuchsia", "gray", "green",
			  		   				 "lime", "maroon", "navy",
			  		   				 "olive", "orange", "purple",
			  		   				 "red", "silver", "teal",
			  		   				 "white", "yellow"};
	public static Color[] cColor = {Color.AQUA, Color.BLACK, Color.BLUE,
					                Color.FUCHSIA, Color.GRAY, Color.GREEN,
					                Color.LIME, Color.MAROON, Color.NAVY,
					                Color.OLIVE, Color.ORANGE, Color.PURPLE,
					                Color.RED, Color.SILVER, Color.TEAL,
					                Color.WHITE, Color.YELLOW};

    public WandListener(ParticleWands instance) 
    {
        plugin = instance;
    }
    
    @EventHandler
    public void onPlayerShootEvent(PlayerInteractEvent e)
    {	
    	if(!(e.getAction() == Action.RIGHT_CLICK_AIR)) return; 
    		if(!(e.getItem().getType() == Material.BLAZE_ROD)) return; 
    		{
    			Player p = e.getPlayer();
    			if(Trails.cooldownFireTimer.containsKey(p)) return;
    				if(!checkValidWand(e)) return;
    					Color color = getColor(e);
    					shootParticles(e, color);
    					Trails.cooldownFireTimer.put(p, 6);
    		}
    			
    }
    
    public void shootParticles(PlayerInteractEvent event, Color color)
    {	
    	Player p = event.getPlayer();
    	Snowball ball = p.getWorld().spawn(p.getEyeLocation(), Snowball.class);
    	ball.setShooter(p);
    	ball.setVelocity(p.getLocation().getDirection().multiply(5));
    	Trails.snowballTimer.put(ball, color);
    	Trails.snowballTime.put(ball, 60);
    }
    
    public static boolean checkValidWand(PlayerInteractEvent e)
    {
    	boolean isWand = false;
    	ItemStack wand = e.getItem();
    	ItemMeta wandMeta = wand.getItemMeta();
    	List<String> lore = wandMeta.getLore();
    	if(lore != null)
    	{
    		for(String s : lore)
    		{
    			List<String> seperated = Arrays.asList(s.split(":"));
    			for(String split : seperated)
    			{ 				
    				if(split.equalsIgnoreCase("particle wand"))
    				{
    					isWand = true;
    				}
    			}
    		}
    	}
    	return isWand;
    }
    
    public Color getColor(PlayerInteractEvent event)
    {
    	boolean isWand = false;
    	Color color = Color.WHITE;
    	ItemStack wand = event.getItem();
    	ItemMeta wandMeta = wand.getItemMeta();
    	List<String> lore = wandMeta.getLore();
    	if(lore != null)
    	{
    		for(String s : lore)
    		{
    			List<String> seperated = Arrays.asList(s.split(":"));
    			for(String split : seperated)
    			{
    				if(isWand == true)
    				{
    					int length = sColor.length;
    					for(int i = 0; i < length; i++)
    					{
    						if(split.equalsIgnoreCase(sColor[i]))
    						{
    							color = cColor[i];
    						}
    					}
    					isWand = false;
    				}
    				if(split.equalsIgnoreCase("particle wand"))
    				{
    					isWand = true;
    				}
    			}
    		}
    	}
    	
    	return color;
    }
    
    public Boolean getColorPermission(PlayerInteractEvent event)
    {
    	boolean hasPermission = false;
    	boolean isWand = false;
    	Color color = Color.WHITE;
    	ItemStack wand = event.getItem();
    	ItemMeta wandMeta = wand.getItemMeta();
    	List<String> lore = wandMeta.getLore();
    	if(lore != null)
    	{
    		for(String s : lore)
    		{
    			List<String> seperated = Arrays.asList(s.split(":"));
    			for(String split : seperated)
    			{
    				if(isWand == true)
    				{
    					int length = sColor.length;
    					for(int i = 0; i < length; i++)
    					{
    						if(split.equalsIgnoreCase(sColor[i]))
    						{
    							if(event.getPlayer().hasPermission("wand." + sColor) || event.getPlayer().isOp())
    							{
    								hasPermission = true;
    							}
    						}
    					}
    					isWand = false;
    				}
    				if(split.equalsIgnoreCase("particle wand"))
    				{
    					isWand = true;
    				}
    			}
    		}
    	}
    	
    	return hasPermission;
    }
}
