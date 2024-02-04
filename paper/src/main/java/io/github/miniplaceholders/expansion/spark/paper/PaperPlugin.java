package io.github.miniplaceholders.expansion.spark.paper;

import io.github.miniplaceholders.expansion.spark.common.CommonExpansion;
import me.lucko.spark.api.Spark;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getSLF4JLogger().info("Starting Spark Expansion for Paper");
        final RegisteredServiceProvider<Spark> provider = getServer().getServicesManager().getRegistration(Spark.class);
        assert provider != null;
        final Spark spark = provider.getProvider();
        CommonExpansion.register(spark);
        getSLF4JLogger().info("Started Spark Expansion");

    }
}
