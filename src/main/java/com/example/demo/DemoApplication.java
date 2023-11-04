package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;


@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	final int THRESHOLD = 10; // Example threshold
	LoadingCache<String, Integer> userMap = CacheBuilder.newBuilder()
			.build(
					new CacheLoader<String, Integer>() {
						public Integer load(String key) {
							return 0; // Default load implementation
						}
					});
	/**
	 * Increments the value associated with the key by 1.
	 * If the value exceeds the threshold, it resets to 0.
	 * @param key the key to increment the value for
	 * @return true if the threshold was exceeded and the value was reset, otherwise false
	 */
	public boolean incrementAndCheck(String key) {
		return userMap.asMap().compute(key, (k, currentValue) -> {
			// Check if current value is null, which shouldn't happen with a loading cache
			if (currentValue == null) {
				System.out.println("value was null");
				currentValue = 0;
			}

			// If the current value exceeds the threshold
			if (currentValue >= THRESHOLD) {
				// Reset to 0 and return true to indicate the threshold was exceeded
				return 0;
			} else {
				// Otherwise, increment the value
				return currentValue + 1;
			}
		}) == 0; // If the new value is 0, the threshold was exceeded
	}


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("ciao");
		System.out.println("BEFORE AAA " + userMap.asMap());
		incrementAndCheck("AAA");
		System.out.println("AFTER AAA 1 " + userMap.asMap());
		incrementAndCheck("AAA");
		System.out.println("AFTER AAA 2 " + userMap.asMap());

	}
}
