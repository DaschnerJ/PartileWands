package io.github.daschnerj.particleWands.other;

import io.github.daschnerj.particleWands.ParticleWands;

import java.util.ConcurrentModificationException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Trails extends BukkitRunnable
{
	public static HashMap<Snowball, Color> snowballTimer = new HashMap<Snowball, Color>();
	public static HashMap<Snowball, Integer> snowballTime = new HashMap<Snowball, Integer>();
	public static HashMap<Player, Integer> cooldownFireTimer = new HashMap<Player, Integer>();
	
	public ParticleWands plugin;
	
	FireworkEffectPlayer fp = new FireworkEffectPlayer();

	public Trails(ParticleWands plugin)
	{
		this.plugin = plugin;
	}

	public void run()
	{
		try
		{
			coolDowns();
			if(!snowballTimer.isEmpty())
				for(Snowball s : snowballTimer.keySet())
				{
					try 
					{
						Firework fw = (Firework) s.getWorld().spawn(s.getLocation(), Firework.class);
						FireworkMeta meta = fw.getFireworkMeta();
						meta.addEffect(FireworkEffect.builder().withColor(snowballTimer.get(s)).with(Type.BALL).build());
						fw.setFireworkMeta(meta);
						fw.setVelocity(s.getLocation().getDirection().multiply(0));
						detonate(fw);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					if(s.isDead())
					{
						if(snowballTimer.containsKey(s))
							snowballTimer.remove(s);
						if(snowballTime.containsKey(s))
							snowballTime.remove(s);
					}
					if(!s.isValid())
					{
						if(snowballTimer.containsKey(s))
							snowballTimer.remove(s);
						if(snowballTime.containsKey(s))
							snowballTime.remove(s);
					}
			
					if(snowballTime.containsKey(s))
					{
						int time = snowballTime.get(s) - 1;
						snowballTime.remove(s);
						snowballTime.put(s, time);
						if(time <= 0)
						{
							s.remove();
						}
					}
				}
		}
		catch(ConcurrentModificationException e)
		{
			
		}
    }
	
	public void coolDowns()
	{
		for(Player p : cooldownFireTimer.keySet())
		{
			int time = cooldownFireTimer.get(p);
			cooldownFireTimer.remove(p);
			cooldownFireTimer.put(p, time - 1);
			if( time <= 0)
			{
				cooldownFireTimer.remove(p);
			}
		}
	}
	 public void detonate(final Firework fw){
	        //Wait 1 Tick and Detontae
	        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
	        {
	            public void run() 
	            {
	                try
	                {
	                    fw.detonate();
	                }
	                catch(Exception e)
	                {
	                	
	                }
	            }
	        }, (2));
	    }


}
