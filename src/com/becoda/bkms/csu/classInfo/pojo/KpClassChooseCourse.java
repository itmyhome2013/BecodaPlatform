package com.becoda.bkms.csu.classInfo.pojo;

import java.util.Date;

/**
 * KpClassChooseCourseId entity. @author MyEclipse Persistence Tools
 */

public class KpClassChooseCourse implements java.io.Serializable {

	// Fields

	private String classId;
	private String lessonId;
	private Date time;
	private String address;
	private String linkName;
	private String linkPhone;

	// Constructors

	/** default constructor */
	public KpClassChooseCourse() {
	}

	/** full constructor */
	public KpClassChooseCourse(String classId, String lessonId, Date time,
			String address, String linkName, String linkPhone) {
		this.classId = classId;
		this.lessonId = lessonId;
		this.time = time;
		this.address = address;
		this.linkName = linkName;
		this.linkPhone = linkPhone;
	}

	// Property accessors

	public String getClassId() {
		return this.classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getLessonId() {
		return this.lessonId;
	}

	public void setLessonId(String lessonId) {
		this.lessonId = lessonId;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLinkName() {
		return this.linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkPhone() {
		return this.linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof KpClassChooseCourse))
			return false;
		KpClassChooseCourse castOther = (KpClassChooseCourse) other;

		return ((this.getClassId() == castOther.getClassId()) || (this
				.getClassId() != null && castOther.getClassId() != null && this
				.getClassId().equals(castOther.getClassId())))
				&& ((this.getLessonId() == castOther.getLessonId()) || (this
						.getLessonId() != null
						&& castOther.getLessonId() != null && this
						.getLessonId().equals(castOther.getLessonId())))
				&& ((this.getTime() == castOther.getTime()) || (this.getTime() != null
						&& castOther.getTime() != null && this.getTime()
						.equals(castOther.getTime())))
				&& ((this.getAddress() == castOther.getAddress()) || (this
						.getAddress() != null && castOther.getAddress() != null && this
						.getAddress().equals(castOther.getAddress())))
				&& ((this.getLinkName() == castOther.getLinkName()) || (this
						.getLinkName() != null
						&& castOther.getLinkName() != null && this
						.getLinkName().equals(castOther.getLinkName())))
				&& ((this.getLinkPhone() == castOther.getLinkPhone()) || (this
						.getLinkPhone() != null
						&& castOther.getLinkPhone() != null && this
						.getLinkPhone().equals(castOther.getLinkPhone())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getClassId() == null ? 0 : this.getClassId().hashCode());
		result = 37 * result
				+ (getLessonId() == null ? 0 : this.getLessonId().hashCode());
		result = 37 * result
				+ (getTime() == null ? 0 : this.getTime().hashCode());
		result = 37 * result
				+ (getAddress() == null ? 0 : this.getAddress().hashCode());
		result = 37 * result
				+ (getLinkName() == null ? 0 : this.getLinkName().hashCode());
		result = 37 * result
				+ (getLinkPhone() == null ? 0 : this.getLinkPhone().hashCode());
		return result;
	}

}