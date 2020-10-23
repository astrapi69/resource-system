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
package de.alpharogroup.resource.system.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import de.alpharogroup.checksum.ByteArrayChecksumExtensions;
import de.alpharogroup.checksum.api.ChecksumAlgorithm;
import de.alpharogroup.collections.list.ListExtensions;
import de.alpharogroup.resource.system.jpa.entities.Resources;
import de.alpharogroup.resource.system.jpa.repositories.ResourcesRepository;
import de.alpharogroup.resource.system.mapper.ResourcesMapper;
import de.alpharogroup.resource.system.viewmodel.Resource;
import de.alpharogroup.resource.system.dto.UploadRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.alpharogroup.resource.system.service.api.ResourcesService;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * The service class {@link ResourcesServiceImpl}.
 */
@Transactional
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourcesServiceImpl
	implements
		ResourcesService
{
	ResourcesRepository repository;

	ResourcesMapper resourcesMapper;

	@Override
	public List<Resources> findByDescription(String description)
	{
		return repository.findByDescription(description);
	}

	@Override
	public List<Resources> findByFilename(String filename)
	{
		return repository.findByFilename(filename);
	}

	@Override
	public Resources getDefaultPlaceholder()
	{
		final List<Resources> Resources = findByFilename("default_placeholder.jpg");
		return ListExtensions.getFirst(Resources);
	}

	@Override
	public Resources getManPlaceholder()
	{
		final List<Resources> Resources = findByFilename("man_placeholder.jpg");
		return ListExtensions.getFirst(Resources);
	}

	@Override
	public Resources getWomanPlaceholder()
	{
		final List<Resources> Resources = findByFilename("woman_placeholder.jpg");
		return ListExtensions.getFirst(Resources);
	}

	public Resource download(String id) {
		Resource resource = Resource.builder().build();
		Optional<Resources> optionalEntity = repository.findById(UUID.fromString(id));
		if (optionalEntity.isPresent()) {
			Resources entity = optionalEntity.get();
			resource = resourcesMapper.toDto(entity);
		}
		return resource;
	}

	public Resource upload(UploadRequest uploadRequest) {
		MultipartFile file = uploadRequest.getMultipartFile();
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		String contentType = file.getContentType();
		String md5;
		byte[] fileContent;
		if (file.isEmpty()) {
			throw new RuntimeException("file is empty");
		}
		if (filename.contains("..")) {
			throw new RuntimeException("Filename contains not valid path sequence");
		}
		try
		{
			fileContent = file.getBytes();
			md5 = ByteArrayChecksumExtensions.getChecksum(fileContent, ChecksumAlgorithm.MD5);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Read file bytes failed",e);
		}
		catch (NoSuchAlgorithmException e)
		{
			throw new RuntimeException(
				"Checksum failed because of not found algorithm for checksum genearation", e);
		}
		Resources resources = Resources.builder()
			.checksum(md5)
			.content(fileContent)
			.contentType(contentType)
			.created(LocalDateTime.now())
			.deletedFlag(false)
			.description(file.getName())
			.filename(filename)
			.filepath(uploadRequest.getFilepath())
			.filesize(fileContent.length)
			.build();
		Resources saved = repository.save(resources);
		return resourcesMapper.toDto(saved);
	}

}
