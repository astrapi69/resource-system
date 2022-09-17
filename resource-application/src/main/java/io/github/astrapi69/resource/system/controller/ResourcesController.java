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
package io.github.astrapi69.resource.system.controller;

import io.github.astrapi69.resource.system.enumtype.AppRestPath;
import io.github.astrapi69.resource.system.service.api.ResourcesService;
import io.github.astrapi69.resource.system.viewmodel.Resource;
import io.github.astrapi69.resource.system.viewmodel.UploadRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@RestController
@RequestMapping(value = AppRestPath.REST_VERSION
		+ AppRestPath.REST_FILES, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ResourcesController {
	ResourcesService resourcesService;
	public static final String REST_PATH_DOWNLOAD = "/download/{id}";

	@CrossOrigin(origins = "*")
	@RequestMapping(value = REST_PATH_DOWNLOAD, method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Downloads the file from the given id")
	@ApiImplicitParams({@ApiImplicitParam(name = "id", value = "The id", dataType = "String")})
	public ResponseEntity<?> download(@PathVariable String id) {
		Resource resource = resourcesService.download(id);
		String filename = resource.getFilename();
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "file; filename=\"" + filename + "\"");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");
		InputStream inputStream = new ByteArrayInputStream(resource.getContent());

		return ResponseEntity.ok().headers(header).contentLength(resource.getContent().length)
			.contentType(MediaType.parseMediaType(resource.getContentType()))
			.body(new InputStreamResource(inputStream));
	}

	@RequestMapping(path = "/file", consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE}, method = RequestMethod.POST)
	public ResponseEntity<Resource> upload(@ModelAttribute UploadRequest uploadRequest)
	{
		Resource resource = resourcesService.upload(uploadRequest);
		return ResponseEntity.status(HttpStatus.OK).body(resource);
	}

}
