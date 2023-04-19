package io.github.miniplaceholders.expansion.spark.common;

import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;

import java.util.function.BiFunction;

public interface SparkPlaceholder extends BiFunction<ArgumentQueue, Context, Tag> {
}
