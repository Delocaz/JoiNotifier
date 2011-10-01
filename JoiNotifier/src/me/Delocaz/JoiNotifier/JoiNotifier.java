package me.Delocaz.JoiNotifier;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class JoiNotifier extends JavaPlugin {
	public void onEnable() {
		try {
			play(new URL("http://translate.google.com/translate_tts?tl=en&q=" + URLEncoder.encode("Hi there! JoiNotifire has been enabled!", "UTF-8")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, new PlayerListener() {
			public void onPlayerJoin(PlayerJoinEvent e) {
				try {
					JoiNotifier.play(new URL("http://translate.google.com/translate_tts?tl=en&q=" + e.getPlayer().getName() + URLEncoder.encode(" joined your Minecraft server! Check it out!", "UTF-8")));
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}, Event.Priority.Monitor, this);
	}
	public void onDisable() {
		
	}
	protected static Player play(URL i) {
		java.net.URLConnection c = null;
		try {
			c = i.openConnection();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		c.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.815.0 Safari/535.1");
		InputStream in = null;
		try {
			in = c.getInputStream();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		final Player player;

		try {
			player = new Player(in);
		} catch (JavaLayerException e) {
			System.err.println(e);
			System.exit(1);
			return null;
		}

		new Thread() {
			public void run() {
				try {
					player.play();
				} catch (JavaLayerException e) {
					System.err.println(e);
					System.exit(1);
				}
			}
		}.start();

		return player;
	}

}
