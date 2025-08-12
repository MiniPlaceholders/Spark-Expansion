package io.github.miniplaceholders.expansion.spark.common;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.provider.ExpansionProvider;
import io.github.miniplaceholders.api.provider.LoadRequirement;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;

public final class SparkExpansionProvider implements ExpansionProvider {

    @Override
    public Expansion provideExpansion() {
        final Spark spark = SparkProvider.get();
        return Expansion.builder("spark")
                .version("2.0.0")
                .author("MiniPlaceholders Contributors")
                .globalPlaceholder("tps", new TPSPlaceholder(spark))
                .globalPlaceholder("tickduration", new MSPTPlaceholder(spark))
                .globalPlaceholder("cpu_system", new CPUSystemPlaceholder(spark))
                .globalPlaceholder("cpu_process", new CPUProcessPlaceholder(spark))
                .build();
    }

    @Override
    public LoadRequirement loadRequirement() {
        return LoadRequirement.requiredComplement("spark");
    }
}
