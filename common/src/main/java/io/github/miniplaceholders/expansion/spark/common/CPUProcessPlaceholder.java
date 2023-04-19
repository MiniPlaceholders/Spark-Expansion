package io.github.miniplaceholders.expansion.spark.common;

import me.lucko.spark.api.Spark;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;

final class CPUProcessPlaceholder implements SparkPlaceholder {
    private final Spark spark;
    CPUProcessPlaceholder(Spark spark) {
        this.spark = spark;
    }
    @Override
    public Tag apply(ArgumentQueue argumentQueue, Context context) {
        return null;
    }
}
