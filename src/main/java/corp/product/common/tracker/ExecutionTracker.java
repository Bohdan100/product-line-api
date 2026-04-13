package corp.product.common.tracker;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import java.util.UUID;

@Component
@Scope("prototype")
public class ExecutionTracker {
    private final long startTime = System.currentTimeMillis();
    private final String transactionId = UUID.randomUUID().toString().substring(0, 8);

    public void logStep(String message) {
        long elapsed = System.currentTimeMillis() - startTime;
        System.out.printf("[TRX-%s] +%dms: %s%n", transactionId, elapsed, message);
    }

    public void finish(String operationName) {
        long totalTime = System.currentTimeMillis() - startTime;
        System.out.printf("[TRX-%s] COMPLETED: %s in %dms%n", transactionId, operationName, totalTime);
    }
}