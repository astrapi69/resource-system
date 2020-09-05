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
package de.alpharogroup.resource.system.factory;

import de.alpharogroup.resource.system.jpa.entities.Resources;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public final class ResourceSystemFactory implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant instance. */
	private static final ResourceSystemFactory instance = new ResourceSystemFactory();

	public static ResourceSystemFactory getInstance()
	{
		return instance;
	}

	private ResourceSystemFactory()
	{
	}

	/**
	 * Factory method for create an Resources object.
	 *
	 * @param id
	 *            the id
	 * @param description
	 *            the description
	 * @param filename
	 *            the filename
	 * @param filesize
	 *            the filesize
	 * @param filetype
	 *            the filetype
	 * @param content
	 *            the content
	 * @param created
	 *            the created
	 * @param deletedFlag
	 *            the deleted flag
	 * @param checksum
	 *            the checksum
	 * @return Resources A Resources object
	 */
	public Resources newResources(final UUID id, final String description, final String filename,
		final long filesize, final String filetype, final byte[] content,
		final LocalDateTime created, final Boolean deletedFlag, final String checksum)
	{
		final Resources resources = new Resources();
		resources.setDescription(description);
		resources.setFilename(filename);
		resources.setFilesize(filesize);
		resources.setContentType(filetype);
		resources.setId(id);
		resources.setContent(content);
		resources.setCreated(created);
		resources.setChecksum(checksum);
		resources.setDeletedFlag(deletedFlag);
		return resources;
	}

	/**
	 * Factory method for create an Resources object.
	 *
	 * @param description
	 *            the description
	 * @param filename
	 *            the filename
	 * @param filesize
	 *            the filesize
	 * @param filetype
	 *            the filetype
	 * @param content
	 *            the content
	 * @param saved
	 *            the saved
	 * @param deletedFlag
	 *            the deleted flag
	 * @param checksum
	 *            the checksum
	 * @return Resources A Resources object
	 */
	public Resources newResources(final String description, final String filename,
		final long filesize, final String filetype, final byte[] content, final LocalDateTime saved,
		final Boolean deletedFlag, final String checksum)
	{
		return newResources(null, description, filename, filesize, filetype, content, saved,
			deletedFlag, checksum);
	}

}
