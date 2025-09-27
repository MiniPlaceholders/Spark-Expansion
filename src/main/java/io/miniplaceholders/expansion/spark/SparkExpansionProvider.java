package io.miniplaceholders.expansion.spark;

import io.github.miniplaceholders.api.Expansion;
import io.github.miniplaceholders.api.provider.ExpansionProvider;
import io.github.miniplaceholders.api.provider.LoadRequirement;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;

public final class SparkExpansionProvider implements ExpansionProvider {

    @Override
    public Expansion provideExpansion() {
    // Do not call SparkProvider.get() here; placeholders obtain Spark lazily when needed.
    return Expansion.builder("spark")
        .version("2.0.0")
        .author("MiniPlaceholders Contributors")
        .globalPlaceholder("tps", new TPSPlaceholder())
        .globalPlaceholder("tickduration", new MSPTPlaceholder())
        .globalPlaceholder("cpu_system", new CPUSystemPlaceholder())
        .globalPlaceholder("cpu_process", new CPUProcessPlaceholder())
        .build();
    }

    @Override
    public LoadRequirement loadRequirement() {
        // Do not require spark at load time; handle absence at runtime in provideExpansion()
        return LoadRequirement.none();
    }
}
