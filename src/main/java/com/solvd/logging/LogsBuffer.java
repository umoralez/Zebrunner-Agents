package com.solvd.logging;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;

import com.solvd.ZebrunnerAPI;
import com.solvd.domain.LogDTO;
import com.solvd.utils.AgentFileNotFound;

public class LogsBuffer {

    private static final Logger LOGGER = LogManager.getLogger(LogsBuffer.class);
    private static final ScheduledExecutorService FLUSH_EXECUTOR = Executors.newScheduledThreadPool(4);
    private static ZebrunnerAPI API;
    private static final AtomicBoolean EXECUTOR_ENABLED = new AtomicBoolean();

    private static volatile Queue<LogDTO> QUEUE = new ConcurrentLinkedQueue<>();
    private final Function<LogEvent, LogDTO> converter;

    public LogsBuffer(Function<LogEvent, LogDTO> converter) throws AgentFileNotFound {
        API = ZebrunnerAPI.getInstance();
        this.converter = converter;
        Runtime.getRuntime().addShutdownHook(new Thread(LogsBuffer::shutdown));
    }

    public void put(LogEvent event) {
        LogDTO log = converter.apply(event);
        log.setTestId(API.getDATA().getTestId());
        QUEUE.add(log);

        if (EXECUTOR_ENABLED.compareAndSet(false, true)) {
            scheduleFlush();
        }
    }

    private static void scheduleFlush() {
        FLUSH_EXECUTOR.scheduleWithFixedDelay(LogsBuffer::flush, 1, 1, TimeUnit.SECONDS);
    }

    private static void flush() {
        if (!QUEUE.isEmpty()) {
            Queue<LogDTO> logsBatch = QUEUE;
            QUEUE = new ConcurrentLinkedQueue<>();
            API.sendLogs(logsBatch);
        }
    }

    private static void shutdown() {
        FLUSH_EXECUTOR.shutdown();
        try {
            if (!FLUSH_EXECUTOR.awaitTermination(10, TimeUnit.SECONDS)) {
                FLUSH_EXECUTOR.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }

        flush();
    }
}
