package com.gft.estudosapidevdojo.exceptionHandler;

import javax.annotation.Generated;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class Error {

	private String userMessage;
	private String developerMessage;

	@Generated("SparkTools")
	private Error(Builder builder) {
		this.userMessage = builder.userMessage;
		this.developerMessage = builder.developerMessage;
	}

	/**
	 * Creates builder to build {@link Error}.
	 *
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Error}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String userMessage;
		private String developerMessage;

		private Builder() {
		}

		public Builder userMessage(String mensagemUsuario) {
			this.userMessage = mensagemUsuario;
			return this;
		}

		public Builder developerMessage(String mensagemDev) {
			this.developerMessage = mensagemDev;
			return this;
		}

		public Error build() {
			return new Error(this);
		}
	}

}
