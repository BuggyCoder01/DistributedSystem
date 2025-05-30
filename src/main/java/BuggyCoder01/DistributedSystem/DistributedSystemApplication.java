package BuggyCoder01.DistributedSystem;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.builder.SpringApplicationBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(scanBasePackages = {
		"BuggyCoder01.DistributedSystem.common",
		"BuggyCoder01.DistributedSystem.coordinator",
		"BuggyCoder01.DistributedSystem.node"
})
public class DistributedSystemApplication {

	public static void main(String[] args) {
		// بدء المنسق (Coordinator) في الخيط الرئيسي
		ConfigurableApplicationContext coordinatorContext = startCoordinator(args);

		// بدء العقد (Nodes) في خيوط منفصلة
		ExecutorService nodeExecutor = Executors.newFixedThreadPool(3);
		startNode(nodeExecutor, "node1", args);
		startNode(nodeExecutor, "node2", args);
		startNode(nodeExecutor, "node3", args);

		// تسجيل خطاف لإيقاف التطبيق بشكل أنيق
		registerShutdownHook(coordinatorContext, nodeExecutor);
	}

	private static ConfigurableApplicationContext startCoordinator(String[] args) {
		System.out.println("Starting Coordinator on port 8080...");
		return new SpringApplicationBuilder(DistributedSystemApplication.class)
				.profiles("coordinator")
				.run(args);
	}

	private static void startNode(ExecutorService executor, String nodeProfile, String[] args) {
		executor.submit(() -> {
			System.out.println("Starting Node [" + nodeProfile + "]...");
			new SpringApplicationBuilder(DistributedSystemApplication.class)
					.profiles("node", nodeProfile)
					.run(args);
		});
	}

	private static void registerShutdownHook(
			ConfigurableApplicationContext coordinatorContext,
			ExecutorService executor
	) {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("Shutting down application...");

			// إيقاف سياق المنسق
			if (coordinatorContext != null && coordinatorContext.isRunning()) {
				coordinatorContext.close();
			}

			// إيقاف العقد
			executor.shutdownNow();

			System.out.println("Application shutdown complete.");
		}));
	}
}
