package me.artish1.OITC.Listeners;

import me.artish1.OITC.OITC;
import me.artish1.OITC.Arena.Arena;
import me.artish1.OITC.Arena.Arenas;
import me.artish1.OITC.Arena.GameState;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListener implements Listener{
	
	OITC plugin;
	
	public SignListener(OITC plugin) {
		this.plugin = plugin;	
	}
	
	@EventHandler
	public void onSignBreak(BlockBreakEvent e){
		if(e.getBlock().getState() instanceof Sign){
			Sign sign = (Sign) e.getBlock().getState();
			if(e.getPlayer().hasPermission("oitc.admin")){
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "密室死斗" + ChatColor.DARK_GRAY + "]")){
				if(e.getPlayer().isSneaking()){
					
					for(Arena arena : Arenas.getArenas()){
						if(sign.getLine(1).equalsIgnoreCase( ChatColor.BOLD + arena.getName() )){
							arena.removeSign(e.getBlock().getLocation());
							OITC.sendMessage(e.getPlayer(), "你移除了一个牌子, 这个牌子来自:" + ChatColor.DARK_AQUA + arena.getName());
							
							break;
						}
					}
					
				}else{
					e.setCancelled(true);
					sign.update(true);
					OITC.sendMessage(e.getPlayer(), "如果你想破坏这个牌子, 请在蹲伏的同时破坏这个牌子!");
				}
				
			}
			}else{
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "密室死斗" + ChatColor.DARK_GRAY + "]")){
					e.setCancelled(true);
					sign.update(true);
				}
				
			}
			
			
			
			
			
		}
	}
	
	@EventHandler
	public void onSignCreate(SignChangeEvent e){
		Player player = e.getPlayer();
	    if ((e.getLine(0).equalsIgnoreCase("oitc")) && 
	      (player.hasPermission("oitc.admin"))) {
	      for (Arena arena : Arenas.getArenas()) {
	    	  
	        if (e.getLine(1).equalsIgnoreCase(arena.getName()))
	        {

	        	e.setLine(0, ChatColor.DARK_GRAY + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + "密室死斗" + ChatColor.DARK_GRAY + "]");
	          e.setLine(1, ChatColor.BOLD + arena.getName());
	          e.setLine(3, ChatColor.BOLD + ""+arena.getPlayers().size() + "/" + arena.getMaxPlayers());
	          
	          if (arena.getState() == GameState.INGAME) {
		          e.setLine(2, ChatColor.DARK_RED + "正在游戏");
		        } else {
		        	if(arena.getState() == GameState.LOBBY){
		          e.setLine(2, ChatColor.GREEN + "等待玩家中");
		        	}else{
		        		if(arena.getState() == GameState.STOPPING){
		      	          e.setLine(2, ChatColor.RED + "正在结束");
		        		}else{
		        			if(arena.getState() == GameState.STARTING){
		        		          e.setLine(2, ChatColor.AQUA + "正在开始");
		        			}
		        		}
		        	}
		        }
	          
	          
	
	          arena.addSign(e.getBlock().getLocation());
	          arena.updateSigns();
	          player.sendMessage(ChatColor.GRAY + "你制作了一个加入牌子, 右击这个牌子可以加入场地: " + ChatColor.GOLD + arena.getName());
	        }
	      }
	    }
	}
	
	
	
	@EventHandler
	public void onSignInteract(PlayerInteractEvent e){
		Player player = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.hasBlock() && e.getClickedBlock().getState()  instanceof Sign){
			Sign sign = (Sign) e.getClickedBlock().getState();
				if(sign.getLine(0).equalsIgnoreCase(ChatColor.DARK_GRAY + "[" + ChatColor.AQUA.toString() + ChatColor.BOLD.toString() + 
			        "密室死斗" + ChatColor.DARK_GRAY + "]"))
				{
			        sign.update();
			        
			        if(Arenas.isInArena(player)){
			        	player.sendMessage(ChatColor.RED + "你在就在一个场地中了!");
			        	player.sendMessage(ChatColor.GRAY + "如果你想离开现在所在的场地, 请使用指令 /oitc leave");
			        	return;
			        }
			        
			        	for(Arena arena : Arenas.getArenas()){
			        		if(sign.getLine(1).equalsIgnoreCase( ChatColor.BOLD + arena.getName() )){
			        		if(!arena.hasPlayer(player)){
			        			//if(!arena.isOn()){
			        				if(arena.getMaxPlayers() > arena.getPlayers().size()){
			        					
			        					arena.addPlayer(player);
			        					
			        					
			        					
			        				}else{
			        					player.sendMessage(ChatColor.RED + "对不起! 那个场地已经满了!");
			        				}
			        				
			        				
			        			//}else{
			        				//player.sendMessage(ChatColor.RED + "Sorry! That Arena is " + arena.getState().toString());
			        			//}
			        		}
			        		
			        		break;
			        	}
			        }
				}
		}
		
	}
	
	
}
