package ru.tinkoff.test.translator.dao.entity;

import java.time.LocalDateTime;

public class InvoiceInfoEntity {
	private Long id;
	private LocalDateTime invoiceTime;
	private String[] inputParams;
	private String clientIp;

	public InvoiceInfoEntity(LocalDateTime invoiceTime, String[] inputParams, String clientIp) {
		this.invoiceTime = invoiceTime;
		this.inputParams = inputParams;
		this.clientIp = clientIp;
	}

	public InvoiceInfoEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getInvoiceTime() {
		return invoiceTime;
	}

	public void setInvoiceTime(LocalDateTime invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	public String[] getInputParams() {
		return inputParams;
	}

	public void setInputParams(String... inputParams) {
		this.inputParams = inputParams;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Override
	public String toString() {
		return "InvoiceInfoEntity [id=" + id + ", invoiceTime=" + invoiceTime + ", inputParams=" + inputParams
				+ ", clientIp=" + clientIp + "]";
	}
}
