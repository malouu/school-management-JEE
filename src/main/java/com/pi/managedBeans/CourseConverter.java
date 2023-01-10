package com.pi.managedBeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import com.pi.dao.CourseDao;
import com.pi.entities.Course;

@ManagedBean
@RequestScoped
public class CourseConverter implements Converter {

    private CourseDao courseDao = new CourseDao();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // print the type of value
        if (value == null) {
            return null;
        }
        if (value instanceof Course) {
            return String.valueOf(((Course) value).getId());
        } else {
            throw new ConverterException(new FacesMessage(value + " is not a valid Course"));
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            Course course = courseDao.getCourseById(Integer.parseInt(value));
            System.out.println("****************************" + course.toString());
            return course;
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(value + " is not a valid Course ID"), e);
        }
    }

}
