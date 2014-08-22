package me.artish1.OITC;

import java.io.File;
import java.util.logging.Logger;

import me.artish1.OITC.Arena.Arena;
import me.artish1.OITC.Arena.Arenas;
import me.artish1.OITC.Arena.LeaveReason;
import me.artish1.OITC.Listeners.*;
import me.artish1.OITC.Utils.Methods;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class OITC extends JavaPlugin {
		
		public final Logger logger = Logger.getLogger("Minecraft");
		public final Methods m = new Methods(this);
		public final GameListener gl = new GameListener(this);
		public final SignListener sl = new SignListener(this);
		
	
		public File kitsFile;
		public static FileConfiguration kits;
		public File playersFile;
		public static FileConfiguration players;
		public File arenasFile;
		public FileConfiguration arenas;
	
	
	  public static FileConfiguration getKitsFile(){
		  return kits;
	  }
	  
	  
	  
	  
	 
	
	public void onEnable() {
		
		//LOADING CONFIG FILES ****************************
	    System.out.println("正在加载YML文件!");
	    System.out.println("密室死斗(OITC)插件, TeamTF搬运/汉化! Translated By TeamTF!");
	    this.playersFile = new File(getDataFolder(), "players.yml");
	    this.arenasFile = new File(getDataFolder(), "arenas.yml");
	    this.kitsFile = new File(getDataFolder(), "kits.yml");
	    
	    kits = new YamlConfiguration();
	    this.arenas = new YamlConfiguration();
	    players = new YamlConfiguration();
	    
	    Methods.loadYamls();
	    
	    this.arenas.options().copyDefaults(true);
	    players.options().copyDefaults(true);
	    kits.options().copyDefaults(true);
	    
	    getConfig().options().copyDefaults(true);
	    
	    System.out.println("成功加载YML文件!");
	    //********************CONFIG FILES***********************
	    
	    
	    
	    
	    //***********LISTENERS*****************
	    
	    getServer().getPluginManager().registerEvents(gl, this);
	    getServer().getPluginManager().registerEvents(sl, this);
	  //  getServer().getPluginManager().registerEvents(gl, this);
	  //  getServer().getPluginManager().registerEvents(gl, this);

	    
	    
	    //*************************************
	    
	    
	    
	    
	    try
	    {
	      for (String s : this.arenas.getStringList("Arenas.List"))
	      {
	        Arena arena = new Arena(s);
	        this.logger.info("[密室死斗] 正在加载场地: " + arena.getName());
	        
	        Arenas.addArena(arena);
	        arena.updateSigns();
	        this.logger.info("[密室死斗] 场地: " + arena.getName() + " 成功加载!");
	      }
	    }
	    catch (Exception e)
	    {
	      this.logger.info("[密室死斗] 加载场地失败.");
	    }
	    try
	    {
	      this.m.firstRun();
	    }
	    catch (Exception localException1) {}
	    Methods.loadYamls();
		
		super.onEnable();
	}
	
	
	public void onDisable() {
		
		for(Arena arena : Arenas.getArenas()){
			arena.stop();
		}
		
		super.onDisable();
	}
	
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		if(label.equalsIgnoreCase("oitc") && !(sender instanceof Player)){
			sender.sendMessage("你必须是一个在游戏内的玩家");
		}
		
		if(label.equalsIgnoreCase("oitc") && sender instanceof Player){
			Player player = (Player) sender;
			
			if(args.length == 0){
				player.sendMessage("");
		          player.sendMessage("");
		          player.sendMessage("");
		          player.sendMessage("");
		          player.sendMessage("");
		          player.sendMessage("");
		          player.sendMessage("");
		          player.sendMessage("");
		          

		          player.sendMessage(ChatColor.GRAY + "--------" + ChatColor.AQUA + "密室死斗" + ChatColor.GRAY + "--------");
		          player.sendMessage(ChatColor.GRAY + "插件作者: " + ChatColor.RED + "Artish1" + ChatColor.GRAY + "翻译: " + ChatColor.RED + "TeamTF本地化小组");
		          
		          player.sendMessage(ChatColor.AQUA + "/oitc lobby" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "将你传送至游戏大厅");
		          player.sendMessage(ChatColor.AQUA + "/oitc leave" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "离开你目前所在的场地");
		          if (player.hasPermission("oitc.admin"))
		          {
		            player.sendMessage(ChatColor.AQUA + "/oitc create [场地名]" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "创建一个新的场地");
		            player.sendMessage(ChatColor.AQUA + "/oitc delete [场地名]" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "删除一个场地");
		            player.sendMessage(ChatColor.AQUA + "/oitc addspawn [场地名]" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "为一个场地添加重生点");
		            
		            player.sendMessage(ChatColor.AQUA + "/oitc setmainlobby" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + " 设置游戏大厅");
		            player.sendMessage(ChatColor.AQUA + "/oitc setlobby [场地名]" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + " 为某个场地设置单独的大厅.");
		            player.sendMessage(ChatColor.AQUA + "/oitc stop [场地名]" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "强制停止这个场地的游戏");
		            player.sendMessage(ChatColor.AQUA + "/oitc start [场地名]" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "强制开始这个场地的游戏");
		            player.sendMessage(ChatColor.AQUA + "/oitc reload" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "重置所有配置文件");
		            player.sendMessage(ChatColor.AQUA + "/oitc list" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "所有场地的列表");
		            player.sendMessage(ChatColor.AQUA + "/oitc version" + ChatColor.DARK_GRAY + " || " + ChatColor.GRAY + "插件版本");

		          }
			}
			
			
			if(args.length == 1){
				if(args[0].equalsIgnoreCase("list")){
					
					String arenas = "场地列表: " + ChatColor.DARK_AQUA;
					
					for(Arena arena : Arenas.getArenas()){
						arenas = arenas + arena.getName() + ", ";
					}
					
					sendMessage(player, arenas);
				}
				
				if(args[0].equalsIgnoreCase("version")){
					sendMessage(player, "你正在使用密室死斗版本: " + ChatColor.RED + getDescription().getVersion());
					
				}
				
				
				if(player.hasPermission("oitc.admin")){
					if(args[0].equalsIgnoreCase("setmainlobby")){
						Methods.setLobby(player.getLocation());
						sendMessage(player, "你成功设置了游戏大厅!");
					}
					
					if(args[0].equalsIgnoreCase("reload")){
						Methods.loadYamls();
						reloadConfig();
						for(Arena arena : Arenas.getArenas()){
							arena.updateSigns();
						}
						
						sendMessage(player, "重置设定文件成功!");
					}
				
				}
				
				if(args[0].equalsIgnoreCase("lobby")){
					if(!Arenas.isInArena(player)){
						if(Methods.getLobby() != null){
							player.teleport(Methods.getLobby());
							sendMessage(player, "欢迎来到 " + ChatColor.DARK_AQUA + "游戏大厅!");
							
						}else{
							sendMessage(player, "啊哦, 看起来你还没有设置游戏大厅噢! 请联系服务器管理员!");
						}
					}else{
						Arena arena = Arenas.getArena(player);
						sendMessage(player, "你离开了你当前的场地, 并进入了游戏大厅.");
						arena.removePlayer(player, LeaveReason.QUIT);
					}
				}
				
				if(args[0].equalsIgnoreCase("leave")){
					if(Arenas.isInArena(player)){
						Arena arena = Arenas.getArena(player);
						arena.removePlayer(player, LeaveReason.QUIT);
					}else{
						sendMessage(player,"你不在任何一个场地中, 但你还是会被传送至游戏大厅!");
					}
				}
				
				
			}
			
			
			if(args.length == 2){
				if(player.hasPermission("oitc.admin")){
				if(args[0].equalsIgnoreCase("create")){
					if (!Arenas.arenaExists(args[1]))
		            {
		              this.arenas.addDefault("Arenas." + args[1], args[1]);
		              this.arenas.addDefault("Arenas." + args[1] + ".Signs.Counter", Integer.valueOf(0));
		              getConfig().addDefault(args[1] + ".Countdown", Integer.valueOf(15));
		              getConfig().addDefault(args[1] + ".MaxPlayers", Integer.valueOf(20));
		              getConfig().addDefault(args[1] + ".KillsToWin", Integer.valueOf(25));
		              getConfig().addDefault(args[1] + ".AutoStartPlayers", Integer.valueOf(8));
		              getConfig().addDefault(args[1] + ".EndTime", Integer.valueOf(600));
		              Arena arena = new Arena(args[1]);
		              Arenas.addArena(arena);
		              Methods.addToList(arena);
		              sendMessage(player, ChatColor.GRAY + "你成功创建了场地: " + ChatColor.GOLD + arena.getName());
		              Methods.saveYamls();
		              saveConfig();
		            }
		            else
		            {
		            	sendMessage(player, ChatColor.RED + "此场地已经存在!");
		            }
				}
				
				if(args[0].equalsIgnoreCase("delete")){
					if (getConfig().contains(args[1])){
		              getConfig().set(args[1], null);
		              this.arenas.set("Arenas." + args[1], null);
		              
		              Methods.removeFromList(args[1]);
		              
		              Methods.saveYamls();
		              saveConfig();
		              sendMessage(player, "你删除了场地: " + ChatColor.DARK_RED + args[1]);
		            }
		            else
		            {
		              sendMessage(player, "对不起, 此场地不存在 " + ChatColor.RED + args[1]);
		            }
				}
				
				
				
				if(args[0].equalsIgnoreCase("start")){
					if(Arenas.arenaExists(args[1])){
						Arena arena = Arenas.getArena(args[1]);
						
						if(arena.getPlayers().size() >= 2){
							arena.start();
							sendMessage(player, "你开启了下列场地中的游戏: " + ChatColor.DARK_AQUA + arena.getName());
							
						}else{
							sendMessage(player, "无法开启场地中的游戏.");
							sendMessage(player, "游戏模式要么是在游戏中, 正在停止或者就是没有足够的玩家.");
						}
						
						
					}else{
						sendMessage(player, "Sorry, there is no such arena named " + ChatColor.RED + args[1]);
					}
				}
				
				if(args[0].equalsIgnoreCase("stop")){
					if(Arenas.arenaExists(args[1])){
							
						Arena arena = Arenas.getArena(args[1]);
						arena.sendAll(ChatColor.RED + player.getName() + ChatColor.GRAY + " 停止了场地中的游戏!");	
						arena.stop();
						
						
					}else{
						sendMessage(player, "对不起, 没有这个名字的场地: " + ChatColor.RED + args[1]);
					}
				}
				
				if(args[0].equalsIgnoreCase("addspawn")){
					if(Arenas.arenaExists(args[1])){
						Arena arena = Arenas.getArena(args[1]);
						arena.addSpawn(player.getLocation()); 
						sendMessage(player, "你为下列场地增加了一个重生点: " + ChatColor.DARK_AQUA + arena.getName());
						
					}else{
						sendMessage(player, "对不起, 没有这个名字的场地: " + ChatColor.RED + args[1]);
					}
				}
				
				if(args[0].equalsIgnoreCase("setlobby")){
					if(Arenas.arenaExists(args[1])){
						Arena arena = Arenas.getArena(args[1]);
						arena.setLobbySpawn(player.getLocation()); 
						sendMessage(player, "你为下列场地设置了大厅的重生点: " + ChatColor.DARK_AQUA + arena.getName());
					}else{
						sendMessage(player, "对不起, 没有这个名字的场地: " + ChatColor.RED + args[1]);
					}
				}
				
				}
				
				
				
			}
			
			
			
		}
		
		
		
		
		return super.onCommand(sender, command, label, args);
	}
	
	
	 public static void sendMessage(Player player, String Message)
	  {
	    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.AQUA + "密室死斗" + ChatColor.GRAY + "] " + ChatColor.GRAY + Message);
	  }
	
}
