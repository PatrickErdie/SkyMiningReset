package me.patrickerdie.box;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.logging.Level;

public class Reset {

    public Reset() {
        LoadData data = new LoadData();
        Set<Mine> mines = data.getMines();
        for (Mine mine : mines) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    mine.reset();
                }
            }.runTaskTimer(Box.instance, 0, mine.getResetTime());
        }
    }

}
