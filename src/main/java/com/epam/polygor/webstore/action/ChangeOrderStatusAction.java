package com.epam.polygor.webstore.action;


import com.epam.polygor.webstore.service.PurchaseService;
import com.epam.polygor.webstore.servlet.Scope;
import com.epam.polygor.webstore.servlet.WebContext;
import java.util.HashMap;
import java.util.Map;

public class ChangeOrderStatusAction implements Action {

    @Override
    public ActionResult execute(WebContext webContext) {
        PurchaseService purchaseService = new PurchaseService();
        String[] purchaseIDs = webContext.getParameterValues("purchaseID");
        String[] purchaseStatuses = webContext.getParameterValues("purchaseStatus");
        String orderNumber = webContext.getParameter("orderNumber");
        if (purchaseIDs.length != purchaseStatuses.length) {
            throw new ActionException("Amount of purchases not equals to statuses amount");
        }
        Map<Long, String> purchaseStatusByID = getPurchaseStatusByIdMap(purchaseIDs, purchaseStatuses);
        purchaseService.updatePurchases(purchaseStatusByID);
        //set order number to request for displaying which particular order has been changed
        webContext.setAttribute(orderNumber, orderNumber, Scope.FLASH);
        return new ActionResult(webContext.getPreviousURI(), true);
    }



    private Map<Long, String> getPurchaseStatusByIdMap(String[] purchaseIDs, String[] purchaseStatuses) {
        Map<Long, String> statusByID = new HashMap<>();
        long purchaseID;
        for (int i = 0; i < purchaseIDs.length; i++) {
            purchaseID = Long.parseLong(purchaseIDs[i]);
            statusByID.put(purchaseID, purchaseStatuses[i]);
        }
        return statusByID;
    }
}
