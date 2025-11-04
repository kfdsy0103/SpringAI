package piu.springAI.service.chapter04.dto;

import lombok.Getter;

@Getter
public class ReviewClassification {
	public enum Sentiment {
		POSITIVE, NEUTRAL, NEGATIVE;
	}

	private String review;
	private Sentiment classification;
}
