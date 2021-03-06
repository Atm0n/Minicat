package com.biel.lobby;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.connorlinfoot.bountifulapi.BountifulAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import com.biel.BielAPI.Utils.IconMenu;
import com.biel.BielAPI.Utils.Pair;
import com.biel.lobby.mapes.Joc;
import com.biel.lobby.mapes.MapaResetejable;
import com.biel.lobby.mapes.MapaResetejable.MapMode;
import com.biel.lobby.mapes.jocs.*;


public class GestorMapes implements Listener{
	public lobby plugin;
	ArrayList<Pair<String, Double>> auto_ratings;
	ArrayList<ContenidorMapa> Mapes = new ArrayList<>();
	public GestorMapes() {

		this.plugin = lobby.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		//Mapes.add(new ContenidorJoc(ObsidianDefenders.class, "Obsidian defenders", Material.OBSIDIAN, DevelopmentState.NotWorking));
		Mapes.add(new ContenidorJoc(Spleef.class, "Spleef", Material.SNOW, DevelopmentState.Release));
		Mapes.add(new ContenidorJoc(RainbowClay.class, "Rainbow Clay", Material.HARD_CLAY, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(Torres.class, "Torres de defensa", Material.ARROW, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(Quakecraft.class, "Quakecraft", Material.STONE_HOE, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(Dominion.class, "Dominion", Material.DIAMOND, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(TeamDeathMatch.class, "Team Death Match", Material.IRON_SWORD, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(Arena4.class, "Arena 4", Material.BAKED_POTATO, DevelopmentState.Alpha));
		//Mapes.add(new ContenidorJoc(Coliseu.class, "Coliseu", Material.QUARTZ_BLOCK, DevelopmentState.NotWorking));
		//Mapes.add(new ContenidorJoc(TheTowers.class, "The Towers", Material.EXP_BOTTLE, DevelopmentState.NotWorking));
		Mapes.add(new ContenidorJoc(TNTRun.class, "TNT Run", Material.TNT, DevelopmentState.KnownIssues));
		//Mapes.add(new ContenidorJoc(RoboRampage.class, "ToTheSky", Material.LAPIS_BLOCK, DevelopmentState.InDevelopment));
		//Mapes.add(new ContenidorJoc(Arena1v1.class, "Arena 1v1", Material.WOOD_SWORD, DevelopmentState.PreAlpha));
		Mapes.add(new ContenidorJoc(ArenaAllvAll.class, "Arena ALLvsALL", Material.SAND, DevelopmentState.Beta));
		Mapes.add(new ContenidorJoc(BaseLunar.class, "Base Lunar", Material.GLASS, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(BoletumDTC.class, "Boletus DTC", Material.MUSHROOM_SOUP, DevelopmentState.Beta));
		//Mapes.add(new ContenidorJoc(DominionTitan.class, "Dominion Titan", Material.DIAMOND_AXE, DevelopmentState.Beta));
		//Mapes.add(new ContenidorJoc(DominionKOTH.class, "Dominion KOTH", Material.DIAMOND_BARDING, DevelopmentState.Beta));
		//Mapes.add(new ContenidorJoc(TeamDeathMatchJaneatorForest.class, "TDM Janeator Forest", Material.SAPLING, DevelopmentState.Alpha));
		//Mapes.add(new ContenidorJoc(InfernoRush.class, "Inferno Rush", Material.BLAZE_POWDER, DevelopmentState.InDevelopment));
		Mapes.add(new ContenidorJoc(KingSkeletonChallenge.class, "King Skeleton", Material.GOLD_HELMET, DevelopmentState.PreAlpha));
		//Mapes.add(new ContenidorJoc(WarehouseKOTH.class, "Warehouse KOTH", Material.WOOD, DevelopmentState.Beta));
		//Mapes.add(new ContenidorJoc(OniChan.class, "Oni-Chan", Material.CAKE, DevelopmentState.InDevelopment));
		//Mapes.add(new ContenidorJoc(PixelRift.class, "Pixel Rift", Material.ITEM_FRAME, DevelopmentState.KnownIssues));
		//Mapes.add(new ContenidorJoc(ResourceRush.class, "Resource Rush", Material.DIAMOND_ORE, DevelopmentState.InDevelopment));
		Mapes.add(new ContenidorJoc(Parkour.class, "ParkourFlow", Material.GOLD_BLOCK, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(InkWars.class, "Ink Wars", Material.COAL_BLOCK, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(RedstoneWars.class, "Redstone Wars", Material.REDSTONE_BLOCK, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(PilotaSplash.class, "Pilota Splash", Material.SLIME_BALL, DevelopmentState.Alpha));
		//Mapes.add(new ContenidorJoc(TempleQuest.class, "Temple Quest", Material.QUARTZ_BLOCK, DevelopmentState.InDevelopment));
		Mapes.add(new ContenidorJoc(OneInTheChamber.class, "OneInTheChamber", Material.BOW, DevelopmentState.Alpha));
		Mapes.add(new ContenidorJoc(BedWars.class, "Bed Wars", Material.BED, DevelopmentState.InDevelopment));
	}
	public void queryAutoRatings() {
		auto_ratings = Com.getDataAPI().getAutoRating();
		Mapes.sort((m1, m2) -> Double.compare(m2.getRating(), m1.getRating()));
	}
	public void ObrirMenuMapes(Player ply){
		queryAutoRatings();
		IconMenu menu = new IconMenu(ChatColor.RED + "Tots els mapes", (int) (9 * (Math.ceil(Mapes.size() / 9) + 1)), event -> {

			event.setWillClose(false);
            int pos = event.getPosition();
            ContenidorMapa cont = Mapes.get(pos);
            cont.playerClick(event.getPlayer());

        });

		for(ContenidorMapa mapa : Mapes){

			int count = 1;
			if(mapa.getPlayerAmount() > 0 ) count = mapa.getPlayerAmount();

			ItemStack icon = new ItemStack(mapa.mat, count);

			menu.setOption(Mapes.indexOf(mapa), icon, mapa.getDisplayName(), mapa.getDescription());

		}


		menu.open(ply);
	}
	public ArrayList<Mapa> getAllInstances(){
		ArrayList<Mapa> all = new ArrayList<>();
		for (ContenidorMapa c : Mapes){
			if (c instanceof ContenidorJoc){
				ContenidorJoc contenidorJoc = (ContenidorJoc) c;
				all.addAll(contenidorJoc.getInstàncies());
			}

		}
		return all;
	}
	public Mapa getMapWherePlayerIs(Player p){
		if (lobby.isOnLobby(p)){return null;}
		for(Mapa m : getAllInstances()){
			if(m.getWorld().getPlayers().contains(p)){
				return m;
			}
		}
		//Bukkit.broadcastMessage("Jugador desaparegut");
		return null;
	}
	public abstract class ContenidorMapa implements Listener{
		public lobby plugin;

		Class<?> ClassMapa;
		String nom;
		Material mat;
		public String getNom() {
			return nom;
		}
		public ArrayList<String> getDescription(){
			ArrayList<String> l = new ArrayList<>();
			int playerAmount = getPlayerAmount();
			if(playerAmount > 0)l.add(ChatColor.GREEN + "" + playerAmount + " jugador" + (playerAmount > 1 ? "s" : ""));
			return l;
		}
		public double getRating(){
			return 0;
		}
		public abstract int getPlayerAmount();
		public abstract int getMapCount();

		public String getDisplayName() {

			if(this.getMapCount() == 0) {
				return ChatColor.STRIKETHROUGH + "" + ChatColor.BOLD + nom + ChatColor.RESET;
			}

			return ChatColor.BOLD + nom + ChatColor.RESET;

		}
		public void setNom(String nom) {
			this.nom = nom;
		}


		public ContenidorMapa(Class<?> classMapa, String nom, Material mat) {
			plugin = lobby.getPlugin();
			plugin.getServer().getPluginManager().registerEvents(this, plugin);
			ClassMapa = classMapa;
			this.nom = nom;
			this.mat = mat;

		}
		abstract void playerClick(Player ply);

	}
	public enum DevelopmentState {NotWorking, KnownIssues, InDevelopment, PreAlpha, Alpha, Beta, Release}
	public class ContenidorJoc extends ContenidorMapa{
		public ContenidorJoc(Class<?> classMapa, String nom, Material mat, DevelopmentState s) {
			super(classMapa, nom, mat);
			// TODO Auto-generated constructor stub
			developmentState = s;
		}
		ArrayList<Joc> Instàncies = new ArrayList<>();

		DevelopmentState developmentState = DevelopmentState.Release;
		public DevelopmentState getDevelopmentState() {
			return developmentState;
		}
		public void setDevelopmentState(DevelopmentState developmentState) {
			this.developmentState = developmentState;
		}
		public String getDevelopmentString(){
			switch(developmentState){
			case InDevelopment:
				return ChatColor.GREEN + "[En desenvolupament]";
			case Alpha:
				return ChatColor.DARK_RED + "[Alpha]";
			case Beta:
				return ChatColor.GOLD + "[Beta]";
			case NotWorking:
				return ChatColor.STRIKETHROUGH + "" + ChatColor.RED + "[No Funciona]";
			case KnownIssues:
				return ChatColor.RED + "[Errors coneguts]";
			case PreAlpha:
				return ChatColor.RED + "[Pre-Alpha]";
			case Release:
				return "";
			default:
				break;
			}
			return "";
		}
		@Override
		public ArrayList<String> getDescription() {

			ArrayList<String> l = super.getDescription();
			l.add(0, getRatingString() + ChatColor.DARK_GRAY + " (" + Math.round(getRating() * 10D) / 10D + "%)");

			if(this.getMapCount() == 0) {
				l.add(1, ChatColor.RED + "No hi ha mapes disponibles");
			}

			return l;
		}
		@Override
		public int getPlayerAmount() {
			// TODO Auto-generated method stub
			return Instàncies.stream().mapToInt(j -> j.getPlayers().size()).sum();
		}

		@Override
		public int getMapCount() {

			Joc tempInstance = getTempInstance();
			return tempInstance.getMultiWorldList().size();

		}

		@Override
		void playerClick(Player ply) {
			// TODO Auto-generated method stub
			ObrirMenu(ply);
		}
		@Override
		public String getDisplayName() {
			// TODO Auto-generated method stub
			return super.getDisplayName() + " " + getDevelopmentString();
		}
		public Joc getTempInstance(){
			try {
				return (Joc) ClassMapa.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		public Joc addMap(Integer map){
			try {
				Joc newInstance = (Joc) ClassMapa.newInstance();
				if(map != null){
					newInstance.setMultiMapId(map);
				}
				newInstance.initialize();
				Instàncies.add(newInstance);
				return newInstance;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Bukkit.broadcastMessage("Ha fallat la creaciò d'una instància: " + nom);
				Bukkit.broadcastMessage("Error: " + e.getMessage());
				Bukkit.broadcastMessage("ErrorType: " + e.getClass().getName());
				if (e instanceof InvocationTargetException){
					InvocationTargetException ex = (InvocationTargetException) e;
					Bukkit.broadcastMessage("Message: " + ex.getTargetException().getMessage());
					Bukkit.broadcastMessage("String: " + ex.getTargetException().toString());
				}
				return null;
			}
		}
		Boolean canAutoJoin(){
			//if (AlgunMapaDisponible() == false){return false;}
			return Instàncies.size() <= 1;

		}
		void autoJoin(Player ply){
			if  (canAutoJoin()){
				if(Instàncies.size() == 0){
					addMap(null);
				}
				Instàncies.get(0).Join(ply);
			}

		}
		Boolean AlgunMapaDisponible(){
			for(Joc map : Instàncies){
				if (map.getGameState() == Joc.GameState.WaitingForPlayers){
					return true;
				}
			}
			return false;
		}
		Joc getInstànciaFromWorld(World world){
			for(Joc map : Instàncies){
				if (map.world.getName().equals(world.getName())){
					return map;
				}
			}
			return null;
		}
		@EventHandler
		public void onPlayerChangedWorld(PlayerChangedWorldEvent evt) {

			Player ply = evt.getPlayer();
			Joc map = getInstànciaFromWorld(evt.getFrom());

			if (map != null) {

				checkNecessary(map);
			} 
		}

		void checkNecessary(Joc map){
			if (map.getWorld() == null){ Bukkit.broadcastMessage("WORLD: null");return;}

			if(map.getEditMode())return;

			if (map.getWorld().getPlayers().size() == 0){
				//map.JocFinalitzat();
				map.clearExternals();
				map.deleteVirtualWorld();
				Instàncies.remove(map);
				map.destroyEventBus();
				System.gc();
				//map = null;
				//Bukkit.broadcastMessage("Mapa esborrat!");
			}
		}
		public DyeColor getGameColor(Joc joc){
			switch(joc.getGameState()){
			case InGame:
				return DyeColor.RED;
			case Preparing:
				return DyeColor.YELLOW;
			case WaitingForPlayers:
				return DyeColor.GREEN;
			case Complete:
				return DyeColor.PURPLE;
			case Resetejant:
				return DyeColor.GRAY;
			case Editant:
				return DyeColor.BLUE;
			default:
				break;
			}
			return DyeColor.WHITE;
		}
		public double getRating(){
			Joc tempInstance = getTempInstance();
			return Math.sqrt(auto_ratings.stream().filter(p -> p.getFirst().equals(tempInstance.getGameName())).mapToDouble(Pair::getSecond).findAny().orElse(0) / 100D) * 100D;
		}
		public String getRatingString(){
			Character c = '\u272E';
			int n = 10;
			double rating = getRating();
			int colorPoint = (int) Math.round(rating / n);
			String r = "";
			ChatColor tcolor;
			tcolor = ChatColor.GOLD;
			if(rating >= 75)tcolor = ChatColor.AQUA;
			r += tcolor; 
			for (int i = 0; i < n; i++) {
				if(i == colorPoint){r += ChatColor.GRAY;}
				r += c;
			}
			return r ;
		}
		public void ObrirMenu(Player ply){
			Joc tempInstance = getTempInstance();
			IconMenu menu = new IconMenu("Instàncies disponibles", 27, event -> {

                event.setWillClose(false);
                Joc tempInstance1 = getTempInstance();

                int pos = event.getPosition();
                MapMode m = tempInstance1.getMapMode();

                if (pos < (m == MapMode.MULTIPLE ? 27 - tempInstance1.getMultiWorldList().size() : 26)){
                    Joc map = Instàncies.get(pos); /*Open*/
                    map.Join(event.getPlayer());
                } else {
                    Joc nouMapa = addMap(m == MapMode.MULTIPLE ? 26 - event.getPosition() : null); /*New*/
                    nouMapa.Join(event.getPlayer());
                }

            });

			if(this.getMapCount() == 0) {

				String msg = ChatColor.RED + "" + ChatColor.ITALIC + "No hi ha mapes disponibles";
				BountifulAPI.sendActionBar(ply, msg, 150);
				ply.playSound(ply.getLocation(), Sound.ENTITY_VILLAGER_NO, 100.0F, 1.0F);
				return;

			}

			for(Joc mapa : Instàncies){
				Wool wool = new Wool(getGameColor(mapa));
				ItemStack stack = wool.toItemStack();
				stack.setAmount(mapa.getPlayers().size());
				String tStr = new SimpleDateFormat("mm:ss").format(new Date(mapa.tempsTranscorregut()));//Integer.toString(mapa.segonsTranscorreguts());
				double gameProgressETA = mapa.getGameProgressETA();
				String progressStr = ChatColor.AQUA + "Progrés: " + Math.round(gameProgressETA * 1000) / 10 + "% ETA";
				menu.setOption(Instàncies.indexOf(mapa), stack, mapa.getGameName(),ChatColor.WHITE + mapa.NomWorld, ChatColor.WHITE + mapa.getGameState().name(), ChatColor.GREEN + "Jugadors: " + Integer.toString(mapa.getPlayers().size()), ChatColor.YELLOW + "Espectadors:" + mapa.getSpectators().size(), "Temps: " + tStr, progressStr);
			}
			MapMode mapMode = tempInstance.getMapMode();
			if (!AlgunMapaDisponible() || mapMode == MapMode.MULTIPLE){
				if(mapMode == MapMode.SINGLE)menu.setOption(26, new ItemStack(Material.EMERALD, 1), ChatColor.GREEN + "Afegeix", ChatColor.WHITE + "Crea una nova instància");
				if(mapMode == MapMode.MULTIPLE){
					ArrayList<String> multiWorldList = tempInstance.getMultiWorldList();
					for (int i = 0; i < multiWorldList.size(); i++) {
						String name = multiWorldList.get(i);
						menu.setOption(26 - i, new ItemStack(Material.EMERALD, 1),
								ChatColor.GREEN + name, ChatColor.WHITE
								+ "Crear una nova instància");
					}
				}
			}

			menu.open(ply);
		}

		public ArrayList<Joc> getInstàncies() {
			return Instàncies;
		}
		public void setInstàncies(ArrayList<Joc> instàncies) {
			Instàncies = instàncies;
		}

	}

	public void openAllGamesMenu(Player ply){
		IconMenu menu = new IconMenu("Tots els jocs", 27, event -> {

            event.setWillClose(true);
            List<Joc> allInstances = getGames();

            int pos = event.getPosition();
            Joc joc = allInstances.get(pos);
            joc.Join(event.getPlayer());
        });
		List<Joc> games = getGames();
		for(Joc mapa : games){
			Wool wool = new Wool(DyeColor.BLACK);
			ItemStack stack = wool.toItemStack();
			stack.setAmount(mapa.getPlayers().size());
			String tStr = new SimpleDateFormat("mm:ss").format(new Date(mapa.tempsTranscorregut()));//Integer.toString(mapa.segonsTranscorreguts());
			menu.setOption(games.indexOf(mapa), stack, mapa.getGameName(),ChatColor.WHITE + mapa.getGameName() + " (" + mapa.NomWorld + ")", ChatColor.WHITE + mapa.getGameState().name(), ChatColor.GREEN + "Jugadors: " + Integer.toString(mapa.getPlayers().size()), ChatColor.YELLOW + "Espectadors:" + mapa.getSpectators().size(), "Temps: " + tStr);
		}	

		menu.open(ply);
	}
	//	public ItemStack getIconForInstance(Mapa m){
	//		ItemStack stack = new ItemStack(Material.WOOD)
	//		if (m instanceof Joc){
	//			Joc j = (Joc) m;
	//			Wool wool = new Wool(getGameColor(j));
	//			stack = wool.toItemStack();
	//			stack.setAmount(mapa.getPlayers().size());
	//			String tStr = new SimpleDateFormat("mm:ss").format(new Date(mapa.tempsTranscorregut()));//Integer.toString(mapa.segonsTranscorreguts());
	//			menu.setOption(Instàncies.indexOf(j), stack, j.getMapName(),ChatColor.WHITE + j.NomWorld, ChatColor.WHITE + j.getGameState().name(), ChatColor.GREEN + "Jugadors: " + Integer.toString(j.getPlayers().size()), ChatColor.YELLOW + "Espectadors:" + j.getSpectators().size(), "Temps: " + tStr);
	//		
	//		}
	//	}
	public List<Joc> getGames() {
		return getAllInstances().stream().filter(m -> m instanceof Joc).map(m -> (Joc)m).collect(Collectors.toList());
	}
}
