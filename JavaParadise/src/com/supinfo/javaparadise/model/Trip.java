package com.supinfo.javaparadise.model;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
public class Trip implements Serializable {
	
	private Long id;
	private Place departure;
	private Place destination;
	private BigDecimal price;
	
	
	public Trip() {
		// TODO Auto-generated constructor stub
	}
	
	public Place getDeparture() {
		return departure;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Place getDestination() {
		return destination;
	}

	public void setDestination(Place destination) {
		this.destination = destination;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setDeparture(Place departure) {
		this.departure = departure;
	}

}
