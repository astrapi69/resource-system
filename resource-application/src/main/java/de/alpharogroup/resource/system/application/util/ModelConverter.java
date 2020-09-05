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
package de.alpharogroup.resource.system.application.util;

import java.util.ArrayList;
import java.util.List;

import de.alpharogroup.resource.system.jpa.entities.Resources;
import de.alpharogroup.throwable.RuntimeExceptionDecorator;
import org.apache.commons.lang3.ArrayUtils;

import de.alpharogroup.copy.object.CopyObjectExtensions;
import de.alpharogroup.resource.system.application.model.ResourcesModel;

/**
 * The class {@link ModelConverter}.
 */
public class ModelConverter
{

	/**
	 * Converts the given array of {@link Byte} objects to a primitive {@code byte} array.
	 *
	 * @param array
	 *            the array
	 * @return the primitive {@code byte} array
	 */
	public static byte[] toByteArray(final Byte[] array)
	{
		return ArrayUtils.toPrimitive(array);
	}

	/**
	 * Converts the given primitive {@code byte} array to an array of {@link Byte} objects.
	 *
	 * @param array
	 *            the array
	 * @return the array of {@link Byte} objects.
	 */
	public static Byte[] toObject(final byte[] array)
	{
		return ArrayUtils.toObject(array);
	}

	/**
	 * To resources model.
	 *
	 * @param resources
	 *            the resources
	 * @return the list
	 */
	public static List<ResourcesModel> toResourcesModel(final List<Resources> resources)
	{
		final List<ResourcesModel> rml = new ArrayList<>();
		for (final Resources resource : resources)
		{
			rml.add(toResourcesModel(resource));
		}
		return rml;
	}

	/**
	 * To resources model.
	 *
	 * @param resource
	 *            the resource
	 * @return the resources model
	 */
	public static ResourcesModel toResourcesModel(final Resources resource)
	{
		return RuntimeExceptionDecorator.decorate(()->
			CopyObjectExtensions.copy(resource, new ResourcesModel()));
	}
}
