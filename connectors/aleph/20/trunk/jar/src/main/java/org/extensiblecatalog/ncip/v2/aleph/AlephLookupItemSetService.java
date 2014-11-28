package org.extensiblecatalog.ncip.v2.aleph;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.extensiblecatalog.ncip.v2.aleph.restdlf.AlephException;
import org.extensiblecatalog.ncip.v2.aleph.restdlf.item.AlephItem;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephConstants;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephRemoteServiceManager;
import org.extensiblecatalog.ncip.v2.aleph.util.AlephUtil;
import org.extensiblecatalog.ncip.v2.aleph.util.ItemToken;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.BibInformation;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicId;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.HoldingsInformation;
import org.extensiblecatalog.ncip.v2.service.HoldingsSet;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemDescriptionLevel;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemInformation;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.ItemUseRestrictionType;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetInitiationData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetResponseData;
import org.extensiblecatalog.ncip.v2.service.LookupItemSetService;
import org.extensiblecatalog.ncip.v2.service.Problem;
import org.extensiblecatalog.ncip.v2.service.ProblemType;
import org.extensiblecatalog.ncip.v2.service.RemoteServiceManager;
import org.extensiblecatalog.ncip.v2.service.ResponseHeader;
import org.extensiblecatalog.ncip.v2.service.ServiceContext;
import org.extensiblecatalog.ncip.v2.service.ServiceError;
import org.extensiblecatalog.ncip.v2.service.ServiceException;
import org.extensiblecatalog.ncip.v2.service.ServiceHelper;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.Version1BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.Version1GeneralProcessingError;
import org.extensiblecatalog.ncip.v2.service.Version1ItemIdentifierType;
import org.extensiblecatalog.ncip.v2.service.Version1LookupItemProcessingError;
import org.xml.sax.SAXException;

public class AlephLookupItemSetService implements LookupItemSetService {

	private static HashMap<String, ItemToken> tokens = new HashMap<String, ItemToken>();

	private int itemsForwarded;
	private int maximumItemsCount;

	private Random random = new Random();

	private boolean wantSeeAllProblems;

	@Override
	public LookupItemSetResponseData performService(LookupItemSetInitiationData initData, ServiceContext serviceContext, RemoteServiceManager serviceManager)
			throws ServiceException {

		if (initData.getMaximumItemsCount() != null)
			try {
				maximumItemsCount = initData.getMaximumItemsCount().intValueExact();
			} catch (Exception e) {
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "MaximumItemsCount element is not in correct format. " + e.getMessage());
			}
		else
			maximumItemsCount = 0;

		List<BibliographicId> bibIds = initData.getBibliographicIds();
		List<ItemId> itemIds = initData.getItemIds();

		String token = initData.getNextItemToken();
		ItemToken nextItemToken = null;
		// remove any bib ids from bibIds list that may have already been processed
		if (token != null) {
			// In case maximum items returned was reached while parsing one bibliographic Id with a lot alephItems inside
			nextItemToken = tokens.get(token);
			if (nextItemToken != null) {
				int index;
				if (bibIds.size() > 0) {
					index = getBibIdIndex(bibIds, nextItemToken.getBibliographicId());
					if (index > -1) {
						// remove the ones already processed
						if (nextItemToken.isRecordId() && !nextItemToken.doneWithRecordId())
							bibIds.subList(0, index).clear();
						else
							bibIds.subList(0, ++index).clear();
					}
				} else {
					index = getItemIdIndex(itemIds, nextItemToken.getBibliographicId());
					if (index > -1) {
						// remove the ones already processed
						itemIds.subList(0, ++index).clear();
					}
				}

				// Remove token from memory hashmap
				tokens.remove(token);
			} else
				throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "Invalid NextItemToken: " + token);
		}

		AlephRemoteServiceManager alephSvcMgr = (AlephRemoteServiceManager) serviceManager;
		wantSeeAllProblems = alephSvcMgr.echoParticularProblemsToLUIS;

		final LookupItemSetResponseData responseData = new LookupItemSetResponseData();
		itemsForwarded = 0;

		List<BibInformation> bibInformations = new ArrayList<BibInformation>();

		boolean getBibDescription = initData.getBibliographicDescriptionDesired();

		if (bibIds != null && bibIds.size() > 0) {

			for (BibliographicId bibId : bibIds) {
				BibInformation bibInformation = new BibInformation();

				try {
					if (bibId.getBibliographicRecordId() != null) {

						String id = bibId.getBibliographicRecordId().getBibliographicRecordIdentifier();

						if (id.isEmpty()) {
							if (wantSeeAllProblems) {
								Problem problem = new Problem(new ProblemType("Empty BibliographicRecordIdentifierValue."), null,
										"You have specified empty BibliographicRecordIdentifierValue.");
								bibInformation.setProblems(Arrays.asList(problem));
								bibInformations.add(bibInformation);
							}
							break;
						}

						bibInformation.setBibliographicId(bibId);

						List<AlephItem> alephItems = alephSvcMgr.lookupItems(id, initData);
						if (alephItems != null && alephItems.size() > 0) {
							int foundPieces = alephItems.size();

							// FIXME: NextItemToken should be implemented inside parser considering limited memory
							if (nextItemToken != null) {
								if (nextItemToken.isRecordId() && !nextItemToken.doneWithRecordId()) {
									alephItems.subList(0, nextItemToken.getNoOfDoneItemIds()).clear();
									nextItemToken.setDoneWithRecordId(true);
								}
							}

							for (AlephItem item : alephItems) {
								item.setNumberOfPieces(new BigDecimal(foundPieces));
							}

							AgencyId suppliedAgencyId = null;
							if (getBibDescription) {
								if (initData.getInitiationHeader() == null || initData.getInitiationHeader().getToAgencyId() == null)
									suppliedAgencyId = alephSvcMgr.toAgencyId(alephSvcMgr.getDefaultAgencyId());
								else
									suppliedAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId();
							}

							List<HoldingsSet> holdingSets = parseHoldingsSets(alephItems, suppliedAgencyId, initData);

							bibInformation.setHoldingsSets(holdingSets);

							int itemsToForward = holdingSets.get(0).getItemInformations().size();
							if (itemsToForward > 0) {

								bibInformations.add(bibInformation);

								// It is important to let token create even if the bibId is last of all desired bibIds in case of not all children items can be forwarded due to maximumItemsCount
								boolean isLast = bibIds.get(bibIds.size() - 1).equals(bibId) && itemsToForward == alephItems.size();

								// Do not create NextItemToken if this is last item desired
								if (maximumItemsCount == itemsForwarded && !isLast) {
									// Set next item token
									ItemToken itemToken = new ItemToken();

									itemToken.setBibliographicId(id);
									// itemToken.setItemId(alephItems.get(itemsCount - 1).getItemId());
									itemToken.setIsRecordId(true);

									if (itemsToForward == alephItems.size())
										itemToken.setDoneWithRecordId(true);
									else {
										if (nextItemToken == null)
											itemToken.setNoOfDoneItemIds(itemsToForward);
										else
											itemToken.setNoOfDoneItemIds(itemsToForward + nextItemToken.getNoOfDoneItemIds());
									}

									int newToken = random.nextInt();

									itemToken.setNextToken(Integer.toString(newToken));
									tokens.put(Integer.toString(newToken), itemToken);

									responseData.setNextItemToken(Integer.toString(newToken));
									break;
								}
							}

						} else if (wantSeeAllProblems) {
							Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, null, "Item " + id + ", you are searching for, does not exist.");

							bibInformation.setProblems(Arrays.asList(p));

							bibInformations.add(bibInformation);
							++itemsForwarded;

							boolean isLast = bibIds.get(bibIds.size() - 1).equals(bibId);

							// Do not create NextItemToken if this is last item desired
							if (maximumItemsCount == itemsForwarded && !isLast) {
								// Set next item token
								ItemToken itemToken = new ItemToken();

								itemToken.setBibliographicId(id);

								int newToken = random.nextInt();
								itemToken.setNextToken(Integer.toString(newToken));

								tokens.put(Integer.toString(newToken), itemToken);

								responseData.setNextItemToken(Integer.toString(newToken));
								break;
							}
						}

					} else if (bibId.getBibliographicItemId() != null) {
						String id = bibId.getBibliographicItemId().getBibliographicItemIdentifier();

						if (id.isEmpty()) {
							if (wantSeeAllProblems) {
								Problem problem = new Problem(new ProblemType("Empty BibliographicItemIdentifierValue."), null,
										"You have specified empty BibliographicItemIdentifierValue.");
								bibInformation.setProblems(Arrays.asList(problem));
								bibInformations.add(bibInformation);
							}
							break;
						}

						bibInformation.setBibliographicId(bibId);

						AlephItem alephItem = alephSvcMgr.lookupItem(id, initData);
						if (alephItem != null) {

							AgencyId suppliedAgencyId = null;
							if (getBibDescription) {
								if (initData.getInitiationHeader() == null || initData.getInitiationHeader().getToAgencyId() == null)
									suppliedAgencyId = alephSvcMgr.toAgencyId(alephSvcMgr.getDefaultAgencyId());
								else
									suppliedAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId();
							}

							HoldingsSet holdingsSet = parseHoldingsSet(alephItem, suppliedAgencyId, initData);

							bibInformation.setHoldingsSets(Arrays.asList(holdingsSet));

							bibInformations.add(bibInformation);
							++itemsForwarded;

							boolean isLast = bibIds.get(bibIds.size() - 1).equals(bibId);
							// Do not create NextItemToken if this is last item desired
							if (maximumItemsCount == itemsForwarded && !isLast) {

								ItemToken itemToken = new ItemToken();
								itemToken.setBibliographicId(id);
								itemToken.setItemId(alephItem.getItemId());

								int newToken = random.nextInt();

								itemToken.setNextToken(Integer.toString(newToken));
								tokens.put(Integer.toString(newToken), itemToken);

								responseData.setNextItemToken(Integer.toString(newToken));
								break;
							}
						} else if (wantSeeAllProblems) {
							Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, null, "Item " + id + ", you are searching for, does not exist.");

							bibInformation.setProblems(Arrays.asList(p));

							// Note that Problem elements within <ns1:BibInformation> is also considered as one item forwarded
							bibInformations.add(bibInformation);
							++itemsForwarded;

							boolean isLast = bibIds.get(bibIds.size() - 1).equals(bibId);
							// Do not create NextItemToken if this is last item desired
							if (maximumItemsCount == itemsForwarded && !isLast) {

								ItemToken itemToken = new ItemToken();
								itemToken.setBibliographicId(id);

								int newToken = random.nextInt();

								itemToken.setNextToken(Integer.toString(newToken));
								tokens.put(Integer.toString(newToken), itemToken);

								responseData.setNextItemToken(Integer.toString(newToken));
								break;
							}
						}

					} else {

						bibInformation.setProblems(ServiceHelper.generateProblems(Version1GeneralProcessingError.NEEDED_DATA_MISSING, "BibliographicItemId/RecordId", null,
								"BibliographicItemId/RecordId was not properly set."));
						bibInformations.add(bibInformation);
						++itemsForwarded;
					}
				} catch (IOException ie) {
					Problem p = new Problem(new ProblemType("Processing IOException error."), null, ie.getMessage());
					bibInformation.setProblems(Arrays.asList(p));

					bibInformations.add(bibInformation);
				} catch (ParserConfigurationException pce) {
					Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
					bibInformation.setProblems(Arrays.asList(p));

					bibInformations.add(bibInformation);
				} catch (SAXException se) {
					Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
					bibInformation.setProblems(Arrays.asList(p));

					bibInformations.add(bibInformation);
				} catch (AlephException ae) {
					Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
					bibInformation.setProblems(Arrays.asList(p));

					bibInformations.add(bibInformation);
				} catch (Exception e) {
					Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
					responseData.setProblems(Arrays.asList(p));
					break;
				}

			}

		} else if (initData.getItemIds() != null && initData.getItemIds().size() > 0) {

			for (ItemId itemId : itemIds) {
				String id = itemId.getItemIdentifierValue();
				BibInformation bibInformation = new BibInformation();

				if (id.isEmpty()) {
					if (wantSeeAllProblems) {
						Problem problem = new Problem(new ProblemType("Empty ItemIdIdentifierValue."), null, "You have specified empty ItemIdIdentifierValue.");
						bibInformation.setProblems(Arrays.asList(problem));
						bibInformations.add(bibInformation);
					}
				} else {
					try {
						AlephItem alephItem = alephSvcMgr.lookupItem(id, initData);

						bibInformation.setBibliographicId(createBibliographicId(id));

						if (alephItem != null) {

							AgencyId suppliedAgencyId = null;

							if (getBibDescription) {
								if (initData.getInitiationHeader() == null || initData.getInitiationHeader().getToAgencyId() == null)
									suppliedAgencyId = alephSvcMgr.toAgencyId(alephSvcMgr.getDefaultAgencyId());
								else
									suppliedAgencyId = initData.getInitiationHeader().getToAgencyId().getAgencyId();
							}

							HoldingsSet holdingsSet = parseHoldingsSet(alephItem, suppliedAgencyId, initData);

							bibInformation.setHoldingsSets(Arrays.asList(holdingsSet));

							bibInformations.add(bibInformation);
							++itemsForwarded;

							boolean isLast = itemIds.get(itemIds.size() - 1).equals(itemId);
							// Do not create NextItemToken if this is last item desired
							if (maximumItemsCount == itemsForwarded && !isLast) {

								ItemToken itemToken = new ItemToken();
								itemToken.setBibliographicId(id);

								int newToken = random.nextInt();

								itemToken.setNextToken(Integer.toString(newToken));
								tokens.put(Integer.toString(newToken), itemToken);

								responseData.setNextItemToken(Integer.toString(newToken));
								break;
							}
						} else if (wantSeeAllProblems) {
							Problem p = new Problem(Version1LookupItemProcessingError.UNKNOWN_ITEM, null, "Item " + id + ", you are searching for, does not exist.");

							bibInformation.setProblems(Arrays.asList(p));

							// Note that Problem elements within <ns1:BibInformation> is also considered as one item forwarded
							bibInformations.add(bibInformation);
							++itemsForwarded;

							boolean isLast = itemIds.get(itemIds.size() - 1).equals(itemId);
							// Do not create NextItemToken if this is last item desired
							if (maximumItemsCount == itemsForwarded && !isLast) {

								ItemToken itemToken = new ItemToken();
								itemToken.setBibliographicId(id);

								int newToken = random.nextInt();

								itemToken.setNextToken(Integer.toString(newToken));
								tokens.put(Integer.toString(newToken), itemToken);

								responseData.setNextItemToken(Integer.toString(newToken));
								break;
							}
						}
					} catch (IOException ie) {
						Problem p = new Problem(new ProblemType("Processing IOException error."), null, ie.getMessage());
						bibInformation.setProblems(Arrays.asList(p));

						bibInformations.add(bibInformation);
					} catch (ParserConfigurationException pce) {
						Problem p = new Problem(new ProblemType("Processing ParserConfigurationException error."), null, pce.getMessage());
						bibInformation.setProblems(Arrays.asList(p));

						bibInformations.add(bibInformation);
					} catch (SAXException se) {
						Problem p = new Problem(new ProblemType("Processing SAXException error."), null, se.getMessage());
						bibInformation.setProblems(Arrays.asList(p));

						bibInformations.add(bibInformation);
					} catch (AlephException ae) {
						Problem p = new Problem(new ProblemType("Processing AlephException error."), null, ae.getMessage());
						bibInformation.setProblems(Arrays.asList(p));

						bibInformations.add(bibInformation);
					} catch (Exception e) {
						Problem p = new Problem(new ProblemType("Unknown processing exception error."), null, e.getMessage());
						responseData.setProblems(Arrays.asList(p));
						break;
					}
				}
			}

		} else {
			throw new ServiceException(ServiceError.UNSUPPORTED_REQUEST, "LookupItemSet service has no implemented service to process HoldingsSetId.");
		}

		if (responseData.getProblems() == null || responseData.getProblem(0) == null)
			responseData.setBibInformations(bibInformations);

		InitiationHeader initiationHeader = initData.getInitiationHeader();
		if (initiationHeader != null) {
			ResponseHeader responseHeader = new ResponseHeader();
			if (initiationHeader.getFromAgencyId() != null && initiationHeader.getToAgencyId() != null) {
				// Reverse From/To AgencyId because of the request was processed (return to initiator)
				ToAgencyId toAgencyId = new ToAgencyId();
				toAgencyId.setAgencyIds(initiationHeader.getFromAgencyId().getAgencyIds());

				FromAgencyId fromAgencyId = new FromAgencyId();
				fromAgencyId.setAgencyIds(initiationHeader.getToAgencyId().getAgencyIds());

				responseHeader.setFromAgencyId(fromAgencyId);
				responseHeader.setToAgencyId(toAgencyId);
			}
			if (initiationHeader.getFromSystemId() != null && initiationHeader.getToSystemId() != null) {
				responseHeader.setFromSystemId(initiationHeader.getFromSystemId());
				responseHeader.setToSystemId(initiationHeader.getToSystemId());
				if (initiationHeader.getFromAgencyAuthentication() != null && !initiationHeader.getFromAgencyAuthentication().isEmpty())
					responseHeader.setFromSystemAuthentication(initiationHeader.getFromAgencyAuthentication());
			}
			responseData.setResponseHeader(responseHeader);
		}

		return responseData;
	}

	private BibliographicId createBibliographicId(String id) {
		BibliographicId bibliographicId = new BibliographicId();
		BibliographicItemId bibliographicItemId = new BibliographicItemId();

		bibliographicItemId.setBibliographicItemIdentifier(id);
		bibliographicItemId.setBibliographicItemIdentifierCode(Version1BibliographicItemIdentifierCode.LEGAL_DEPOSIT_NUMBER);

		bibliographicId.setBibliographicItemId(bibliographicItemId);
		return bibliographicId;
	}

	/**
	 * 
	 * Parses response from requested ItemId (we know for sure there is only one item to parse)
	 * 
	 * @param alephItem
	 * @param suppliedAgencyId
	 * @param getBibDescription
	 * @param getCircStatus
	 * @param getHoldQueueLength
	 * @param getItemDescription
	 * @return
	 */
	private HoldingsSet parseHoldingsSet(AlephItem alephItem, AgencyId suppliedAgencyId, LookupItemSetInitiationData initData) {
		HoldingsSet holdingsSet = new HoldingsSet();
		List<ItemInformation> itemInfoList = new ArrayList<ItemInformation>();

		ItemOptionalFields iof = new ItemOptionalFields();

		if (initData.getBibliographicDescriptionDesired()) {
			BibliographicDescription bDesc = AlephUtil.getBibliographicDescription(alephItem, suppliedAgencyId);
			holdingsSet.setBibliographicDescription(bDesc);
		}

		addItemIdentifierToItemOptionalFields(iof, alephItem.getBarcode(), Version1BibliographicItemIdentifierCode.LEGAL_DEPOSIT_NUMBER);

		ItemInformation info = new ItemInformation();

		ItemId itemId = new ItemId();
		itemId.setItemIdentifierValue(alephItem.getItemId());
		itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
		info.setItemId(itemId);

		if (initData.getHoldQueueLengthDesired())
			iof.setHoldQueueLength(new BigDecimal(alephItem.getHoldQueueLength()));
		if (initData.getCirculationStatusDesired())
			iof.setCirculationStatus(alephItem.getCirculationStatus());
		if (initData.getItemDescriptionDesired()) {
			ItemDescription itemDescription = new ItemDescription();
			if (alephItem.getDescription() != null)
				itemDescription.setItemDescriptionLevel(new ItemDescriptionLevel(AlephConstants.DEFAULT_SCHEME, alephItem.getDescription()));
			itemDescription.setCallNumber(alephItem.getCallNumber());
			itemDescription.setCopyNumber(alephItem.getCopyNumber());
			itemDescription.setNumberOfPieces(alephItem.getNumberOfPieces());
			if (alephItem.getDescription() != null) {
				HoldingsInformation holdingsInformation = new HoldingsInformation();
				holdingsInformation.setUnstructuredHoldingsData(alephItem.getDescription());
				itemDescription.setHoldingsInformation(holdingsInformation);
			}
			iof.setItemDescription(itemDescription);
		}
		if (initData.getItemUseRestrictionTypeDesired()) {
			if (alephItem.getItemRestrictions().size() > 0) {

				List<ItemUseRestrictionType> itemUseRestrictionTypes = new ArrayList<ItemUseRestrictionType>();

				for (String itemRestriction : alephItem.getItemRestrictions()) {

					ItemUseRestrictionType itemUseRestrictionType = AlephUtil.parseItemUseRestrictionType(itemRestriction);

					if (itemUseRestrictionType != null)
						itemUseRestrictionTypes.add(itemUseRestrictionType);
				}

				if (itemUseRestrictionTypes.size() > 0)
					iof.setItemUseRestrictionTypes(itemUseRestrictionTypes);
			}
		}

		info.setItemOptionalFields(iof);
		itemInfoList.add(info);
		holdingsSet.setItemInformations(itemInfoList);

		return holdingsSet;
	}

	/**
	 * 
	 * Parses response from requested RecordId
	 * 
	 * @param alephItems
	 * @param suppliedAgencyId
	 * @param getBibDescription
	 * @param getCircStatus
	 * @param getHoldQueueLength
	 * @param getItemDescription
	 * @return
	 */
	private List<HoldingsSet> parseHoldingsSets(List<AlephItem> alephItems, AgencyId suppliedAgencyId, LookupItemSetInitiationData initData) {

		HoldingsSet holdingsSet = new HoldingsSet();
		List<ItemInformation> itemInfoList = new ArrayList<ItemInformation>();
		for (AlephItem item : alephItems) {

			if (maximumItemsCount == 0 || itemsForwarded < maximumItemsCount) {
				ItemOptionalFields iof = new ItemOptionalFields();

				if (initData.getBibliographicDescriptionDesired()) {
					BibliographicDescription bDesc = AlephUtil.getBibliographicDescription(item, suppliedAgencyId);
					holdingsSet.setBibliographicDescription(bDesc);
				}

				addItemIdentifierToItemOptionalFields(iof, item.getBarcode(), Version1BibliographicItemIdentifierCode.LEGAL_DEPOSIT_NUMBER);

				ItemInformation info = new ItemInformation();

				ItemId itemId = new ItemId();
				itemId.setItemIdentifierValue(item.getItemId());
				itemId.setItemIdentifierType(Version1ItemIdentifierType.ACCESSION_NUMBER);
				info.setItemId(itemId);

				if (initData.getHoldQueueLengthDesired())
					iof.setHoldQueueLength(new BigDecimal(item.getHoldQueueLength()));
				if (initData.getCirculationStatusDesired())
					iof.setCirculationStatus(item.getCirculationStatus());
				if (initData.getItemDescriptionDesired()) {
					ItemDescription itemDescription = new ItemDescription();
					if (item.getDescription() != null)
						itemDescription.setItemDescriptionLevel(new ItemDescriptionLevel(AlephConstants.DEFAULT_SCHEME, item.getDescription()));
					itemDescription.setCallNumber(item.getCallNumber());
					itemDescription.setCopyNumber(item.getCopyNumber());
					itemDescription.setNumberOfPieces(item.getNumberOfPieces());
					if (item.getDescription() != null) {
						HoldingsInformation holdingsInformation = new HoldingsInformation();
						holdingsInformation.setUnstructuredHoldingsData(item.getDescription());
						itemDescription.setHoldingsInformation(holdingsInformation);
					}
					iof.setItemDescription(itemDescription);
				}
				if (initData.getItemUseRestrictionTypeDesired()) {
					if (item.getItemRestrictions().size() > 0) {

						List<ItemUseRestrictionType> itemUseRestrictionTypes = new ArrayList<ItemUseRestrictionType>();

						for (String itemRestriction : item.getItemRestrictions()) {

							ItemUseRestrictionType itemUseRestrictionType = AlephUtil.parseItemUseRestrictionType(itemRestriction);

							if (itemUseRestrictionType != null)
								itemUseRestrictionTypes.add(itemUseRestrictionType);
						}

						if (itemUseRestrictionTypes.size() > 0)
							iof.setItemUseRestrictionTypes(itemUseRestrictionTypes);
					}
				}

				info.setItemOptionalFields(iof);

				itemInfoList.add(info);
				++itemsForwarded;
			} else
				break;
		}
		holdingsSet.setItemInformations(itemInfoList);

		return Arrays.asList(holdingsSet);
	}

	/**
	 * Adds passed String to Item Optional Fields as BibliographicItemIdentifier with passed BibliographicItemIdentifierCode.
	 * 
	 * @param iof
	 * @param barcode
	 * @param bibItemIdCode
	 */
	private void addItemIdentifierToItemOptionalFields(ItemOptionalFields iof, String barcode, BibliographicItemIdentifierCode bibItemIdCode) {
		BibliographicDescription bibDesc = new BibliographicDescription();
		List<BibliographicItemId> itemIds = new ArrayList<BibliographicItemId>();
		BibliographicItemId bibId = new BibliographicItemId();
		bibId.setBibliographicItemIdentifier(barcode);
		bibId.setBibliographicItemIdentifierCode(bibItemIdCode);
		itemIds.add(bibId);
		bibDesc.setBibliographicItemIds(itemIds);
		iof.setBibliographicDescription(bibDesc);
	}

	private int getBibIdIndex(List<BibliographicId> bibIds, String bibId) {

		for (int i = 0; i < bibIds.size(); i++) {
			BibliographicId bibIdFromList = bibIds.get(i);
			if (bibIdFromList.getBibliographicItemId() != null) {
				if (bibIds.get(i).getBibliographicItemId().getBibliographicItemIdentifier().equalsIgnoreCase(bibId)) {
					return i;
				}
			} else if (bibIdFromList.getBibliographicRecordId() != null) {
				if (bibIds.get(i).getBibliographicRecordId().getBibliographicRecordIdentifier().equalsIgnoreCase(bibId)) {
					return i;
				}
			}
		}

		return -1;
	}

	private int getItemIdIndex(List<ItemId> itemIds, String itemId) {
		for (int i = 0; i < itemIds.size(); i++) {
			if (itemIds.get(i).getItemIdentifierValue().equalsIgnoreCase(itemId)) {
				return i;
			}
		}
		return -1;
	}
}
