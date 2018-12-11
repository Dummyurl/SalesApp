package crm.valai.com.inventorycrm.interfaces;

import java.util.HashMap;
import java.util.List;

import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;

/**
 * @author by Mohit Arora on 30/3/18.
 */

public interface SetListSize {
    void setListSize(int size);

//    void setMyOrderResultList(HashMap<Integer, List<MyOrderResultPOJO>> list);
//
//    HashMap<Integer, List<MyOrderResultPOJO>> getMyOrderResultList();

    void setMyOrderResultListNew(List<MyOrderResultPOJO> list);

    List<MyOrderResultPOJO> getMyOrderResultListNew();

}
