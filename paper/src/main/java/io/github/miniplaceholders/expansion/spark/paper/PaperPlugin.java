package io.github.miniplaceholders.expansion.spark.paper;

import io.github.miniplaceholders.expansion.spark.common.CommonExpansion;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperPlugin extends JavaPlugin {

    @Override
    public void onEnable(){
        this.getSLF4JLogger().info("Starting Spark Expansion for Paper");
        CommonExpansion.register();
    }
}
