package io.miniplaceholders.expansion.spark;

import io.github.miniplaceholders.api.resolver.GlobalTagResolver;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class CPUSystemPlaceholder implements GlobalTagResolver {
    @Override
    public Tag tag(ArgumentQueue argumentQueue, Context context) {
        final DoubleStatistic<StatisticWindow.CpuUsage> cpuUsage;
        try {
            cpuUsage = SparkProvider.get().cpuSystem();
        } catch (IllegalStateException e) {
            return null;
        }

        if (!argumentQueue.hasNext()) {
            final double[] usage = cpuUsage.poll();
            final Component component = MiniMessage.miniMessage().deserialize(
                    "<usage10s> <gray>|</gray> "
                            + "<usage1m> <gray>|</gray> "
                            + "<usage15m>",
                    Placeholder.component("usage10s", Component.text(usage[0])),
                    Placeholder.component("usage1m", Component.text(usage[1])),
                    Placeholder.component("usage15m", Component.text(usage[2]))
            );
            return Tag.selfClosingInserting(component);
        }

        final String time = argumentQueue.pop().lowerValue();
        final StatisticWindow.CpuUsage usageType = switch (time) {
            case "10s" -> StatisticWindow.CpuUsage.SECONDS_10;
            case "1m" -> StatisticWindow.CpuUsage.MINUTES_1;
            case "15m" -> StatisticWindow.CpuUsage.MINUTES_15;
            default -> throw context.newException("");
        };
        return Tag.selfClosingInserting(Component.text(cpuUsage.poll(usageType)));
    }
}
