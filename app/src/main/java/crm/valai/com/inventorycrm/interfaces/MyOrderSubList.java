package crm.valai.com.inventorycrm.interfaces;

import android.content.Context;
import android.widget.Spinner;

import crm.valai.com.inventorycrm.modals.MyOrderResultPOJO;

/**
 * @author by Mohit Arora on 3/4/18.
 */

public interface MyOrderSubList {
    void addMtPcsSpinner(Context ctx, Spinner spinner);

    void showMessages(String message);

    void hideKeyBoards();

    void saveResultList(MyOrderResultPOJO myOrderResultPOJO);
}
