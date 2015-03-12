package io.github.daschnerj.particleWands;

import io.github.daschnerj.particleWands.commands.WandCommand;
import io.github.daschnerj.particleWands.listeners.WandListener;
import io.github.daschnerj.particleWands.other.Trails;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class ParticleWands extends JavaPlugin 
{
	
	private final WandListener wandListener = new WandListener(this);
	
	@Override
	public void onEnable() 
	{
		PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(wandListener, this);
		getLogger().info("onEnable has been invoked!");
		
		this.getCommand("wand").setExecutor(new WandCommand());
		
		BukkitTask trails = new Trails(this).runTaskTimer(this, 1, 1);
		trails.isSync();
	}
 
	@Override
	public void onDisable() 
	{
		getLogger().info("onDisable has been invoked!");
	}
}
