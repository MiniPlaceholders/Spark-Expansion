package io.github.miniplaceholders.expansion.spark.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import io.github.miniplaceholders.expansion.spark.common.CommonExpansion;
import org.slf4j.Logger;

@Plugin(
    name = "Example-Expansion",
    id = "example-expansion",
    version = Constants.VERSION,
    authors = {"4drian3d"},
    dependencies = {
        @Dependency(id = "miniplaceholders"),
        @Dependency(id = "spark")
    }
)
public final class VelocityPlugin {
    private final Logger logger;

    @Inject
    public VelocityPlugin(Logger logger) {
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        logger.info("Starting Example Expansion for Velocity");
        CommonExpansion.register();
    }
}
