package com.solvd.logging;

import java.io.Serializable;
import java.util.function.Function;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Core;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.solvd.domain.LogDTO;
import com.solvd.utils.AgentFileNotFound;

@Plugin(name = "ReportingAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public final class ReportingAppender extends AbstractAppender {

	private static final Function<LogEvent, LogDTO> CONVERTER = e -> new LogDTO(e.getMessage().getFormattedMessage(),
			e.getLevel().toString(), e.getTimeMillis());
	private static volatile LogsBuffer logsBuffer;

	protected ReportingAppender(String name, Filter filter, Layout<? extends Serializable> layout,
			boolean ignoreExceptions) {
		super(name, filter, layout, ignoreExceptions, Property.EMPTY_ARRAY);
	}

	@PluginFactory
	public static ReportingAppender create(@PluginAttribute("name") String name,
			@PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filter") Filter filter) {

		if (name == null) {
			LOGGER.error("No name provided for TestLoggerAppender");
			return null;
		}

		if (layout == null) {
			layout = PatternLayout.createDefaultLayout();
		}

		return new ReportingAppender(name, filter, layout, true);
	}

	@Override
	public void append(LogEvent event) {
		getBuffer().put(event);
	}

	private static LogsBuffer getBuffer() {
		if (logsBuffer == null) {
			synchronized (ReportingAppender.class) {
				if (logsBuffer == null) {
					try {
						logsBuffer = new LogsBuffer(CONVERTER);
					} catch (AgentFileNotFound e) {
						e.printStackTrace();
					}
				}
			}
		}
		return logsBuffer;
	}

}
