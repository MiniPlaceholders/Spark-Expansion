package io.github.miniplaceholders.expansion.spark.common;

import me.lucko.spark.api.Spark;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

final class CPUProcessPlaceholder implements SparkPlaceholder {
    private final Spark spark;
    CPUProcessPlaceholder(Spark spark) {
        this.spark = spark;
    }
    @Override
    public Tag apply(ArgumentQueue argumentQueue, Context context) {
        final DoubleStatistic<StatisticWindow.CpuUsage> cpuUsage = spark.cpuProcess();
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
