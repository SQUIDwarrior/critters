/*
 * Copyright (c) 2013 Mike Deats
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Created on May 4, 2013
 */
package critters.util;

import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

public class PerformanceMonitor {
	private int availableProcessors = 1;
	private long lastSystemTime = 0;
	private long lastProcessCpuTime = 0;
	private OperatingSystemMXBean osBean;
	private volatile static PerformanceMonitor monitor = null;

	private PerformanceMonitor() {
		osBean = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();

		availableProcessors = osBean.getAvailableProcessors();
	}

	public static PerformanceMonitor getInstance() {
		if (monitor == null) {
			synchronized (PerformanceMonitor.class) {
				monitor = new PerformanceMonitor();
			}
		}

		return monitor;
	}

	public synchronized double getCpuUsage() {
		if (lastSystemTime == 0) {
			baselineCounters();
			return 0.0;
		}

		long systemTime = System.nanoTime();
		long processCpuTime = 0;

		processCpuTime = osBean.getProcessCpuTime();

		double cpuUsage = (double) (processCpuTime - lastProcessCpuTime)
				/ (systemTime - lastSystemTime);

		lastSystemTime = systemTime;
		lastProcessCpuTime = processCpuTime;

		return cpuUsage;
	}

	private void baselineCounters() {
		lastSystemTime = System.nanoTime();

		lastProcessCpuTime = osBean.getProcessCpuTime();
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		while(true)
		{
			System.out.printf("cpu=%.4f\n", (PerformanceMonitor.getInstance().getCpuUsage() * 100.0));
			Thread.sleep(1000);
		}
	}
}

