package io.github.miniplaceholders.expansion.spark.common;

import io.github.miniplaceholders.api.Expansion;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import me.lucko.spark.api.statistic.types.GenericStatistic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import static net.kyori.adventure.text.format.NamedTextColor.*;

public class CommonExpansion {
    public static void register() {
        final Spark spark = SparkProvider.get();
        Expansion.builder("spark")
                .globalPlaceholder("tps", (queue, ctx) -> {
                    DoubleStatistic<StatisticWindow.TicksPerSecond> statistics = spark.tps();
                    if (statistics == null) {
                        return null;
                    }
                    if (!queue.hasNext()) {
                        final double[] tps = statistics.poll();
                        final Component component = MiniMessage.miniMessage().deserialize(
                                "<green><tps5s> <gray>|</gray> <tps10s> <gray>|</gray> <tps15s> <gray>|</gray> <tps1m> <gray>|</gray> <tps5m> <gray>|</gray> <tps15m>",
                                Placeholder.component("tps5s", formatTps(tps[0])),
                                Placeholder.component("tps10s", formatTps(tps[1])),
                                Placeholder.component("tps15s", formatTps(tps[2])),
                                Placeholder.component("tps1m", formatTps(tps[3])),
                                Placeholder.component("tps5m", formatTps(tps[4])),
                                Placeholder.component("tps15m", formatTps(tps[5]))
                        );
                        return Tag.selfClosingInserting(component);
                    }

                    final String time = queue.pop().value();
                    final StatisticWindow.TicksPerSecond tps = switch (time) {
                        case "5s" -> StatisticWindow.TicksPerSecond.SECONDS_5;
                        case "10s" -> StatisticWindow.TicksPerSecond.SECONDS_10;
                        case "1m" -> StatisticWindow.TicksPerSecond.MINUTES_1;
                        case "5m" -> StatisticWindow.TicksPerSecond.MINUTES_5;
                        case "15m" -> StatisticWindow.TicksPerSecond.MINUTES_15;
                    };
                    return Tag.selfClosingInserting(formatTps(statistics.poll(tps)));
                })
                .globalPlaceholder("mspt", (queue, ctx) -> {
                    final GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> stats = spark.mspt();
                    if (stats == null) {
                        return null;
                    }
                    if (!queue.hasNext()) {
                        final DoubleAverageInfo[] mspt = stats.poll();
                        final Component component = MiniMessage.miniMessage().deserialize(
                                "<green><mspt10s_max> <gray>|</gray> <mspt10s_min> <gray>|</gray> <mspt10s_av> <gray>|</gray> <mspt1m_max> <gray>|</gray> <mspt1m_min> <gray>|</gray> <mspt1m_av>",
                                Placeholder.component("mspt10s_max", formatTps(mspt[0].max())),
                                Placeholder.component("mspt10s_min", formatTps(mspt[0].min())),
                                Placeholder.component("mspt10s_av", formatTps(mspt[0].max())),
                                Placeholder.component("mspt1m_min", formatTps(mspt[1].min())),
                                Placeholder.component("mspt1m_max", formatTps(mspt[1].max())),
                                Placeholder.component("mspt1m_min", formatTps(mspt[1].min()))
                        );
                        return Tag.selfClosingInserting(component);
                    }

                    final String first = queue.pop().lowerValue();

                    if (first.equals("10s")) {
                        final DoubleAverageInfo averageInfo = stats.poll(StatisticWindow.MillisPerTick.SECONDS_10);
                        if (!queue.hasNext()) {
                            final Component component = MiniMessage.miniMessage().deserialize(
                                "<green><mspt10s_max> <gray>|</gray> <mspt10s_min> <gray>|</gray> <mspt10s_av>",
                                Placeholder.component("mspt10s_max", formatTps(averageInfo.max())),
                                Placeholder.component("mspt10s_min", formatTps(averageInfo.min())),
                                Placeholder.component("mspt10s_av", formatTps(averageInfo.max()))
                            );
                            return Tag.selfClosingInserting(component);
                        }
                        final String range = queue.pop().lowerValue();
                        final double mspt = switch (range) {
                            case "max" -> averageInfo.max();
                            case "min" -> averageInfo.min();
                            case "av" -> averageInfo.median();
                        };
                        return Tag.selfClosingInserting(formatMSPT(mspt));
                    } else if (first.equals("1m")) {
                        final DoubleAverageInfo averageInfo = stats.poll(StatisticWindow.MillisPerTick.MINUTES_1);
                        if (!queue.hasNext()) {
                            final Component component = MiniMessage.miniMessage().deserialize(
                                    "<green><mspt1m_max> <gray>|</gray> <mspt1m_min> <gray>|</gray> <mspt1m_av>",
                                    Placeholder.component("mspt10s_max", formatTps(averageInfo.max())),
                                    Placeholder.component("mspt10s_min", formatTps(averageInfo.min())),
                                    Placeholder.component("mspt10s_av", formatTps(averageInfo.max()))
                            );
                            return Tag.selfClosingInserting(component);
                        }
                        final String range = queue.pop().lowerValue();
                        final double mspt = switch (range) {
                            case "max" -> averageInfo.max();
                            case "min" -> averageInfo.min();
                            case "av" -> averageInfo.median();
                        };
                        return Tag.selfClosingInserting(formatMSPT(mspt));
                    } else {
                        return null;
                    }
                })
                .globalPlaceholder("cpu_system", (queue, ctx) -> {
                    DoubleStatistic<StatisticWindow.CpuUsage> cpuUsage = spark.cpuSystem();
                    return null;
                })
                .globalPlaceholder("cpu_process", (queue, ctx) -> null)
                .build()
                .register();
    }

    private static Component formatTps(double tps) {
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

    private static Component formatMSPT(double mspt) {
        final NamedTextColor color;
        if (mspt >= 50d) {
            color = RED;
        } else if (mspt >= 40d) {
            color = YELLOW;
        } else {
            color = GREEN;
        }
        return Component.text(mspt, color);
    }
}
