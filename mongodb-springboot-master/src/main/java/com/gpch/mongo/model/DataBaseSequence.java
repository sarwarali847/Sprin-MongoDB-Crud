package com.gpch.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="database_sequence")
public class DataBaseSequence {
	
	@Id
	private String id;
	private long seq;
	
	public DataBaseSequence() { }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}
	
	
	
	

}
