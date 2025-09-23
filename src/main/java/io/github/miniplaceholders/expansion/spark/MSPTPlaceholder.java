package io.github.miniplaceholders.expansion.spark;

import io.github.miniplaceholders.api.resolver.GlobalTagResolver;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;
import me.lucko.spark.api.statistic.types.GenericStatistic;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import static net.kyori.adventure.text.format.NamedTextColor.*;

record MSPTPlaceholder(Spark spark) implements GlobalTagResolver {
    @Override
    public Tag tag(ArgumentQueue argumentQueue, Context context) {
        final GenericStatistic<DoubleAverageInfo, StatisticWindow.MillisPerTick> stats = spark.mspt();
        if (stats == null) {
            return null;
        }
        if (!argumentQueue.hasNext()) {
            final DoubleAverageInfo[] mspt = stats.poll();
            final Component component = MiniMessage.miniMessage().deserialize(
                    "<green><mspt10s_max> <gray>|</gray> <mspt10s_min> <gray>|</gray> <mspt10s_av> <gray>|</gray> <mspt1m_max> <gray>|</gray> <mspt1m_min> <gray>|</gray> <mspt1m_av>",
                    Placeholder.component("mspt10s_max", formatMSPT(mspt[0].max())),
                    Placeholder.component("mspt10s_min", formatMSPT(mspt[0].min())),
                    Placeholder.component("mspt10s_av", formatMSPT(mspt[0].max())),
                    Placeholder.component("mspt1m_min", formatMSPT(mspt[1].min())),
                    Placeholder.component("mspt1m_max", formatMSPT(mspt[1].max())),
                    Placeholder.component("mspt1m_min", formatMSPT(mspt[1].min()))
            );
            return Tag.selfClosingInserting(component);
        }

        final String first = argumentQueue.pop().lowerValue();

        if (first.equals("10s")) {
            final DoubleAverageInfo averageInfo = stats.poll(StatisticWindow.MillisPerTick.SECONDS_10);
            return msptTag(argumentQueue, averageInfo, context, "10s");
        } else if (first.equals("1m")) {
            final DoubleAverageInfo averageInfo = stats.poll(StatisticWindow.MillisPerTick.MINUTES_1);
            return msptTag(argumentQueue, averageInfo, context, "1m");
        } else {
            return null;
        }
    }

    private Tag msptTag(ArgumentQueue queue, DoubleAverageInfo averageInfo, Context context, String id) {
        if (!queue.hasNext()) {
            final Component component = MiniMessage.miniMessage().deserialize(
                    "<green><mspt" + id + "_max> <gray>|</gray> <mspt" + id + "_min> <gray>|</gray> <mspt" + id + "_av>",
                    Placeholder.component("mspt" + id + "_max", formatMSPT(averageInfo.max())),
                    Placeholder.component("mspt" + id + "_min", formatMSPT(averageInfo.min())),
                    Placeholder.component("mspt" + id + "_av", formatMSPT(averageInfo.max()))
            );
            return Tag.selfClosingInserting(component);
        }
        final String range = queue.pop().lowerValue();
        final double mspt = switch (range) {
            case "max" -> averageInfo.max();
            case "min" -> averageInfo.min();
            case "av" -> averageInfo.median();
            default -> throw context.newException("");
        };
        return Tag.selfClosingInserting(formatMSPT(mspt));
    }

    private Component formatMSPT(double mspt) {
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
