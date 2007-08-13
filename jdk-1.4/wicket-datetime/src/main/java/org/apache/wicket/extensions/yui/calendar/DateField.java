/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.extensions.yui.calendar;

import java.util.Date;

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.MutableDateTime;

/**
 * Works on a {@link java.util.Date} object. Displays a date field and a
 * {@link CalendarPopup calendar popup}.
 * 
 * @author eelcohillenius
 */
public class DateField extends FormComponentPanel
{
	private static final long serialVersionUID = 1L;

	private MutableDateTime date;

	private DateTextField dateField;

	/**
	 * Construct.
	 * 
	 * @param id
	 */
	public DateField(String id)
	{
		super(id);
		init();
	}

	/**
	 * Construct.
	 * 
	 * @param id
	 * @param model
	 */
	public DateField(String id, IModel model)
	{
		super(id, model);
		init();
	}

	/**
	 * Gets date.
	 * 
	 * @return date
	 */
	public Date getDate()
	{
		return (date != null) ? date.toDate() : null;
	}

	/**
	 * @see org.apache.wicket.markup.html.form.FormComponent#isRequired()
	 */
	public final boolean isRequired()
	{
		// prevent clients from overriding
		return super.isRequired();
	}

	/**
	 * Sets date.
	 * 
	 * @param date
	 *            date
	 */
	public void setDate(Date date)
	{
		this.date = (date != null) ? new MutableDateTime(date) : null;
		setModelObject(date);
	}

	/**
	 * @see org.apache.wicket.markup.html.form.FormComponent#setRequired(boolean)
	 */
	public FormComponent setRequired(boolean required)
	{
		dateField.setRequired(required);
		return this;
	}

	/**
	 * Initialize.
	 */
	private void init()
	{
		setType(Date.class);
		PropertyModel dateFieldModel = new PropertyModel(this, "date");
		add(dateField = newDateTextField(dateFieldModel));
		dateField.add(new DatePicker());
	}

	/**
	 * Sets the converted input. In this case, we're really just interested in
	 * the nested date field, as that is the element that receives the real user
	 * input. So we're just passing that on.
	 * <p>
	 * Note that overriding this method is a better option than overriding
	 * {@link #updateModel()} like the first versions of this class did. The
	 * reason for that is that this method can be used by form validators
	 * without having to depend on the actual model being updated, and this
	 * method is called by the default implementation of {@link #updateModel()}
	 * anyway (so we don't have to override that anymore).
	 * </p>
	 * 
	 * @return instance of {@link Date}, possibly null
	 * 
	 * @see org.apache.wicket.markup.html.form.FormComponent#convertInput()
	 */
	protected void convertInput()
	{
		setConvertedInput(dateField.getConvertedInput());
	}

	/**
	 * create a new {@link DateTextField} instance to be added to this panel.
	 * 
	 * @param dateFieldModel
	 *            model that should be used by the {@link DateTextField}
	 * @return a new date text field instance
	 */
	protected DateTextField newDateTextField(PropertyModel dateFieldModel)
	{
		return DateTextField.forShortStyle("date", dateFieldModel);
	}

	/**
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	protected void onBeforeRender()
	{
		Date d = (Date)getModelObject();
		if (d != null)
		{
			date = new MutableDateTime(d);
		}
		else
		{
			date = null;
		}

		super.onBeforeRender();
	}
}
