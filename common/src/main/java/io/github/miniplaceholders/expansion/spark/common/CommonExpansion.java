package io.github.miniplaceholders.expansion.spark.common;

import io.github.miniplaceholders.api.Expansion;
import me.lucko.spark.api.Spark;

public final class CommonExpansion {
    public static void register(Spark spark) {
        Expansion.builder("spark")
                .globalPlaceholder("tps", new TPSPlaceholder(spark))
                .globalPlaceholder("tickduration", new MSPTPlaceholder(spark))
                .globalPlaceholder("cpu_system", new CPUSystemPlaceholder(spark))
                .globalPlaceholder("cpu_process", new CPUProcessPlaceholder(spark))
                .build()
                .register();
    }
}
