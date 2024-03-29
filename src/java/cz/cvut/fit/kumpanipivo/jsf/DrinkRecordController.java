package cz.cvut.fit.kumpanipivo.jsf;

import cz.cvut.fit.kumpanipivo.ejbs.ActiveBarrelBean;
import cz.cvut.fit.kumpanipivo.entity.DrinkRecord;
import cz.cvut.fit.kumpanipivo.jsf.util.JsfUtil;
import cz.cvut.fit.kumpanipivo.jsf.util.PaginationHelper;
import cz.cvut.fit.kumpanipivo.ejbs.DrinkRecordFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@Named("drinkRecordController")
@SessionScoped
public class DrinkRecordController implements Serializable {

    private DrinkRecord current;
    private DataModel items = null;
    @EJB
    private cz.cvut.fit.kumpanipivo.ejbs.DrinkRecordFacade ejbFacade;
    @EJB
    private ActiveBarrelBean activeBarrelBean;
    
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public DrinkRecordController() {
    }

    public DrinkRecord getSelected() {
        if (current == null) {
            current = new DrinkRecord();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    public String getBarrelToString(){
        return current.getBarrel().toString();
    }

    private DrinkRecordFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    List<DrinkRecord> list = getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()});
                    System.out.println(list);
                    return new ListDataModel(list);
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (DrinkRecord) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new DrinkRecord();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            if(activeBarrelBean.getActiveBarrel()!= null)
                current.setBarrel(activeBarrelBean.getActiveBarrel());
            else throw new Exception("Není naraženo");
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DrinkRecordCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (DrinkRecord) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DrinkRecordUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (DrinkRecord) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DrinkRecordDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
            items = getPagination().createPageDataModel();
        return items;
    }
    
    

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public DrinkRecord getDrinkRecord(int id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = DrinkRecord.class)
    public static class DrinkRecordControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DrinkRecordController controller = (DrinkRecordController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "drinkRecordController");
            return controller.getDrinkRecord(getKey(value));
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof DrinkRecord) {
                DrinkRecord o = (DrinkRecord) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + DrinkRecord.class.getName());
            }
        }

    }

}
