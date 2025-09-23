package io.github.miniplaceholders.expansion.spark;

import io.github.miniplaceholders.api.resolver.GlobalTagResolver;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import static net.kyori.adventure.text.format.NamedTextColor.*;

record TPSPlaceholder(Spark spark) implements GlobalTagResolver {

    @Override
    public Tag tag(ArgumentQueue argumentQueue, Context context) {
        final DoubleStatistic<StatisticWindow.TicksPerSecond> statistics = spark.tps();
        if (statistics == null) {
            return null;
        }
        if (!argumentQueue.hasNext()) {
            final double[] tps = statistics.poll();
            final Component component = MiniMessage.miniMessage().deserialize(
                    "<tps5s> <gray>|</gray> "
                            + "<tps10s> <gray>|</gray> "
                            + "<tps1m> <gray>|</gray> "
                            + "<tps5m> <gray>|</gray> "
                            + "<tps15m>",
                    Placeholder.component("tps5s", formatTps(tps[0])),
                    Placeholder.component("tps10s", formatTps(tps[1])),
                    Placeholder.component("tps1m", formatTps(tps[2])),
                    Placeholder.component("tps5m", formatTps(tps[3])),
                    Placeholder.component("tps15m", formatTps(tps[4]))
            );
            return Tag.selfClosingInserting(component);
        }
        final String time = argumentQueue.pop().lowerValue();
        final StatisticWindow.TicksPerSecond tps = switch (time) {
            case "5s" -> StatisticWindow.TicksPerSecond.SECONDS_5;
            case "10s" -> StatisticWindow.TicksPerSecond.SECONDS_10;
            case "1m" -> StatisticWindow.TicksPerSecond.MINUTES_1;
            case "5m" -> StatisticWindow.TicksPerSecond.MINUTES_5;
            case "15m" -> StatisticWindow.TicksPerSecond.MINUTES_15;
            default -> throw context.newException("Invalid argument provided");
        };
        return Tag.selfClosingInserting(formatTps(statistics.poll(tps)));
    }

    private Component formatTps(double tps) {
        final NamedTextColor color;
        if (tps > 18.0) {
            color = GREEN;
        } else if (tps > 16.0) {
            color = YELLOW;
        } else {
            color = RED;
        }
        return Component.text(tps, color);
    }
}
