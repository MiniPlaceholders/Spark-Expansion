package io.github.miniplaceholders.expansion.spark.common;

import me.lucko.spark.api.Spark;
import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;

final class CPUSystemPlaceholder implements SparkPlaceholder {
    private final Spark spark;
    CPUSystemPlaceholder(Spark spark) {
        this.spark = spark;
    }
    @Override
    public Tag apply(ArgumentQueue argumentQueue, Context context) {
        DoubleStatistic<StatisticWindow.CpuUsage> cpuUsage = spark.cpuSystem();
        return null;
    }
}
