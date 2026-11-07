package com.enderdevelopment.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class MaceCooldownListener implements Listener {
    private static final long FOLLOW_UP_WINDOW_TICKS = 100L;
    private static final long COOLDOWN_MILLIS = 60_000L;

    private final JavaPlugin plugin;
    private final Map<UUID, BukkitTask> pendingWindows = new HashMap<>();
    private final Map<UUID, Long> cooldownEndsAt = new HashMap<>();

    public MaceCooldownListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player) || !isMace(player.getInventory().getItemInMainHand())) {
            return;
        }

        UUID playerId = player.getUniqueId();
        long now = System.currentTimeMillis();
        Long cooldownEnd = cooldownEndsAt.get(playerId);
        if (cooldownEnd != null) {
            if (now < cooldownEnd) {
                event.setCancelled(true);
                return;
            }
            cooldownEndsAt.remove(playerId);
        }

        BukkitTask pendingWindow = pendingWindows.remove(playerId);
        if (pendingWindow != null) {
            pendingWindow.cancel();
            return;
        }

        BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            pendingWindows.remove(playerId);
            cooldownEndsAt.put(playerId, System.currentTimeMillis() + COOLDOWN_MILLIS);
        }, FOLLOW_UP_WINDOW_TICKS);
        pendingWindows.put(playerId, task);
    }

    private boolean isMace(ItemStack item) {
        return item != null && item.getType() == Material.MACE;
    }
}
