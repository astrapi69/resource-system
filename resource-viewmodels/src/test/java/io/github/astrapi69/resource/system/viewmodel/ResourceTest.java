package io.github.astrapi69.resource.system.viewmodel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.checksum.ByteArrayChecksumExtensions;
import io.github.astrapi69.checksum.FileChecksumExtensions;
import io.github.astrapi69.crypt.api.algorithm.ChecksumAlgorithm;
import io.github.astrapi69.file.FileExtensions;
import io.github.astrapi69.file.delete.DeleteFileExtensions;
import io.github.astrapi69.file.read.ReadFileExtensions;
import io.github.astrapi69.file.search.PathFinder;
import io.github.astrapi69.file.write.WriteFileExtensions;

public class ResourceTest
{
	@Test
	public void testToFile() throws IOException, NoSuchAlgorithmException
	{
		File tmpFile;
		Resource actual;
		String md5;
		byte[] fileContent;

		// new scenario...
		tmpFile = new File(PathFinder.getSrcTestResourcesDir(), "test-read.txt");
		fileContent = ReadFileExtensions.readFileToBytearray(tmpFile);
		String filename = tmpFile.getName();
		String contentType = Files.probeContentType(tmpFile.toPath());
		md5 = ByteArrayChecksumExtensions.getChecksum(fileContent, ChecksumAlgorithm.MD5);
		Resource resource = Resource.builder().checksum(md5).content(fileContent)
			.contentType(contentType).created(OffsetDateTime.now()).deletedFlag(false)
			.description(filename).filename(filename)
			.filepath(FileExtensions.getAbsolutPathWithoutFilename(tmpFile))
			.filesize(fileContent.length).build();
		assertNotNull(resource);

		File file = new File(resource.getFilepath(), "copy-of-test-read.txt");
		WriteFileExtensions.storeByteArrayToFile(resource.getContent(), file);
		md5 = FileChecksumExtensions.getChecksum(file, ChecksumAlgorithm.MD5);
		assertEquals(md5, resource.getChecksum());
		DeleteFileExtensions.delete(file);
	}
}
