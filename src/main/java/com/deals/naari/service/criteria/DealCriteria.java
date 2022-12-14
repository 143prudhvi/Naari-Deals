package com.deals.naari.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.deals.naari.domain.Deal} entity. This class is used
 * in {@link com.deals.naari.web.rest.DealResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /deals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DealCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter highlight;

    private StringFilter postedBy;

    private StringFilter postedDate;

    private StringFilter startDate;

    private StringFilter endDate;

    private StringFilter originalPrice;

    private StringFilter currentPrice;

    private StringFilter priceTag;

    private StringFilter discount;

    private StringFilter discountType;

    private StringFilter active;

    private BooleanFilter approved;

    private StringFilter country;

    private StringFilter city;

    private StringFilter pinCode;

    private StringFilter merchant;

    private StringFilter category;

    private StringFilter tags;

    private StringFilter brand;

    private BooleanFilter expired;

    private Boolean distinct;

    public DealCriteria() {}

    public DealCriteria(DealCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.highlight = other.highlight == null ? null : other.highlight.copy();
        this.postedBy = other.postedBy == null ? null : other.postedBy.copy();
        this.postedDate = other.postedDate == null ? null : other.postedDate.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.endDate = other.endDate == null ? null : other.endDate.copy();
        this.originalPrice = other.originalPrice == null ? null : other.originalPrice.copy();
        this.currentPrice = other.currentPrice == null ? null : other.currentPrice.copy();
        this.priceTag = other.priceTag == null ? null : other.priceTag.copy();
        this.discount = other.discount == null ? null : other.discount.copy();
        this.discountType = other.discountType == null ? null : other.discountType.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.approved = other.approved == null ? null : other.approved.copy();
        this.country = other.country == null ? null : other.country.copy();
        this.city = other.city == null ? null : other.city.copy();
        this.pinCode = other.pinCode == null ? null : other.pinCode.copy();
        this.merchant = other.merchant == null ? null : other.merchant.copy();
        this.category = other.category == null ? null : other.category.copy();
        this.tags = other.tags == null ? null : other.tags.copy();
        this.brand = other.brand == null ? null : other.brand.copy();
        this.expired = other.expired == null ? null : other.expired.copy();
        this.distinct = other.distinct;
    }

    @Override
    public DealCriteria copy() {
        return new DealCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getHighlight() {
        return highlight;
    }

    public StringFilter highlight() {
        if (highlight == null) {
            highlight = new StringFilter();
        }
        return highlight;
    }

    public void setHighlight(StringFilter highlight) {
        this.highlight = highlight;
    }

    public StringFilter getPostedBy() {
        return postedBy;
    }

    public StringFilter postedBy() {
        if (postedBy == null) {
            postedBy = new StringFilter();
        }
        return postedBy;
    }

    public void setPostedBy(StringFilter postedBy) {
        this.postedBy = postedBy;
    }

    public StringFilter getPostedDate() {
        return postedDate;
    }

    public StringFilter postedDate() {
        if (postedDate == null) {
            postedDate = new StringFilter();
        }
        return postedDate;
    }

    public void setPostedDate(StringFilter postedDate) {
        this.postedDate = postedDate;
    }

    public StringFilter getStartDate() {
        return startDate;
    }

    public StringFilter startDate() {
        if (startDate == null) {
            startDate = new StringFilter();
        }
        return startDate;
    }

    public void setStartDate(StringFilter startDate) {
        this.startDate = startDate;
    }

    public StringFilter getEndDate() {
        return endDate;
    }

    public StringFilter endDate() {
        if (endDate == null) {
            endDate = new StringFilter();
        }
        return endDate;
    }

    public void setEndDate(StringFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getOriginalPrice() {
        return originalPrice;
    }

    public StringFilter originalPrice() {
        if (originalPrice == null) {
            originalPrice = new StringFilter();
        }
        return originalPrice;
    }

    public void setOriginalPrice(StringFilter originalPrice) {
        this.originalPrice = originalPrice;
    }

    public StringFilter getCurrentPrice() {
        return currentPrice;
    }

    public StringFilter currentPrice() {
        if (currentPrice == null) {
            currentPrice = new StringFilter();
        }
        return currentPrice;
    }

    public void setCurrentPrice(StringFilter currentPrice) {
        this.currentPrice = currentPrice;
    }

    public StringFilter getPriceTag() {
        return priceTag;
    }

    public StringFilter priceTag() {
        if (priceTag == null) {
            priceTag = new StringFilter();
        }
        return priceTag;
    }

    public void setPriceTag(StringFilter priceTag) {
        this.priceTag = priceTag;
    }

    public StringFilter getDiscount() {
        return discount;
    }

    public StringFilter discount() {
        if (discount == null) {
            discount = new StringFilter();
        }
        return discount;
    }

    public void setDiscount(StringFilter discount) {
        this.discount = discount;
    }

    public StringFilter getDiscountType() {
        return discountType;
    }

    public StringFilter discountType() {
        if (discountType == null) {
            discountType = new StringFilter();
        }
        return discountType;
    }

    public void setDiscountType(StringFilter discountType) {
        this.discountType = discountType;
    }

    public StringFilter getActive() {
        return active;
    }

    public StringFilter active() {
        if (active == null) {
            active = new StringFilter();
        }
        return active;
    }

    public void setActive(StringFilter active) {
        this.active = active;
    }

    public BooleanFilter getApproved() {
        return approved;
    }

    public BooleanFilter approved() {
        if (approved == null) {
            approved = new BooleanFilter();
        }
        return approved;
    }

    public void setApproved(BooleanFilter approved) {
        this.approved = approved;
    }

    public StringFilter getCountry() {
        return country;
    }

    public StringFilter country() {
        if (country == null) {
            country = new StringFilter();
        }
        return country;
    }

    public void setCountry(StringFilter country) {
        this.country = country;
    }

    public StringFilter getCity() {
        return city;
    }

    public StringFilter city() {
        if (city == null) {
            city = new StringFilter();
        }
        return city;
    }

    public void setCity(StringFilter city) {
        this.city = city;
    }

    public StringFilter getPinCode() {
        return pinCode;
    }

    public StringFilter pinCode() {
        if (pinCode == null) {
            pinCode = new StringFilter();
        }
        return pinCode;
    }

    public void setPinCode(StringFilter pinCode) {
        this.pinCode = pinCode;
    }

    public StringFilter getMerchant() {
        return merchant;
    }

    public StringFilter merchant() {
        if (merchant == null) {
            merchant = new StringFilter();
        }
        return merchant;
    }

    public void setMerchant(StringFilter merchant) {
        this.merchant = merchant;
    }

    public StringFilter getCategory() {
        return category;
    }

    public StringFilter category() {
        if (category == null) {
            category = new StringFilter();
        }
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getTags() {
        return tags;
    }

    public StringFilter tags() {
        if (tags == null) {
            tags = new StringFilter();
        }
        return tags;
    }

    public void setTags(StringFilter tags) {
        this.tags = tags;
    }

    public StringFilter getBrand() {
        return brand;
    }

    public StringFilter brand() {
        if (brand == null) {
            brand = new StringFilter();
        }
        return brand;
    }

    public void setBrand(StringFilter brand) {
        this.brand = brand;
    }

    public BooleanFilter getExpired() {
        return expired;
    }

    public BooleanFilter expired() {
        if (expired == null) {
            expired = new BooleanFilter();
        }
        return expired;
    }

    public void setExpired(BooleanFilter expired) {
        this.expired = expired;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DealCriteria that = (DealCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(highlight, that.highlight) &&
            Objects.equals(postedBy, that.postedBy) &&
            Objects.equals(postedDate, that.postedDate) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(originalPrice, that.originalPrice) &&
            Objects.equals(currentPrice, that.currentPrice) &&
            Objects.equals(priceTag, that.priceTag) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(discountType, that.discountType) &&
            Objects.equals(active, that.active) &&
            Objects.equals(approved, that.approved) &&
            Objects.equals(country, that.country) &&
            Objects.equals(city, that.city) &&
            Objects.equals(pinCode, that.pinCode) &&
            Objects.equals(merchant, that.merchant) &&
            Objects.equals(category, that.category) &&
            Objects.equals(tags, that.tags) &&
            Objects.equals(brand, that.brand) &&
            Objects.equals(expired, that.expired) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            highlight,
            postedBy,
            postedDate,
            startDate,
            endDate,
            originalPrice,
            currentPrice,
            priceTag,
            discount,
            discountType,
            active,
            approved,
            country,
            city,
            pinCode,
            merchant,
            category,
            tags,
            brand,
            expired,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DealCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (highlight != null ? "highlight=" + highlight + ", " : "") +
            (postedBy != null ? "postedBy=" + postedBy + ", " : "") +
            (postedDate != null ? "postedDate=" + postedDate + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (endDate != null ? "endDate=" + endDate + ", " : "") +
            (originalPrice != null ? "originalPrice=" + originalPrice + ", " : "") +
            (currentPrice != null ? "currentPrice=" + currentPrice + ", " : "") +
            (priceTag != null ? "priceTag=" + priceTag + ", " : "") +
            (discount != null ? "discount=" + discount + ", " : "") +
            (discountType != null ? "discountType=" + discountType + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (approved != null ? "approved=" + approved + ", " : "") +
            (country != null ? "country=" + country + ", " : "") +
            (city != null ? "city=" + city + ", " : "") +
            (pinCode != null ? "pinCode=" + pinCode + ", " : "") +
            (merchant != null ? "merchant=" + merchant + ", " : "") +
            (category != null ? "category=" + category + ", " : "") +
            (tags != null ? "tags=" + tags + ", " : "") +
            (brand != null ? "brand=" + brand + ", " : "") +
            (expired != null ? "expired=" + expired + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
