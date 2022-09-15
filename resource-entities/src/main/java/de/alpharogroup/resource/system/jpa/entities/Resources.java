/**
 * The MIT License
 *
 * Copyright (C) 2015 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package de.alpharogroup.resource.system.jpa.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.github.astrapi69.entity.uniqueable.UUIDEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

/**
 * The entity class {@link Resources} is keeping the information for the resources from users like
 * logos, files, images etc.
 */
@Entity
@Table(name = Resources.TABLE_NAME)
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Resources extends UUIDEntity implements Cloneable
{
	/**
	 * The serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	static final String SINGULAR_ENTITY_NAME = "resource";
	static final String TABLE_NAME = SINGULAR_ENTITY_NAME+"s";
	static final String COLUMN_NAME_CONTENT_TYPE = "content_type";
	static final String COLUMN_NAME_DELETE_FLAG = "deleted_flag";
	/** The checksum from this resource. */
	@Column
	String checksum;
	/** The binary data from this resource. */
	@Column(columnDefinition = "BYTEA")
	byte[] content;
	/** The content type from this resource. */
	@Column(name = COLUMN_NAME_CONTENT_TYPE, length = 64)
	String contentType;
	/** The date when this resource is created in the database. */
	@Column
	LocalDateTime created;
	/**
	 * A flag that indicates that the resource is deleted from the user. Will be deleted in batch
	 * process.
	 */
	@Column(name = COLUMN_NAME_DELETE_FLAG)
	boolean deletedFlag;
	/** A description for this resource. */
	@Column(length = 1024)
	String description;
	/** The filename from this resource. */
	@Column(length = 1024)
	String filename;
	/** The filepath from this resource. */
	@Column(length = 4096)
	String filepath;
	/** The size from this resource. */
	@Column(length = 64)
	long filesize;

}
