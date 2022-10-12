package com.solvd.logging;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

import org.apache.logging.log4j.core.LogEvent;

import com.solvd.ZebrunnerAPI;
import com.solvd.domain.LogDTO;
import com.solvd.utils.AgentFileNotFound;

public class LogsBuffer {

	
	private static ZebrunnerAPI API;

	private static volatile Queue<LogDTO> QUEUE = new ConcurrentLinkedQueue<>();
	private final Function<LogEvent, LogDTO> converter;

	public LogsBuffer(Function<LogEvent, LogDTO> converter) throws AgentFileNotFound {
		API = ZebrunnerAPI.getInstance();
		this.converter = converter;
	}

	public void put(LogEvent event) {
		LogDTO log = converter.apply(event);
		log.setTestId(API.getDATA().getTestId());
		QUEUE.add(log);

		if (!QUEUE.isEmpty()) {
			Queue<LogDTO> logsBatch = QUEUE;
			QUEUE = new ConcurrentLinkedQueue<>();
			API.sendLogs(logsBatch);
		}
	}

}
