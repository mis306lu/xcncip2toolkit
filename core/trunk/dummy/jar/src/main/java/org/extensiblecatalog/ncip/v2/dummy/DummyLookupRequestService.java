/**
 * Copyright (c) 2010 eXtensible Catalog Organization
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the MIT/X11 license. The text of the license can be
 * found at http://www.opensource.org/licenses/mit-license.php.
 */

package org.extensiblecatalog.ncip.v2.dummy;

import org.extensiblecatalog.ncip.v2.service.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Lookup Request service for the Dummy back-end connector. Basically this just
 * calls the DummyRemoteServiceManager to get hard-coded data (e.g. title, call #, etc.).
 * <p/>
 * Note: If you're looking for a model of how to code your own ILS's NCIPService classes, do not
 * use this class as an example. See the NCIP toolkit Connector developer's documentation for guidance.
 */
public class DummyLookupRequestService implements LookupRequestService {

    /**
     * Handles a NCIP LookupRequest service by returning hard-coded data.
     *
     * @param initData       the LookupRequestInitiationData
     * @param serviceManager provides access to remote services
     * @return LookupRequestResponseData
     */
    @Override
    public LookupRequestResponseData performService(LookupRequestInitiationData initData,
                                                 ServiceContext serviceContext,
                                                 RemoteServiceManager serviceManager) throws ServiceException {

        final LookupRequestResponseData responseData = new LookupRequestResponseData();

        DummyRemoteServiceManager dummySvcMgr = (DummyRemoteServiceManager)serviceManager;

        // Put the bibliographic information into the response.
        BibliographicDescription bibDesc = new BibliographicDescription();
        DummyDatabase.ItemInfo itemInfo = null;
        List<Problem> problems = null;

        if ( initData.getItemId() != null
                && ( initData.getUserId() != null
                    || initData.getAuthenticationInputs() != null && !initData.getAuthenticationInputs().isEmpty())) {

            // Note: We don't validate the RequestType, despite its being required by NCIP.

            itemInfo = DummyDatabase.ItemInfo.getByBarcode(initData.getItemId().getItemIdentifierValue());
            if ( itemInfo == null )
            {

                problems = ServiceHelper.generateProblems(Version1LookupRequestProcessingError.UNKNOWN_REQUEST, "LookupRequest",
                    null, "Item " + initData.getItemId().getItemIdentifierValue() + " not found.");

            } else if ( itemInfo.userNo.compareToIgnoreCase(initData.getUserId().getUserIdentifierValue()) != 0 ) {

                problems = ServiceHelper.generateProblems(Version1LookupRequestProcessingError.UNKNOWN_REQUEST, "LookupRequest",
                    null, "Item " + initData.getItemId().getItemIdentifierValue() + " found but User "
                        + initData.getUserId().getUserIdentifierValue() + " does not match.");

            }

        } else if ( initData.getRequestId() != null ) {

            DummyDatabase.RequestInfo requestInfo
                = DummyDatabase.RequestInfo.getByRequestNo(initData.getRequestId().getRequestIdentifierValue());
            if ( requestInfo != null )
            {
                itemInfo = DummyDatabase.ItemInfo.getByBarcode(requestInfo.itemBarcode);
                if ( itemInfo == null )
                {
                    problems = ServiceHelper.generateProblems(Version1LookupRequestProcessingError.UNKNOWN_REQUEST, "LookupRequest",
                        null, "Item " + initData.getItemId().getItemIdentifierValue() + " not found.");
                }
            }
            else
            {
                problems = ServiceHelper.generateProblems(Version1LookupRequestProcessingError.UNKNOWN_REQUEST, "LookupRequest",
                    null, "Request " + initData.getRequestId().getRequestIdentifierValue() + " not found.");
            }

        } else {

            problems = ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING, "LookupRequest",
                null, "Either ItemId or RequestId is required.");

        }

        if ( problems == null ) {

            ItemId itemId = new ItemId();
            itemId.setItemIdentifierValue(itemInfo.barcode);
            responseData.setItemId(itemId);
            if ( initData.getRequestId() != null )
            {
                responseData.setRequestId(initData.getRequestId());
            }

            DummyDatabase.HoldingInfo holdingInfo = itemInfo.holdingInfo;
            DummyDatabase.BibInfo bibInfo = holdingInfo.bibInfo;

            bibDesc.setTitle(bibInfo.title);

            BibliographicRecordId bibliographicRecordId = new BibliographicRecordId();
            bibliographicRecordId.setBibliographicRecordIdentifier(bibInfo.bibNo);
            AgencyId agencyId = new AgencyId(dummySvcMgr.getLibraryName());
            bibliographicRecordId.setAgencyId(agencyId);
            List<BibliographicRecordId> bibRecordIds = new ArrayList<BibliographicRecordId>();
            bibRecordIds.add(bibliographicRecordId);
            bibDesc.setBibliographicRecordIds(bibRecordIds);

            Language language = Language.find(Version1Language.VERSION_1_LANGUAGE, bibInfo.language);
            bibDesc.setLanguage(language);

            // Item information
            DummyDatabase.CircStatus ilsCircStatus = itemInfo.circStatus;

            // Map from the Dummy ILS's circulation status values to the Scheme Value Pair used in NCIP.
            CirculationStatus circStatus;
            switch (ilsCircStatus) {
                case ON_ORDER: {
                    circStatus = Version1CirculationStatus.IN_PROCESS;
                    break;
                }
                case ON_SHELF: {
                    circStatus = Version1CirculationStatus.AVAILABLE_ON_SHELF;
                    break;
                }
                case CHECKED_OUT: {
                    circStatus = Version1CirculationStatus.ON_LOAN;
                    break;
                }
                case IN_TRANSIT: {
                    circStatus = Version1CirculationStatus.IN_TRANSIT_BETWEEN_LIBRARY_LOCATIONS;
                    break;
                }
                default: {
                    circStatus = Version1CirculationStatus.CIRCULATION_STATUS_UNDEFINED;
                    break;
                }
            }

            // Item Description
            ItemDescription itemDescription = new ItemDescription();
            itemDescription.setCallNumber(itemInfo.callNo);

            if ( holdingInfo != null ) {

                HoldingsInformation holdingsInfo = new HoldingsInformation();
                holdingsInfo.setUnstructuredHoldingsData(holdingInfo.summaryHoldings);
                itemDescription.setHoldingsInformation(holdingsInfo);

            }

            itemDescription.setNumberOfPieces(new BigDecimal(1));

            // Assemble the ItemOptionalFields where most of the data is returned
            ItemOptionalFields itemOptionalFields = new ItemOptionalFields();
            itemOptionalFields.setBibliographicDescription(bibDesc);
            itemOptionalFields.setCirculationStatus(circStatus);
            itemOptionalFields.setItemDescription(itemDescription);

            responseData.setItemOptionalFields(itemOptionalFields);

        } else if ( problems == null ) {

            problems = ServiceHelper.generateProblems(Version1GeneralProcessingError.TEMPORARY_PROCESSING_FAILURE,
                "LookupRequest", null, "Unknown logic error.");
            responseData.setProblems(problems);
            
        } else {

            responseData.setProblems(problems);

        }

        return responseData;
    }

}
