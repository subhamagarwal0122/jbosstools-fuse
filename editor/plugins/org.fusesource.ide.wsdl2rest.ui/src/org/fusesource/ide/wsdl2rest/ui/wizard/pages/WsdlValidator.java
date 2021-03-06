/*******************************************************************************
 * Copyright (c) 2018 Red Hat, Inc. 
 * Distributed under license by Red Hat, Inc. All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at https://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 ******************************************************************************/
package org.fusesource.ide.wsdl2rest.ui.wizard.pages;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.databinding.validation.IValidator;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.fusesource.ide.wsdl2rest.ui.internal.UIMessages;

/**
 * Validate that the WSDL is an accessible file, whether local or remote.
 * 
 * @author brianf
 */
public class WsdlValidator implements IValidator {

	@Override
	public IStatus validate(Object value) {
		if (!((value instanceof String) && ((String) value).length() > 0)) {
			return ValidationStatus.error(UIMessages.wsdl2RestWizardFirstPageValidatorWSDLUrlRequired);
		}
		if (!isURLAccessible((String) value)) {
			return ValidationStatus.error(UIMessages.wsdl2RestWizardFirstPageValidatorWSDLInaccessible);
		}
		return ValidationStatus.ok();
	}
	
	/**
	 * Test to ensure that the URL represented by urlText String is accessible.
	 * 
	 * @param urlText
	 * @return
	 */
	private boolean isURLAccessible(String urlText) {
		try {
			URL url = new URL(urlText);
			return isURLAccessible(url);
		} catch (MalformedURLException e) {
			return false;
		}
	}
	
	private boolean isURLAccessible(URL url) {
		try (InputStream testStream = url.openStream()) {
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}