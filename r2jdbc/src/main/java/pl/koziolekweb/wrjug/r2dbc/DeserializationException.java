package pl.koziolekweb.wrjug.r2dbc;

import lombok.Getter;

@Getter
public class DeserializationException extends Exception {
	private final NotificationTopic topic;

	public DeserializationException(NotificationTopic topic, Exception e) {
		super(e);
		this.topic = topic;
	}
}
