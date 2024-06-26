package modules.sales.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import modules.customers.Account.AccountExtension;
import modules.customers.ContactDetail.ContactDetailExtension;
import modules.products.domain.ProductInfo;
import modules.products.domain.ProductPriceList;
import modules.sales.Opportunity.OpportunityExtension;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.Enumeration;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.metadata.model.document.Bizlet.DomainValue;
import org.skyve.util.Util;

/**
 * Opportunity
 * 
 * @depend - - - PricingType
 * @navhas n product 1 ProductInfo
 * @navhas n contact 1 ContactDetail
 * @navhas n account 1 Account
 * @navhas n priceList 1 ProductPriceList
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public abstract class Opportunity extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "sales";

	/** @hidden */
	public static final String DOCUMENT_NAME = "Opportunity";

	/** @hidden */
	public static final String topicPropertyName = "topic";

	/** @hidden */
	public static final String contactPropertyName = "contact";

	/** @hidden */
	public static final String accountPropertyName = "account";

	/** @hidden */
	public static final String descriptionPropertyName = "description";

	/** @hidden */
	public static final String productPropertyName = "product";

	/** @hidden */
	public static final String priceListPropertyName = "priceList";

	/** @hidden */
	public static final String pricingTypePropertyName = "pricingType";

	/** @hidden */
	public static final String quantityPropertyName = "quantity";

	/** @hidden */
	public static final String bulkDiscountPropertyName = "bulkDiscount";

	/** @hidden */
	public static final String manualDiscountPropertyName = "manualDiscount";

	/** @hidden */
	public static final String taxPropertyName = "tax";

	/** @hidden */
	public static final String totalPropertyName = "total";

	/** @hidden */
	public static final String selectedTabPropertyName = "selectedTab";

	/**
	 * Pricing Type
	 * <br/>
	 * The pricing type to use for the selected product
	 **/
	@XmlEnum
	public static enum PricingType implements Enumeration {
		retail("Retail", "Retail"),
		bulk("Bulk", "Bulk");

		private String code;
		private String description;

		/** @hidden */
		private DomainValue domainValue;

		/** @hidden */
		private static List<DomainValue> domainValues = Stream.of(values()).map(PricingType::toDomainValue).collect(Collectors.toUnmodifiableList());

		private PricingType(String code, String description) {
			this.code = code;
			this.description = description;
			this.domainValue = new DomainValue(code, description);
		}

		@Override
		public String toCode() {
			return code;
		}

		@Override
		public String toLocalisedDescription() {
			return Util.i18n(description);
		}

		@Override
		public DomainValue toDomainValue() {
			return domainValue;
		}

		public static PricingType fromCode(String code) {
			PricingType result = null;

			for (PricingType value : values()) {
				if (value.code.equals(code)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static PricingType fromLocalisedDescription(String description) {
			PricingType result = null;

			for (PricingType value : values()) {
				if (value.toLocalisedDescription().equals(description)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static List<DomainValue> toDomainValues() {
			return domainValues;
		}
	}

	/**
	 * Topic
	 **/
	private String topic;

	/**
	 * Contact
	 * <br/>
	 * The primary contact of the opportunity
	 **/
	private ContactDetailExtension contact = null;

	/**
	 * Account
	 * <br/>
	 * The account of the opportunity
	 **/
	private AccountExtension account = null;

	/**
	 * Description
	 * <br/>
	 * A description of the opportunity
	 **/
	private String description;

	/**
	 * Product
	 * <br/>
	 * The product of the opportunity
	 **/
	private ProductInfo product = null;

	/**
	 * Product Price List
	 * <br/>
	 * The price list to use for the selected product
	 **/
	private ProductPriceList priceList = null;

	/**
	 * Pricing Type
	 * <br/>
	 * The pricing type to use for the selected product
	 **/
	private PricingType pricingType;

	/**
	 * Quantity
	 * <br/>
	 * The quantity of product for this opportunity
	 **/
	private Long quantity;

	/**
	 * Bulk Discount
	 * <br/>
	 * The bulk discount for the inputted amount
	 **/
	private Integer bulkDiscount;

	/**
	 * Manual Discount
	 * <br/>
	 * The amount of manual discount to be applied
	 **/
	private Integer manualDiscount;

	/**
	 * Tax
	 * <br/>
	 * The amount of tax for this amount
	 **/
	private Integer tax;

	/**
	 * Provisional Total
	 * <br/>
	 * The provisional total for this opportunity
	 **/
	private Long total;

	/**
	 * Selected Tab
	 **/
	private Integer selectedTab;

	@Override
	@XmlTransient
	public String getBizModule() {
		return Opportunity.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Opportunity.DOCUMENT_NAME;
	}

	public static OpportunityExtension newInstance() {
		try {
			return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new DomainException(e);
		}
	}

	@Override
	@XmlTransient
	public String getBizKey() {
		try {
			return org.skyve.util.Binder.formatMessage("{topic}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Opportunity) && 
					this.getBizId().equals(((Opportunity) o).getBizId()));
	}

	/**
	 * {@link #topic} accessor.
	 * @return	The value.
	 **/
	public String getTopic() {
		return topic;
	}

	/**
	 * {@link #topic} mutator.
	 * @param topic	The new value.
	 **/
	@XmlElement
	public void setTopic(String topic) {
		preset(topicPropertyName, topic);
		this.topic = topic;
	}

	/**
	 * {@link #contact} accessor.
	 * @return	The value.
	 **/
	public ContactDetailExtension getContact() {
		return contact;
	}

	/**
	 * {@link #contact} mutator.
	 * @param contact	The new value.
	 **/
	@XmlElement
	public void setContact(ContactDetailExtension contact) {
		if (this.contact != contact) {
			preset(contactPropertyName, contact);
			this.contact = contact;
		}
	}

	/**
	 * {@link #account} accessor.
	 * @return	The value.
	 **/
	public AccountExtension getAccount() {
		return account;
	}

	/**
	 * {@link #account} mutator.
	 * @param account	The new value.
	 **/
	@XmlElement
	public void setAccount(AccountExtension account) {
		if (this.account != account) {
			preset(accountPropertyName, account);
			this.account = account;
		}
	}

	/**
	 * {@link #description} accessor.
	 * @return	The value.
	 **/
	public String getDescription() {
		return description;
	}

	/**
	 * {@link #description} mutator.
	 * @param description	The new value.
	 **/
	@XmlElement
	public void setDescription(String description) {
		preset(descriptionPropertyName, description);
		this.description = description;
	}

	/**
	 * {@link #product} accessor.
	 * @return	The value.
	 **/
	public ProductInfo getProduct() {
		return product;
	}

	/**
	 * {@link #product} mutator.
	 * @param product	The new value.
	 **/
	@XmlElement
	public void setProduct(ProductInfo product) {
		if (this.product != product) {
			preset(productPropertyName, product);
			this.product = product;
		}
	}

	/**
	 * {@link #priceList} accessor.
	 * @return	The value.
	 **/
	public ProductPriceList getPriceList() {
		return priceList;
	}

	/**
	 * {@link #priceList} mutator.
	 * @param priceList	The new value.
	 **/
	@XmlElement
	public void setPriceList(ProductPriceList priceList) {
		if (this.priceList != priceList) {
			preset(priceListPropertyName, priceList);
			this.priceList = priceList;
		}
	}

	/**
	 * {@link #pricingType} accessor.
	 * @return	The value.
	 **/
	public PricingType getPricingType() {
		return pricingType;
	}

	/**
	 * {@link #pricingType} mutator.
	 * @param pricingType	The new value.
	 **/
	@XmlElement
	public void setPricingType(PricingType pricingType) {
		preset(pricingTypePropertyName, pricingType);
		this.pricingType = pricingType;
	}

	/**
	 * {@link #quantity} accessor.
	 * @return	The value.
	 **/
	public Long getQuantity() {
		return quantity;
	}

	/**
	 * {@link #quantity} mutator.
	 * @param quantity	The new value.
	 **/
	@XmlElement
	public void setQuantity(Long quantity) {
		preset(quantityPropertyName, quantity);
		this.quantity = quantity;
	}

	/**
	 * {@link #bulkDiscount} accessor.
	 * @return	The value.
	 **/
	public Integer getBulkDiscount() {
		return bulkDiscount;
	}

	/**
	 * {@link #bulkDiscount} mutator.
	 * @param bulkDiscount	The new value.
	 **/
	@XmlElement
	public void setBulkDiscount(Integer bulkDiscount) {
		preset(bulkDiscountPropertyName, bulkDiscount);
		this.bulkDiscount = bulkDiscount;
	}

	/**
	 * {@link #manualDiscount} accessor.
	 * @return	The value.
	 **/
	public Integer getManualDiscount() {
		return manualDiscount;
	}

	/**
	 * {@link #manualDiscount} mutator.
	 * @param manualDiscount	The new value.
	 **/
	@XmlElement
	public void setManualDiscount(Integer manualDiscount) {
		preset(manualDiscountPropertyName, manualDiscount);
		this.manualDiscount = manualDiscount;
	}

	/**
	 * {@link #tax} accessor.
	 * @return	The value.
	 **/
	public Integer getTax() {
		return tax;
	}

	/**
	 * {@link #tax} mutator.
	 * @param tax	The new value.
	 **/
	@XmlElement
	public void setTax(Integer tax) {
		preset(taxPropertyName, tax);
		this.tax = tax;
	}

	/**
	 * {@link #total} accessor.
	 * @return	The value.
	 **/
	public Long getTotal() {
		return total;
	}

	/**
	 * {@link #total} mutator.
	 * @param total	The new value.
	 **/
	@XmlElement
	public void setTotal(Long total) {
		preset(totalPropertyName, total);
		this.total = total;
	}

	/**
	 * {@link #selectedTab} accessor.
	 * @return	The value.
	 **/
	public Integer getSelectedTab() {
		return selectedTab;
	}

	/**
	 * {@link #selectedTab} mutator.
	 * @param selectedTab	The new value.
	 **/
	@XmlElement
	public void setSelectedTab(Integer selectedTab) {
		this.selectedTab = selectedTab;
	}

	/**
	 * priceListSelected
	 *
	 * @return The condition
	 */
	@XmlTransient
	public boolean isPriceListSelected() {
		return (priceList != null);
	}

	/**
	 * {@link #isPriceListSelected} negation.
	 *
	 * @return The negated condition
	 */
	public boolean isNotPriceListSelected() {
		return (! isPriceListSelected());
	}

	/**
	 * productSelected
	 *
	 * @return The condition
	 */
	@XmlTransient
	public boolean isProductSelected() {
		return (product != null);
	}

	/**
	 * {@link #isProductSelected} negation.
	 *
	 * @return The negated condition
	 */
	public boolean isNotProductSelected() {
		return (! isProductSelected());
	}

	/**
	 * uploadSelected
	 *
	 * @return The condition
	 */
	@XmlTransient
	public boolean isUploadSelected() {
		return (getAccount() != null && getAccount().getInteractionType() != null && getAccount().getInteractionType().name() == "upload");
	}

	/**
	 * {@link #isUploadSelected} negation.
	 *
	 * @return The negated condition
	 */
	public boolean isNotUploadSelected() {
		return (! isUploadSelected());
	}
}
