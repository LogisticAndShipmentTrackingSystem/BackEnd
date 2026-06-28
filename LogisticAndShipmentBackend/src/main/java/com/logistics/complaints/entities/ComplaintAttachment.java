package com.logistics.complaints.entities;

import com.logistics.common.entities.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="complaint_attachments")
@Getter
@Setter
@ToString
@AttributeOverride(name="id", column = @Column(name="complaint_attachment_id"))
public class ComplaintAttachment extends BaseEntity{
	@Column(name="file_url")
	private String fileURL;
	
	@Column(name="file_name")
	private String fileName;
	
	@ManyToOne
	@JoinColumn(name="complaint_id", nullable = false)
	private Complaint complaint;
}
