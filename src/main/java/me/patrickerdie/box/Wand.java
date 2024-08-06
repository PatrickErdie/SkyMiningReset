package me.patrickerdie.box;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class Wand implements Listener {

    public static final ItemStack wand = createWand();

    private static ItemStack createWand() {
        ItemStack wand = new ItemStack(Material.GOLDEN_AXE);
        ItemMeta meta = wand.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "BoundMaker");
            meta.setUnbreakable(true);
            meta.addItemFlags(
                    ItemFlag.HIDE_ATTRIBUTES,
                    ItemFlag.HIDE_ADDITIONAL_TOOLTIP,
                    ItemFlag.HIDE_ARMOR_TRIM,
                    ItemFlag.HIDE_DESTROYS,
                    ItemFlag.HIDE_DYE,
                    ItemFlag.HIDE_ENCHANTS,
                    ItemFlag.HIDE_PLACED_ON,
                    ItemFlag.HIDE_UNBREAKABLE);
            List<String> lore = Arrays.asList(" ", ChatColor.GOLD + "Right " + ChatColor.GRAY + "click for position 1", ChatColor.GOLD + "Left " + ChatColor.GRAY + "click for position 2");
            meta.setLore(lore);
            wand.setItemMeta(meta);
        }
        return wand;
    }

    public static void getWand(Player player) {
        player.getInventory().addItem(wand);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getItem() == null) return;
        ItemStack stack = event.getItem();
        if (stack.getItemMeta().getDisplayName().equals(Wand.wand.getItemMeta().getDisplayName())) {
            Location location = event.getClickedBlock() != null ? event.getClickedBlock().getLocation() : null;
            if (location != null) {
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    event.setCancelled(true);
                    Box.setLocation2(player, location);
                } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    event.setCancelled(true);
                    Box.setLocation1(player, location);
                }
            }
        }
    }
}
