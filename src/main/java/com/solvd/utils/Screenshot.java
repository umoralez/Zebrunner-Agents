package com.solvd.utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Screenshot {
	private static final Logger LOGGER = LogManager.getLogger(Screenshot.class);

	private byte[] content;
	private String timeData;

	public void takeScreenshot() {
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		String timeString = Long.toString(Instant.now().toEpochMilli());

		File imageFile = new File("./target/screenshot" + timeString + ".png");
		try {
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ImageIO.write(capture, "png", imageFile);

			this.content = Files.readAllBytes(Paths.get(imageFile.getAbsolutePath()));
			this.timeData = timeString;
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			LOGGER.error(e.getStackTrace());
		}

	}

	public byte[] getContent() {
		return content;
	}

	public String getTimeData() {
		return timeData;
	}

}
